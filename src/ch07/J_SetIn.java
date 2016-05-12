package ch07;

import java.io.FileInputStream;

public class J_SetIn {
    public static void main(String args[]) {
        try {
            System.setIn(new FileInputStream("test.txt"));
//            J_Echo.mb_echo(System.in, "utf-8");
        } catch (Exception e) {
            System.err.println("发生异常:" + e);
            e.printStackTrace();
        } // try-catch结构结束
    } // 方法main结束
} // 类J_SetIn结束
