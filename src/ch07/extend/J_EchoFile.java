package ch07.extend;


import java.io.FileInputStream;
import java.io.IOException;

public class J_EchoFile {
    public static void main(String args[]) throws IOException {
        FileInputStream f = new FileInputStream("test.txt");//11.jpg
        int i;
        int b = f.read();
        for (i = 0; b != -1; i++) {

            if (b > 0xff) {
                throw new IllegalStateException("read more than 0xff.");
            }

            System.out.printf("%2x ", b);

//            System.out.printf("%2s ", Integer.toHexString(b));

//            System.out.printf("%s ", String.format("%2s", Integer.toHexString(b)).replaceAll(" ", "0"));


//            System.out.printf("%8s ", String.format("%8s", Integer.toBinaryString(b)).replaceAll(" ", "0"));

            b = f.read();
        } // for循环结束
        System.out.println();
        System.out.println("文件\"test.txt\"字节数为" + i);
        f.close();
    } // 方法main结束
} // 类J_EchoFile结束
