package ch07;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class J_WriteFile {
    public static void main(String args[]) throws UnsupportedEncodingException {
        String s = "文件输出流例程";
        byte[] b = s.getBytes("utf-8");
        try {
            FileOutputStream f = new FileOutputStream("out.txt");
            f.write(b);
            f.flush();
            f.close();
        } catch (IOException e) {
            System.err.println("发生异常:" + e);
            e.printStackTrace();
        } // try-catch结构结束
    } // 方法main结束
} // 类J_WriteFile结束
