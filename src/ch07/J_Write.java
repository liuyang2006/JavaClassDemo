package ch07;

import java.io.IOException;
import java.io.OutputStream;

public class J_Write
{
    public static void mb_write(OutputStream out)
    {
        String s = "输出流例程";
        byte[ ] b = s.getBytes( );
        try
        {
            out.write(b);
            out.flush( );
        }
        catch (IOException e)
        {
            System.err.println("发生异常:" + e);
            e.printStackTrace( );
        } // try-catch结构结束
    } // 方法mb_write结束

    public static void main(String args[ ])
    {
        mb_write(System.out);
    } // 方法main结束
} // 类J_Write结束
