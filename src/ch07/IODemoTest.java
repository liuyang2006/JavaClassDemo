package ch07;

import org.junit.Before;
import org.junit.Test;

public class IODemoTest {
    public static final String from = "面向对象-ch01.ppt";
    public static final String to = "ch01-copied.ppt";

    @Before
    public void setUp() throws Exception {
        IODemo.printStream = System.out;
    }

    @Test
    public void testCopyFile() throws Exception {
        IODemo.copyFile(from, to);
    }

    @Test
    public void testCopyFileWithBuffer() throws Exception {
        IODemo.copyFileWithBuffer(from, to);

    }

}