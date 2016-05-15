package ch08;

import javax.swing.*;
import java.awt.event.*;  

public class HelloWorldSwing1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorldSwing");
        final JLabel label = new JLabel("Hello World!");
        frame.getContentPane().add(label);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(200,70);
        frame.setVisible(true);
    }
}