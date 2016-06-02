package ch11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class NetRequestInThreadDemo {

    public static boolean flagGetWebpage = false;

    public static final String HTTP_URL = "http://www.zzu.edu.cn";//http://news.zzu.edu.cn

//    public static final String ENCODING = "gb2312";

    public static void main(String[] args) throws InterruptedException {
        Thread netRequestWorker = new Thread(new Runnable() {
            @Override
            public void run() {

                String result = null;

                try {
                    URL url = new URL(HTTP_URL);
                    InputStreamReader isr = new InputStreamReader(url.openStream());
                    BufferedReader br = new BufferedReader(isr);

                    Thread.sleep(2000);
                    StringWriter sw = new StringWriter();
                    char[] buffer = new char[128];
                    int len;
                    while ((len = br.read(buffer)) != -1) {
                        sw.write(buffer, 0, len);
                    }
                    flagGetWebpage = true;
                    result = sw.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            }
        });

        Thread hintWorker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (flagGetWebpage) {
                            break;
                        }
                        System.out.printf(".");

                        Thread.sleep(150);


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Main thread is waiting for net request results");
        hintWorker.start();
        netRequestWorker.start();
    }
}
