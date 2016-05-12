package ch07;

import java.io.*;
import java.net.URL;
import java.util.Date;

public class IODemo {

    public static final int ARRAY_SIZE = 128;

    public static PrintStream printStream;

    public static void encodeingConverter(InputStream inputStream, String inEncoding, OutputStream outputStream, String outEncoding) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, inEncoding);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, outEncoding);

        int count = 0;
        int ch;
        while ((ch = inputStreamReader.read()) != -1) {
            outputStreamWriter.write(ch);
            count++;
        }
        inputStreamReader.close();
        outputStreamWriter.close();
        inputStream.close();
        outputStream.close();
        printStream.printf("encodeingConverter:%d characters copyed from %s to %s.\n", count, inEncoding, outEncoding);
    }

    public static ByteArrayOutputStream getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int count = 0;
        byte[] byteArray = new byte[ARRAY_SIZE];
        int len;
        while ((len = inputStream.read(byteArray)) != -1) {
            byteArrayOutputStream.write(byteArray, 0, len);
            count += len;
        }
        inputStream.close();
        byteArrayOutputStream.close();
        printStream.printf("getBytes:%d bytes getted, using byte[%d] array.\n", count, ARRAY_SIZE);

        return byteArrayOutputStream;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        int ch;
        while ((ch = inputStream.read()) != -1) {
            outputStream.write(ch);
            count++;
        }
        inputStream.close();
        outputStream.close();
        printStream.printf("copyStream:%d bytes copyed.\n", count);
    }

    public static void copyStreamByByteArray(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        byte[] byteArray = new byte[ARRAY_SIZE];
        int len;
        while ((len = inputStream.read(byteArray)) != -1) {
            outputStream.write(byteArray, 0, len);
            count += len;
        }
        inputStream.close();
        outputStream.close();
        printStream.printf("copyStreamByByteArray:%d bytes copyed, using byte[%d] array.\n", count, ARRAY_SIZE);
    }

    public static void copyFile(String from, String to) throws IOException {
        InputStream inputStream = new FileInputStream(from);
        OutputStream outputStream = new FileOutputStream(to);

        int count = 0;
        int ch;
        while ((ch = inputStream.read()) != -1) {
            outputStream.write(ch);
            count++;
        }

        inputStream.close();
        outputStream.close();
        printStream.printf("copyFile:%d bytes copyed.\n", count);
    }

    public static void main(String[] args) throws IOException {

//        printStream = new PrintStream("IODemo.out");

        printStream = System.out;

        String dir = "/Users/liuyang/Downloads/";
        String from = "test.txt";
        String to = "after-copyied.txt";


        copyFile(from, to);

        copyStream(new FileInputStream(from), new FileOutputStream(to));

        copyStreamByByteArray(new FileInputStream(from), new FileOutputStream(to));

        ByteArrayOutputStream byteArrayOutputStream = getBytes(new FileInputStream(from));

        String fileContent = byteArrayOutputStream.toString();

        printStream.println("fileContent is as follows:\n" + fileContent);
        printStream.println("=== end of file ===\n");

        String urlStr = "http://www.zzu.edu.cn";

        URL url = new URL(urlStr);
        copyStream(url.openStream(), new FileOutputStream("zzu-homepage.html"));
//        copyStream(url.openStream(), System.out);


        urlStr = "http://www.zzu.edu.cn/images/logo.png";
        url = new URL(urlStr);

        copyStream(url.openStream(), new FileOutputStream("zzulogo.png"));

        Runtime.getRuntime().exec("open zzulogo.png");

        System.exit(0);

        printStream.printf("Copy file from \"%s\" to \"%s\" finished.\n", from, to);

        encodeingConverter(new FileInputStream(from), "utf-8", new FileOutputStream(to), "GBK");

        // Big file manipulation
        from = "面向对象-ch01.ppt";
        to = "test-test.ppt";

        // No buffer
        Date d1 = new Date();
        printStream.println("Starting copying without buffer...");
        copyStream(new FileInputStream(from), new FileOutputStream(to));
        Date d2 = new Date();

        printStream.println("No buffer copying, time consuming(ms):" + (d2.getTime() - d1.getTime()));
        printStream.println();

        // Using buffer to read and write
        int bufferSize = 512;
        d1 = new Date();
        printStream.println("Starting copying by buffer...");
        copyStream(new BufferedInputStream(new FileInputStream(from), bufferSize), new BufferedOutputStream(new FileOutputStream(to), bufferSize));
        d2 = new Date();

        printStream.println("Use buffer copying, time consuming(ms):" + (d2.getTime() - d1.getTime()));

        printStream.printf("Copy file from \"%s\" to \"%s\" finished.\n", from, to);
    }
}
