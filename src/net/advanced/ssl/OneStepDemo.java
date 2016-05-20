package net.advanced.ssl;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import sun.nio.cs.ext.GBK;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VPNPass implements Serializable {
    public String getPassID() {
        return passID;
    }

    public void setPassID(String passID) {
        this.passID = passID;
    }

    public String getPassPW() {
        return passPW;
    }

    public void setPassPW(String passPW) {
        this.passPW = passPW;
    }

    private String passID;
    private String passPW;

    @Override
    public String toString() {
        return "VPNPass{" +
                "passID='" + passID + '\'' +
                ", passPW='" + passPW + '\'' +
                '}';
    }
}

public class OneStepDemo{

    public static final String VPN_LOGINFROM_URL = "https://vpn.zzu.edu.cn/dana-na/auth/url_default/login.cgi";
    public static final String VPN_HOMEPAGE_URL = "https://vpn.zzu.edu.cn/dana/home/index.cgi";
    public static final String KY_GETVCJPG_URL = "http://ky.zzu.edu.cn/keyans/zzjlogin3d.dll/zzjgetimg?ids=2344";
    public static final String KY_LOGINFORM_URL = "https://ky.zzu.edu.cn/keyanss/zzjlogin8.dll/login";
    public static final String ZZU_HOMEPAGE_URL = "https://vpn.zzu.edu.cn/,DanaInfo=www.zzu.edu.cn,SSO=U+";
    public static final String VPN_LOGOUT_URL = "https://vpn.zzu.edu.cn/dana-na/auth/logout.cgi";
    public static final String VPN_LOGIN_FAIL_URL = "https://vpn.zzu.edu.cn/dana-na/auth/url_default/welcome.cgi?p=failed";

    public static final String ZZU_XBTZLIST_URL = "https://vpn.zzu.edu.cn/msgs/vmsgisapi.dll/,DanaInfo=www16.zzu.edu.cn+vmsglist?mtype=m&lan=101,102,103";

    public static final String VPNPass_FILENAME = "VPNPass.ser";
    public static final String VCODE_FILENAME = "zzuvpnvcode.jpg";

    public static final int MAX_FILE_BUFFER = 128;

    public static ArrayList<HashMap<String, String>> newsList = new ArrayList<>();

    public static String gonghao;
    public static String mima;

    public static CloseableHttpClient httpclient;

    public static VPNPass vpnPass;

    public static boolean onlyGenLoginLink;

    public static Scanner scanner;


    private static void loadPreviousPassFromFile() throws IOException, ClassNotFoundException {
        // load previous pass from file
        FileInputStream fis = new FileInputStream(VPNPass_FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis);

        vpnPass = (VPNPass) ois.readObject();
        ois.close();
        fis.close();
    }

    public static void main(String[] args) throws Exception {
        gonghao = args[0];
        mima = args[1];
        vpnPass = new VPNPass();

        scanner = new Scanner(System.in);
        prepareSSLHttpClient();

        System.out.printf("欢迎使用郑州大学内网VPN账号生成软件.\n");
        System.out.printf("是否仅生成VPN链接? [回车]否, [任意字符]是:");
        String inStr = scanner.nextLine();
        if (!inStr.trim().equals(""))
            onlyGenLoginLink = true;

        System.out.printf("使用已有VPN账号登录请直接[回车], 重新获取VPN账号登录请输入[1]:");
        inStr = scanner.nextLine();
        if (inStr.trim().equals("")) {
            loadPreviousPassFromFile();
        } else {
            requestPassFromKYZZUNet();
        }
        System.out.println("使用的登陆账号为:" + vpnPass);

        //login to vpn zzu for inner network

        //requestGetURL("https://vpn.zzu.edu.cn", true, "GBK");

        postVPNLoginForm(VPN_LOGINFROM_URL, vpnPass);

        if (onlyGenLoginLink) {
            httpclient.close();
            prepareSSLHttpClient();
            postVPNLoginForm(VPN_LOGINFROM_URL, vpnPass);
            System.exit(1);
        }

        System.out.println("获取了内网主页的所有新闻列表如下:");
        parseAndPrintHomepageNewsList(requestGetURL(ZZU_HOMEPAGE_URL, "UTF-8"));

        readingNews();
        //requestGetURL(ZZU_XBTZLIST_URL, "UTF-8");

        loggingOut();

        httpclient.close();
    }

    private static void loggingOut() throws IOException {
        String inStr;
        System.out.printf("是否要登出VPN ZZU?[回车]登出,[任意字符]不登出(下次运行将生成内网链接地址):");
        inStr = scanner.nextLine();
        if (inStr.trim().equals("")) {
            requestGetURL(VPN_LOGOUT_URL, "GBK");
            System.out.println("已经正常登出.");
        } else {
            System.out.println("未登出,下次运行将自动生成内网链接地址.");
        }

    }

    private static void readingNews() throws Exception {
        String inStr = null;
        while (true) {
            System.out.printf("\n请输入准备阅读的新闻 [回车/0]退出 [1-%d]第i条新闻 [l/L]主页新闻列表:", newsList.size());
            inStr = scanner.nextLine().trim();

            int index = 1;

            if (inStr.equals("0") || inStr.equals(""))
                break;
            if (inStr.equalsIgnoreCase("l")) {
                System.out.println("获取了内网主页的所有新闻列表如下:");
                parseAndPrintHomepageNewsList(requestGetURL(ZZU_HOMEPAGE_URL, "UTF-8"));
                continue;
            }
            index = Integer.valueOf(inStr);
            String indexURL = newsList.get(index - 1).get("href");
            String indexTitle = newsList.get(index - 1).get("title");
            System.out.println("\n新闻标题:\t" + indexTitle);

            String str = requestGetURL(indexURL, "UTF-8");


            Pattern pattern = Pattern.compile("<meta http-equiv=\"refresh\" content=\"0;url='(.*)'\">");
            Matcher matcher = pattern.matcher(str);

            if (matcher.find()) {
                String realNewsURL = matcher.group(1);
                str = requestGetURL(realNewsURL, "UTF-8");

            }
            System.out.printf("新闻内容:\t");
            parseAndPrintNews(str);
        }

    }

    private static void requestPassFromKYZZUNet() throws IOException {
        // get pass from internet connection
        do {
            stepForGetVCJPG(); //access for verify code at http://ky.zzu.edu.cn/keyans/zzjlogin3d.dll/zzjgetimg?ids=2344
            Runtime.getRuntime().exec("open " + VCODE_FILENAME); //open verify code
            System.out.printf("请输入获取的验证码:");
            String str = scanner.nextLine();

            if (stepForKYPostLoginForm(str, vpnPass))
                break; //post uid,pw,verified code to https://ky.zzu.edu.cn/keyanss/zzjlogin8.dll/login for VPN id and pw
        } while (true);

        FileOutputStream fos = new FileOutputStream(VPNPass_FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(vpnPass);
    }


    public static void prepareSSLHttpClient() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(new File("myzzuky.keystore"), "99094018".toCharArray(),
                        new TrustSelfSignedStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.NETSCAPE)
                .build();

        httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setDefaultRequestConfig(globalConfig)
                .build();
    }


    public static void stepForGetVCJPG() throws IOException {
        HttpGet httpget = new HttpGet(KY_GETVCJPG_URL);

        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();
            File file = new File(VCODE_FILENAME);
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[MAX_FILE_BUFFER];
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


    public static boolean stepForKYPostLoginForm(String str, VPNPass vpnPass) throws IOException {
        boolean flag;
        HttpPost httpPost = new HttpPost(KY_LOGINFORM_URL);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("uid", gonghao));
        nvps.add(new BasicNameValuePair("pw", mima));
        nvps.add(new BasicNameValuePair("verc", str));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            //match string 用户名：xoqe；密码：1234

            String htmlPage = EntityUtils.toString(entity2, new GBK());

            Pattern pattern = Pattern.compile("用户名：(.*)；密码：(\\d{4})");
            Matcher matcher = pattern.matcher(htmlPage);
            if (matcher.find()) {
                vpnPass.setPassID(matcher.group(1));
                vpnPass.setPassPW(matcher.group(2));
                flag = true;
            } else {
                System.out.println("输入工号,密码或验证码错误,重新获取图片,并检查工号密码是否正确.");
                flag = false;
            }
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
        return flag;
    }

    public static void postVPNLoginForm(String url, VPNPass vpnPass) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", vpnPass.getPassID()));
        nvps.add(new BasicNameValuePair("password", "1234"));
        nvps.add(new BasicNameValuePair("realm", "郑州大学用户REALM"));
        nvps.add(new BasicNameValuePair("tz_offset", "480"));
        nvps.add(new BasicNameValuePair("btnSubmit", "登录"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

//        System.out.println("======================================");
//        System.out.println(httpPost.getRequestLine());
//        System.out.println(Arrays.toString(httpPost.getAllHeaders()));
//
//        String postBody = EntityUtils.toString(httpPost.getEntity(), "UTF-8");
//        System.out.println(postBody);
//        System.out.println("======================================");
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
//            System.out.println("======================================");
//            System.out.println(response.getStatusLine());
//            System.out.println(Arrays.toString(response.getAllHeaders()));
//            System.out.println("======================================");
//

            // 检查是否重定向
            int statuscode = response.getStatusLine().getStatusCode();
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                // 读取新的 URL 地址
                Header header = response.getFirstHeader("location");
                if (header != null) {
                    String newURL = header.getValue();
                    if ((newURL == null) || (newURL.equals("")))
                        newURL = "/";
                    if (newURL.equals(VPN_HOMEPAGE_URL)) {//relogin sucess
                        System.out.println("再次登录成功.");
                        return;
                    }
                    if (newURL.equals(VPN_LOGIN_FAIL_URL)) {
                        System.out.println("原VPN账号已失效,请重新运行该软件,获取新的VPN账号.");
                        System.exit(1);
                    }
                    if (onlyGenLoginLink)
                        System.out.println("生成了VPN链接地址,可以使用浏览器访问以下地址:" + newURL);
                    else
                        System.out.println("请用浏览器登录链接地址,并登出后再运行该软件:" + newURL);

                } else
                    System.out.println("Invalid redirect");
                System.exit(1);
            }

            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String htmlPage = EntityUtils.toString(entity2, "GBK");
            //System.out.println(htmlPage);
            EntityUtils.consume(entity2);
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


    public static void parseAndPrintHomepageNewsList(String htmlStr) throws Exception {
        newsList = new ArrayList<>();
        Pattern pattern = Pattern.compile("<li><span class=\"news_data\">.*</a></li>\\r\\n");
        Matcher matcher = pattern.matcher(htmlStr);
        int i = 1;
        while (matcher.find()) {
            String strNews = matcher.group();
            System.out.printf("News [%d]: ", i++);

            Pattern pattern1 = Pattern.compile("<li><span class=\"news_data\">(.*):</span><a href=\"(.*)\" target=\"_blank\" .*>(.*)</a></li>");


            Matcher matcher1 = pattern1.matcher(strNews);
            while (matcher1.find()) {
                if (matcher1.groupCount() < 3)
                    throw new Exception();
                String date = matcher1.group(1);
                String title = matcher1.group(3);
                title = title.replaceAll("\\[\\d\\d.*\\]","");
                title = title.replaceAll("&#8226;"," ");
                String href = matcher1.group(2);
                HashMap<String, String> news = new HashMap<>();
                news.put("date", date);
                news.put("title", title);
                news.put("href", href);
                newsList.add(news);
                System.out.printf("%s\t%s", date, title);
            }
            System.out.printf("\n");
        }
    }


    public static void parseAndPrintNews(String htmlPage) {
        String txtNews;

        int i = htmlPage.indexOf("<div class=\"zzj_5\">");
        int j = htmlPage.indexOf("</div>", i);
        if ((i != -1) && (j != -1))
            txtNews = htmlPage.substring(i, j);
        else
            txtNews = htmlPage;
        txtNews = txtNews.replaceAll("&nbsp;", "");
        txtNews = txtNews.replaceAll("<[^>]*>", "");
        txtNews = txtNews.replaceAll("\\s+", " ").trim();
        System.out.println(txtNews);
    }

}
