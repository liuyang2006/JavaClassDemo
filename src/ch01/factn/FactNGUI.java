package ch01.factn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactNGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 3979776648430940192L;

    JTextField tf = new JTextField();
    JButton b1 = new JButton("计算");
    private final JTextArea textArea = new JTextArea();

    public FactNGUI() {
        setTitle("计算阶乘举例");
        b1.setFont(new Font("宋体", Font.PLAIN, 26));
        b1.setActionCommand("start");
        JPanel p = new JPanel();
        p.add(b1);

        b1.addActionListener(this);
        tf.setFont(new Font("宋体", Font.PLAIN, 30));

        this.getContentPane().add(tf, "North");
        this.getContentPane().add(p, "South");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(637, 338);
        this.setLocation(300, 300);
        Cursor cu = new Cursor(Cursor.HAND_CURSOR);
        this.setCursor(cu);
        this.setVisible(true);
        tf.setText("");
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        getContentPane().add(textArea, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        String strN = tf.getText();
        int N = Integer.parseInt(strN);

        FactTool tool = new FactTool();
        textArea.setText(tool.getAllResults(N));
    }

    public static void main(String[] args) {
        new FactNGUI();
    }
}
