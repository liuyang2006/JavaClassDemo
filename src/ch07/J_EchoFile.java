package ch07;


import java.io.*;

public class J_EchoFile {
    public static void main(String args[]) throws IOException {
            FileInputStream f = new FileInputStream("test.txt");
            InputStreamReader isr = new InputStreamReader(f, "utf-8");
            int i;
            int b = isr.read();
            for (i = 0; b != -1; i++) {
                System.out.print((char) b);
                b = isr.read();
            } // for循环结束
            System.out.println();
            System.out.println("文件\"test.txt\"字符数为" + i);
            f.close();
    } // 方法main结束
} // 类J_EchoFile结束
