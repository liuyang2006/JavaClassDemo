package ch07;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class J_ReadData {
    // 输出提示信息
    public static void mb_printIinfo() {
        System.out.println("输入整数还是浮点数?");
        System.out.println("\t0: 退出; 1: 整数; 2: 浮点数");
    } // 方法mb_printIinfo结束

    // 接受整数的输入
    public static int mb_getInt(BufferedReader f) {
        try {
            String s = f.readLine();
            int i = Integer.parseInt(s);
            return i;
        } catch (Exception e) {
            return -1;
        } // try-catch结构结束
    } // 方法mb_getInt结束

    // 接受浮点数的输入
    public static double mb_getDouble(BufferedReader f) {
        try {
            String s = f.readLine();
            double d = Double.parseDouble(s);
            return d;
        } catch (Exception e) {
            return 0d;
        } // try-catch结构结束
    } // 方法mb_getDouble结束

    public static void main(String args[]) {
        int i;
        double d;
        try {
            BufferedReader f =
                    new BufferedReader(new InputStreamReader(System.in));
            do {
                mb_printIinfo();
                i = mb_getInt(f);
                if (i == 0)
                    break;
                else if (i == 1) {
                    System.out.print("\t请输入整数: ");
                    i = mb_getInt(f);
                    System.out.println("\t输入整数: " + i);
                } else if (i == 2) {
                    System.out.print("\t请输入浮点数: ");
                    d = mb_getDouble(f);
                    System.out.println("\t输入浮点数: " + d);
                } // if-else结构结束
            }
            while (true); // do-while循环结束
            f.close();
        } catch (Exception e) {
            System.err.println("发生异常:" + e);
            e.printStackTrace();
        } // try-catch结构结束
    } // 方法main结束
} // 类J_ReadData结束
