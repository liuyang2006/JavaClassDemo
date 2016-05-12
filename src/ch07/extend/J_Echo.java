package ch07.extend;

import java.io.IOException;
import java.io.InputStream;

public class J_Echo {
    public static void mb_echo(InputStream in) throws IOException {
        //InputStreamReader

        while (true) // 接受输入并回显
        {
            int b = in.read();
            if (b == -1) // 输入流结束
                break;
            if (b == 0xa)
                System.out.println();
            else {
                System.out.printf("%2x ", b);

                System.out.printf("%8s ", Integer.toBinaryString(b));
//                System.out.printf("%2s ", Integer.toHexString(b));
//                System.out.printf("%8s ", String.format("%8s", Integer.toBinaryString(i)).replaceAll(" ", "0"));

//                System.out.printf("%s ", String.format("%2s", Integer.toHexString(i)).replaceAll(" ", "0"));
            }
        } // while循环结束
        System.out.println();
    } // 方法mb_echo结束

    public static void simpleTest(){
        char mao = '猫';
        char gou = '狗';
        System.out.println(mao<gou);
    }
    public static void main(String args[]) throws IOException {
        mb_echo(System.in);
    }
} // 类J_Echo结束
