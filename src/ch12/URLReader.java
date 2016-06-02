package ch12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader {
    public static void main(String[] args) throws Exception {

        URL zzu = new URL("http://www.zzu.edu.cn");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(zzu.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        in.close();
    }
}
