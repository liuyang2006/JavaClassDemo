package net.advanced;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class HttpUrlConnectionBasics {

    private static final int CONNECTION_TIME_OUT = 3000;
    private static final int READ_TIME_OUT = 3000;

    public static void main(String[] args) {
//        String urlStr = "http://www.zzu.edu.cn";
//        System.out.println(getHtmlByHttp(urlStr));

        String postURLStr = "http://localhost:8080/ReceiveObjectServlet";
        System.out.println(postHtmlByHttp(postURLStr));
    }

    private static String getHtmlByHttp(String getUrl) {
        URL url;
        HttpURLConnection http = null;
        BufferedReader bufferReader = null;

        StringBuffer resultHtml = new StringBuffer();

        try {
            url = new URL(getUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(CONNECTION_TIME_OUT);
            http.setReadTimeout(READ_TIME_OUT);
            http.setRequestMethod("GET");
//            http.setRequestProperty("Host", "www16.zzu.edu.cn"); // 这里是设置head中的host

            http.setRequestProperty("Content-type", "application/x-java-serialized-object");
            http.connect();

            if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("请求的URL：" + getUrl + " 失败，getResponseCode=" + http.getResponseCode());
                http.disconnect();
                return "";
            }

            if (!http.getContentType().contains("text/html")) {
                System.err.println("请求的URL：" + getUrl + " 失败, 返回了非html文档，getContentType=" + http.getContentType());
                http.disconnect();
                return "";
            }

            bufferReader = new BufferedReader(new InputStreamReader(http.getInputStream()));

            String inputLine = null;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultHtml.append(inputLine).append("\n");
            }
            bufferReader.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            System.err.println("请求的URL：" + getUrl);
            e.printStackTrace();
            return "";
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            System.err.println("请求的URL：" + getUrl);
            System.err.println("网络无连接！");
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("请求的URL：" + getUrl);
            System.err.println("网络无连接！");
            return "";
        } finally {

        }
        return resultHtml.toString();
    }

    private static String postHtmlByHttp(String getUrl) {
        URL url;
        HttpURLConnection http = null;
        BufferedReader bufferReader = null;

        StringBuffer resultHtml = new StringBuffer();

        try {
            url = new URL(getUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(CONNECTION_TIME_OUT);
            http.setReadTimeout(READ_TIME_OUT);
            http.setRequestMethod("POST");
//            http.setRequestProperty("Host", "www16.zzu.edu.cn"); // 这里是设置head中的host
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestProperty("Content-type", "application/x-java-serialized-object");
            http.connect();

            OutputStream outStrm = http.getOutputStream();
            ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
            objOutputStrm.writeObject(new String("Hello from java zzu."));
            objOutputStrm.writeObject(new Date());
            objOutputStrm.writeObject(new Integer(6));
            objOutputStrm.writeObject(null);


            objOutputStrm.flush();
            objOutputStrm.close();


            if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("请求的URL：" + getUrl + " 失败，getResponseCode=" + http.getResponseCode());
                http.disconnect();
                return "";
            }

            bufferReader = new BufferedReader(new InputStreamReader(http.getInputStream()));

            String inputLine = null;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultHtml.append(inputLine).append("\n");
            }
            bufferReader.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            System.err.println("请求的URL：" + getUrl);
            e.printStackTrace();
            return "";
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            System.err.println("请求的URL：" + getUrl);
            System.err.println("网络无连接！");
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("请求的URL：" + getUrl);
            System.err.println("网络无连接！");
            return "";
        } finally {

        }
        return resultHtml.toString();
    }
}
