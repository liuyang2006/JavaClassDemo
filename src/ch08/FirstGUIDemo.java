package ch08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FirstGUIDemo {
    public static void main(String[] args) {
        JFrame window = new JFrame("Hello world");
        window.setSize(300, 300);
        window.setLocation(400, 300);

        window.setLayout(new FlowLayout());
        window.add(new JLabel("Username:"));
        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(100, 30));
        window.add(username);

        JButton btn = new JButton("Hello");

//        btn.addActionListener(new ActionListener());

//        btn.addActionListener(new MyInnerActionListener());

//        btn.addActionListener(new MyOutterActionListener());

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You click the button.");

                JOptionPane.showMessageDialog(window, "Hello " + username.getText(), "hello", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        window.add(btn);

//        JLabel pos = new JLabel();
//        window.add(pos);
//
//        window.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                pos.setText(e.getLocationOnScreen().toString());
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });

        window.setVisible(true);
    }


    static class MyInnerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

//            username.getText();

        }
    }


}

class MyOutterActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
//            username.getText();
    }
}