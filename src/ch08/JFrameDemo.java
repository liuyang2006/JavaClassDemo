package ch08;

import javax.swing.*;
import java.awt.*;

public class JFrameDemo {
    public static void main(String s[]) {

        //指定使用当前的Look&Feel装饰窗口。必须在创建窗口前设定。
        JFrame.setDefaultLookAndFeelDecorated(true);

        //创建并设定关闭窗口操作。
        JFrame frame = new JFrame("JFrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //创建一个JLable并加到窗口中。
        JLabel emptyLabel = new JLabel("Hello");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.add(emptyLabel, BorderLayout.CENTER);

        //显示窗口。
        frame.pack();
        frame.setLocation(300, 300);
        frame.setVisible(true);
    }
}

