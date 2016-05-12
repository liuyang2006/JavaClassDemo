package ch07;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class J_BufferedInputStream {

    public static final String m_dirName = "/Users/liuyang/Downloads/";

    private static String m_fileName = "面向对象-ch01.ppt";

    public static void main(String args[]) throws IOException {
        int i, ch;
        i = 0;
        Date d1 = new Date();
        System.out.println("不带缓存的方法正在读取文件" + m_dirName + m_fileName);
        FileInputStream f = new FileInputStream(m_dirName + m_fileName);
        while ((ch = f.read()) != -1) // read entire file
            i++;
        f.close();
        Date d2 = new Date();

        long t = d2.getTime() - d1.getTime(); // 单位(毫秒)
        System.out.printf("不带缓存的方法读取了文件\"%1$s\"(共%2$d字节)%n",
                m_fileName, i);
        System.out.printf("不带缓存的方法需要%1$d毫秒%n", t);

        i = 0;
        d1 = new Date();
        System.out.println("\n带缓存的方法正在读取文件" + m_dirName + m_fileName);
        f = new FileInputStream(m_dirName + m_fileName);
        BufferedInputStream fb = new BufferedInputStream(f);
        while ((ch = fb.read()) != -1) // read entire file
            i++;
        fb.close();
        d2 = new Date();

        t = d2.getTime() - d1.getTime(); // 单位(毫秒)
        System.out.printf("带缓存的方法读取了文件\"%1$s\"(共%2$d字节)%n",
                m_fileName, i);
        System.out.printf("带缓存的方法需要%1$d毫秒%n", t);
    } // 方法main结束
} // 类J_BufferedInputStream结束
