/**
 * 2005-8-28
 */
package ch01.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @author jenny
 */
public class MyGuessGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 288623859145195955L;
    JTextField tf = new JTextField();
    JButton b1 = new JButton("开局");
    JLabel jl = new JLabel();
    int m;// 存放随机数变量
    int count; // 存放猜数次数变量
    int oldNumber; // 存放原有纪录次数变量
    boolean isEnd; // 标志是否破纪录变量

    public MyGuessGame() {
        b1.setFont(new Font("宋体", Font.PLAIN, 30));
        b1.setActionCommand("start");
        JPanel p = new JPanel();
        p.add(b1);
        b1.addActionListener(this);
        tf.setFont(new Font("宋体", Font.PLAIN, 30));
        tf.addActionListener(this);
        tf.setEnabled(false);
        this.getContentPane().add(tf, "North");
        jl.setFont(new Font("宋体", Font.PLAIN, 30));
        this.getContentPane().add(jl);
        this.getContentPane().add(p, "South");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(531, 398);
        this.setLocation(300, 300);
        this.setVisible(true);
    }

    public int getNumber() {
        int m = (int) (Math.random() * 100) + 1;// 产生1~100之间的随机整数
        return m;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        int n = 0; // 存放用户所猜数字的变量
        if (s.equals("start")) {
            isEnd = false;
            count = 0;
            m = this.getNumber();
            System.out.print(m);
            b1.setEnabled(false);
            tf.setEnabled(true);
            jl.setText("请输入1-100之间您猜的数");
            tf.requestFocus();
            oldNumber = readRecord();
        } else {
            if (!isEnd) { // 控制是否结束存储完记录的标志位变量
                count++;
                String sn = tf.getText();
                try {
                    n = Integer.parseInt(sn);
                } catch (NumberFormatException e1) {
                    jl.setText("请输入数字");
                    return;
                }

                if (n < m) {
                    jl.setText("您猜的数偏小");
                    return;
                } else if (n > m) {
                    jl.setText("您猜的数偏大");
                    return;
                } else {
                    jl.setText("恭喜您猜对了,所花次数为" + count);
                    tf.setText("");
                    b1.setEnabled(true);
                    if (oldNumber > count) {
                        jl.setText("您破记录了,请在文本框输入您的姓名");
                        isEnd = true;
                    }
                }
            } else {
                String name = tf.getText();
                this.saveRecord(name, count);
                jl.setText("您的记录已经记录在册,继续努力!");
                tf.setText("");
                b1.setEnabled(true);

            }

        }

    }

    public void saveRecord(String name, int count) {
        File f1 = new File("record.txt");
        try {
            FileWriter fout = new FileWriter(f1);
            PrintWriter bw = new PrintWriter(fout);
            bw.println(count);
            bw.println(name);
            bw.close();
            fout.close();
        } catch (java.io.FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public int readRecord() {
        int count = 100;
        File f1 = new File("record.txt");
        try {
            FileReader fin = new FileReader(f1);
            BufferedReader br = new BufferedReader(fin);
            String s = br.readLine();
            count = Integer.parseInt(s);
            br.close();
            fin.close();
        } catch (java.io.FileNotFoundException e) {
        } catch (IOException e) {
        }
        return count;
    }

    public static void main(String[] args) {
        new MyGuessGame();
    }
}
