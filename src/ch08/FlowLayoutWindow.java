package ch08;

import java.awt.*;
import javax.swing.*;

public class FlowLayoutWindow extends JFrame {
      
    public FlowLayoutWindow() {
        setLayout(new FlowLayout());
        add(new JLabel("Buttons:"));
        add(new JButton("Button 1"));
        add(new JButton("2"));
        add(new JButton("Button 3"));
        add(new JButton("Long-Named Button 4"));
        add(new JButton("Button 5"));
        
    }
 
    public static void main(String args[]) {
        FlowLayoutWindow window = new FlowLayoutWindow();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("FlowLayoutWindow Application");
        window.pack();//���ڵĴ�С����Ϊ�ʺ������ѳߴ��벼������Ŀռ䡣
        window.setVisible(true);
    }
}
