package ch07;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class J_PrintWriter {
    public static void main(String args[]) {
        try {
            PrintWriter f = new PrintWriter("out.txt");
            f.println("莫等闲，白了少年头，空悲切");
            f.close();
        } catch (FileNotFoundException e) {
            System.err.println("发生异常:" + e);
            e.printStackTrace();
        } // try-catch结构结束
    } // 方法main结束
} // 类J_PrintWriter结束
