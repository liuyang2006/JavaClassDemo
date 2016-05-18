package ch08;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIDemo {
    private JButton btn_login;
    private JPanel panel1;
    private JButton btn_cancel;
    private JTextField username;
    private JTextField password;

    public GUIDemo() {
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello " + username.getText());
                if (username.getText().equals("zzu"))
                    JOptionPane.showMessageDialog(null, "Hello " + username.getText(), "Hello world", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Log in fail.", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                username.setText("");
                password.setText("");
            }
        });
        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI Demo");
        frame.setContentPane(new GUIDemo().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(400, 300);
    }

}
