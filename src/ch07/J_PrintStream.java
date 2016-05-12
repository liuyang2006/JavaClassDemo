package ch07;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class J_PrintStream {
    public static void main(String args[]) throws UnsupportedEncodingException {
        try {
            PrintStream f = new PrintStream("out.txt");
            f.printf("输出结果:%1$d+%2$d=%3$d", 1, 2, (1 + 2));
            f.close();
        } catch (FileNotFoundException e) {
            System.err.println("发生异常:" + e);
            e.printStackTrace();
        } // try-catch结构结束
    } // 方法main结束
} // 类J_PrintStream结束
