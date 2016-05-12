package ch07;

import java.io.File;

public class J_File {
    public static void main(String args[]) {
        for (int i = 0; i < args.length; i++) {
            File f = new File(args[i]);
            System.out.println();
            if (f.exists()) {
                System.out.println("getName: " + f.getName());
                System.out.println("getPath: " + f.getPath());
                System.out.println("getParent: " + f.getParent());
                System.out.println("length: " + f.length());
            } else System.out.printf("文件%1$s不存在%n", args[i]);
        } // for循环结束
    } // 方法main结束
} // 类J_File结束
