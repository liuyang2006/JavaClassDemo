package ch07;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

public class IODemo {

    public static final int ARRAY_SIZE = 128;

    public static PrintStream printStream;

    public static void main(String[] args) throws IOException {
//        printStream = new PrintStream("IODemo.out");
        printStream = System.out;

        String from = "test.txt";
        String to = "after-copyied.txt";

        //===================
        printStream.printf("\nStart \"copyFile\" to copy file from %s to %s\n", from, to);
        copyFile(from, to);

        //===================
        printStream.printf("\nStart \"copyStream\" to copy file from %s to %s\n", from, to);
        copyStream(new FileInputStream(from), new FileOutputStream(to));
        printStream.printf("Copy file from \"%s\" to \"%s\" finished.\n", from, to);

        //===================
        printStream.printf("\nStart \"getBytes\" from file %s and convert bytes[] to String and output it.\n", from);
        ByteArrayOutputStream byteArrayOutputStream = getBytes(new FileInputStream(from));
        String fileContent = byteArrayOutputStream.toString("utf-8");
        printStream.println("fileContent is as follows:\n" + fileContent);
        printStream.println("=== output end of file ===\n");

        //===================
        printStream.printf("\nStart \"copyStream\" from internet connection to http://www.zzu.edu.cn.\n");
        String urlStr = "http://www.zzu.edu.cn";
        URL url = new URL(urlStr);
//        copyStream(url.openStream(), new FileOutputStream("zzu-homepage.html"));
        copyStream(url.openStream(), printStream);

        //===================
        printStream.printf("\nStart \"copyStream\" from internet connection to http://www.zzu.edu.cn/images/logo.png.\n");
        urlStr = "http://www.zzu.edu.cn/images/logo.png";
        url = new URL(urlStr);
        copyStream(url.openStream(), new FileOutputStream("zzulogo.png"));
//        Runtime.getRuntime().exec("open zzulogo.png");

        //===================
        printStream.printf("\nStart \"copyStreamWithEncoding\" from internet connection to http://news.zzu.edu.cn and convert to correct encoding output.\n");
        url = new URL("http://news.zzu.edu.cn");
//        copyStream(url.openStream(), printStream);
//        copyCharacterBasedStream(new InputStreamReader(url.openStream(), "gb2312"), new PrintWriter(printStream));
        copyStreamWithEncoding(url.openStream(), "gb2312", printStream, "utf-8");

//        System.exit(0);

        //===================
        printStream.printf("\nStart \"encodeingConverter\" convert file %s with %s to %s with %s.\n", from, "utf-8", to, "GBK");
        encodeingConverter(new FileInputStream(from), "utf-8", new FileOutputStream(to), "GBK");

        //===================
        printStream.printf("\nStart big file manipulation.\n");
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
        inputStream.close();
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
        printStream.printf("getBytes:%d bytes got, using byte[%d] array.\n", count, ARRAY_SIZE);
        return byteArrayOutputStream;
    }

    public static void stringAndBytesConversion() throws UnsupportedEncodingException {
        String abc = "abc";
        byte[] abcBytes = abc.getBytes();
        printStream.println(Arrays.toString(abcBytes));

        abc = new String(abcBytes, "utf-8");
        printStream.println(abc);
    }
    // Reader Writer


    public static void copyStreamWithEncoding(InputStream in, String inEncoding, OutputStream out, String outEncoding) throws IOException {
        int count = 0;
        char[] buff = new char[ARRAY_SIZE];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, inEncoding));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, outEncoding));

        int len;
        while ((len = bufferedReader.read(buff)) != -1) {
            bufferedWriter.write(buff, 0, len);
            count += len;
        }
        bufferedReader.close();
        printStream.printf("\nCopyied %d characters with BufferedReader/Writer from %s to %s encoding conversion.\n", count, inEncoding, outEncoding);
    }


    public static void copyStreamWithBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        byte[] buff = new byte[ARRAY_SIZE];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        int len;
        while ((len = bufferedInputStream.read(buff)) != -1) {
            bufferedOutputStream.write(buff, 0, len);
            count += len;
        }
        bufferedInputStream.close();
        inputStream.close();
        printStream.printf("Copyied %d bytes with BufferedInput/OuputStream.\n", count);
    }


    /**
     * Please use copyStreamWithBuffer for performance reason.
     *
     * @author zzu ie
     * @see #copyStreamWithBuffer(InputStream, OutputStream)
     * @since JDK1.7
     * @deprecated This method is replaced by  <code>copyStreamWithBuffer</code>.
     */
    @Deprecated
    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        byte[] buff = new byte[ARRAY_SIZE];

        int len;
        while ((len = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
            count += len;
        }
        inputStream.close();
        outputStream.flush();
        printStream.printf("Copyied %d bytes.\n", count);
    }

    public static void copyCharacterBasedStream(Reader reader, Writer writer) throws IOException {
        int count = 0;
        char[] buff = new char[ARRAY_SIZE];

        int len;
        while ((len = reader.read(buff)) != -1) {
            writer.write(buff, 0, len);
            count += len;
        }
        reader.close();
        writer.flush();
        printStream.printf("Copyied %d characters.\n", count);
    }

    /**
     * Copy from input stream into output stream byte by byte.
     * 按字节方式从输入流拷贝数据到输出流中.
     *
     * @author zzu ie
     * @see #copyFileWithBuffer(String, String)
     * @since JDK1.7
     * @deprecated This method is replaced by  <code>copyFileWithBuffer</code>.
     */
    @Deprecated
    private static void copyFile(String from, String to) throws IOException {
        Date d1 = new Date();
        copyStream(new FileInputStream(from), new FileOutputStream(to));
        Date d2 = new Date();
        printStream.printf("Copy file %s to %s used time is %d  (ms) successfully.\n", from, to, (d2.getTime() - d1.getTime()));
    }

    private static void copyFileWithBuffer(String from, String to) throws IOException {
        Date d1 = new Date();
        copyStreamWithBuffer(new FileInputStream(from), new FileOutputStream(to));
        Date d2 = new Date();
        printStream.printf("Copy file %s to %s used time is %d  (ms) with buffered stream successfully.\n", from, to, (d2.getTime() - d1.getTime()));
    }

}
