package ch08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FirstGUIDemo extends JFrame {

    public JTextField username;

    public FirstGUIDemo() {
        super("Hello world");
        setSize(400, 400);
        setLocation(400, 300);

        setLayout(new FlowLayout());
        add(new JLabel("Username:"));

        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(100, 30));
        add(username);

        this.username = username;

        JButton btn = new JButton("Hello");


        btn.addActionListener(new MyInnerActionListener());

        btn.addActionListener(new MyOutterActionListener());

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You click the button.");

                System.out.println(Thread.currentThread());

                JOptionPane.showMessageDialog(FirstGUIDemo.this, "Hello " + username.getText(), "hello", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        add(btn);


        //Mouse listener demo
        JLabel pos = new JLabel();
        add(pos);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pos.setText(e.getPoint().toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        FirstGUIDemo firstGUIDemo = new FirstGUIDemo();
        System.out.println("main finished.");
    }


    class MyInnerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("actionPerformed in MyInnerActionListener");
            username.getText();

        }
    }


}

class MyOutterActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed in MyOutterActionListener");

//        System.out.println(Thread.currentThread());
//        username.getText();
    }
}