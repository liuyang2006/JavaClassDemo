package ch01.zzunews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPTool {

    private String html;

    public HTTPTool(String url) {
        html = connectAndGetHtml(url);
    }

    /**
     * Empty string returned if there is any network error.
     *
     * @return
     */
    public String GetHtml() {
        return html;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String url = "http://localhost:8080/FactNWeb/FactN?n=10";

        HTTPTool tool = new HTTPTool(url);

        System.out.println(tool.GetHtml());
    }

    private String connectAndGetHtml(String xwURL) {
        URL url;
        HttpURLConnection urlConn = null;
        InputStreamReader in = null;
        BufferedReader buffer = null;

        String resultHtml = "";

        try {
            url = new URL(xwURL);
            urlConn = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(urlConn.getInputStream());
            buffer = new BufferedReader(in);

            String inputLine = null;
            while ((inputLine = buffer.readLine()) != null) {
                resultHtml += inputLine + "\n";
            }
            buffer.close();
            in.close();
            urlConn.disconnect();
        } catch (MalformedURLException e) {
            System.err.println("请求的URL：" + xwURL);
            e.printStackTrace();
            resultHtml = "";
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("请求的URL：" + xwURL);
            System.err.println("网络无连接！");
            resultHtml = "";

        } finally {

        }
        return resultHtml;
    }
}
