package ch01.zzunews;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liuyang on 16/4/2.
 */
public class NewsConfig {

    public static String[] typeName;
    public static String[] typeUrl;
    public final static String pagePrefix = "&tts=&tops=&pn=";

    public static int getTypeId(String inStr) {
        for (int i = 0; i < typeName.length; i++) {
            if (inStr.equals(typeName[i]))
                return i;
        }
        return 0;
    }

    public static void getConfigs(String[] typeName, String[] typeUrl) {
        NewsConfig.typeName = typeName;
        NewsConfig.typeUrl = typeUrl;
    }
    public static void getConfigs(String filename) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            prop.load(isr);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        int n = prop.size();
        typeName = new String[n];
        typeUrl = new String[n];

        int i = 0;
        Iterator it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            typeName[i] = key.toString();
            typeUrl[i] = value.toString();
            i++;
        }
    }
}
