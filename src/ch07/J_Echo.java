package ch07;

import java.io.IOException;
import java.io.InputStream;

public class J_Echo {
    public static void mb_echo(InputStream in) throws IOException {
        while (true) // 接受输入并回显
        {
            int i = in.read();
            if (i == -1) // 输入流结束
                break;
            char c = (char) i;
            System.out.print(c);
        } // while循环结束
        System.out.println();
    } // 方法mb_echo结束

    public static void main(String args[]) throws IOException {
        mb_echo(System.in);
    } // 方法main结束
} // 类J_Echo结束
