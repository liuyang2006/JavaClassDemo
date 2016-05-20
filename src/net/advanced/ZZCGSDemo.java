package net.advanced;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZZCGSDemo {
    private static CloseableHttpClient httpclient;
    private static Scanner scanner;
    private static String vcStr;

    public static void main(String[] args) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        scanner = new Scanner(System.in);
        prepareHttpClient();

        do {
            stepForGetVCJPG();

            Runtime.getRuntime().exec("open " + "zzcgsvc.jpg"); //open verify code
            System.out.printf("请输入获取图片中的验证码:");
            vcStr = scanner.nextLine();

            if (stepForZZCGSPostLoginForm(vcStr))
                break;

            System.out.println("验证码输入错误,请重新输入.");
        } while (true);
        httpclient.close();
    }

    public static boolean stepForZZCGSPostLoginForm(String str) throws IOException {
        boolean flag = false;
        HttpPost httpPost = new HttpPost("http://zzcgs.com.cn/jdc_cx.aspx?ywdm=A_1");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTc0OTgwNzU5M2RkohwiYYdkshv9e4n0ilFFvboGO+M="));
        nvps.add(new BasicNameValuePair("ddlHpzl", "02"));
        nvps.add(new BasicNameValuePair("txtHphm", "豫A7665E"));
        nvps.add(new BasicNameValuePair("txtClsbdh", "LFV2A11G0A3519761"));
        nvps.add(new BasicNameValuePair("txtYzm", str));
        nvps.add(new BasicNameValuePair("Button1", " 查　询 "));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            //match string 用户名：xoqe；密码：1234

            String htmlPage = EntityUtils.toString(entity2, "UTF-8");

            if (htmlPage.indexOf("验证码填写有误") != -1)
                return false;

            parseHtmlForCGXX(htmlPage);


            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
        return true;
    }

    private static void parseHtmlForCGXX(String htmlPage) {

        Pattern pattern = Pattern.compile("<tr><td align='center'>(.*)</td><td align='center'>(.*)</td><td align='center'>(.*)</td></tr>");
        Matcher matcher = pattern.matcher(htmlPage);

        System.out.printf("车主\t下次年审日期\t报废日期\n");
        while (matcher.find()) {
            String name = matcher.group(1);
            String checkDate = matcher.group(2);
            String lastDate = matcher.group(3);
            System.out.printf("%s\t%s\t%s\n", name, checkDate, lastDate);
        }

        int st = htmlPage.lastIndexOf("<table border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#dedede\" width=\"680\" class=\"table\"");
        int end = htmlPage.indexOf("</table>", st);

        String errorData = htmlPage.substring(st, end + "</table>".length());
        Pattern pattern1 = Pattern.compile("(?s)<tr>.*</tr>");
        Matcher matcher1 = pattern1.matcher(errorData);

        int count = -1;
        while (matcher1.find()) {
            String strLine = matcher1.group();

            Pattern pattern2 = Pattern.compile("<b>(.*)</b>");
            Matcher matcher2 = pattern2.matcher(strLine);

            while (matcher2.find()) {
                String outStr = matcher2.group(1);
                System.out.printf("%s\t", outStr);
            }
            System.out.println();
            count++;
        }

        System.out.printf("共查询到 %d 条违法记录.\n", count);

    }

    public static void stepForGetVCJPG() throws IOException {
        HttpGet httpget = new HttpGet("http://zzcgs.com.cn/GetYzmCode.aspx");

        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();
            File file = new File("zzcgsvc.jpg");
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[128];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }

    public static String requestGetURL(String url, String encode) throws IOException {
        return requestGetURL(url, false, encode);
    }

    public static String requestGetURL(String url, boolean showInfo, String encodeStr) throws IOException {
        String htmlPage = null;
        HttpGet httpGet = new HttpGet(url);

        if (showInfo) {
            System.out.println("======================================");
            System.out.println(httpGet.getRequestLine());
            System.out.println(Arrays.toString(httpGet.getAllHeaders()));
            System.out.println("======================================");
        }
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {


            if (showInfo) {
                System.out.println("======================================");
                System.out.println(response.getStatusLine());
                System.out.println(Arrays.toString(response.getAllHeaders()));
                System.out.println("======================================");
            }
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            htmlPage = EntityUtils.toString(entity2, encodeStr);
            if (showInfo) {
                System.out.println(htmlPage);
            }

            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }
        return htmlPage;
    }


    public static void prepareHttpClient() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.NETSCAPE)
                .build();

        httpclient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();
    }
}
