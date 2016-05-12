package ch01.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//工具运行方法:   java ch01.game.GoodLuckyStudent java2016.txt
class Student {
    private String sno;
    private String sname;

    public Student(String sno, String sname) {
        this.sno = sno;
        this.sname = sname;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "学号 " + sno + " 姓名 " + sname;
    }
}

class Course {
    private String info;

    public ArrayList<Student> getStudents() {
        return students;
    }

    private ArrayList<Student> students;
    private Random random = new Random();

    private Course() {
        students = new ArrayList<>();
    }

    public static Course buildCourseFromFile(String fn) {
        Course course = new Course();

        try {
            //Scanner sin = new Scanner(new BufferedReader(new FileReader(fn)));
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fn), "UTF-8"); // ensure utf-8 file read in every platform
            Scanner sin = new Scanner(isr);
            String header = sin.nextLine();
            course.setInfo(header);

            while (sin.hasNext()) {
                String sno = sin.next();
                String sname = sin.next();

                Student student = new Student(sno, sname);
                course.addStudent(student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("名单文件没有找到.");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return course;
    }

    public String getGeneralInfo() {
        return info + ", " + "共" + students.size() + "人";
    }

    private void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student stu) {
        if (stu == null)
            return;
        students.remove(stu);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Student nextRandomStudent() {

        if (students.size() == 0)
            return null;

        //int i = (int) (Math.random() * students.size());
        int i = random.nextInt(students.size());

        return students.get(i);
    }
}

public class GoodLuckyStudent extends JFrame implements ActionListener {

    private static final long serialVersionUID = 3979776648430940192L;

    private JLabel snoLable = new JLabel("学号");
    private JLabel snameLable = new JLabel("姓名");
    private JTextArea infoTextArea = new JTextArea();
    private JButton button = new JButton("随机名单");
    private JScrollPane scrollPane = new JScrollPane(infoTextArea);

    private Course course;
    private Thread randomListStudentsThread;
    private Thread inputMonitorThread;
    private int step = 0;
    private RandomListStudents randomTask;

    public GoodLuckyStudent(String fn) {

        new Thread(new Runnable() {
            @Override
            public void run() { // ensure file reading work in a single thread
                course = Course.buildCourseFromFile(fn);
                System.out.println(course.getGeneralInfo());

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        infoTextArea.setText(course.getGeneralInfo() + "\n");
                    }
                });
            }
        }).start();


        setTitle("班级抽查点名演示");
        setLayout(new GridLayout(2, 2));

        button.setFont(new Font("宋体", Font.PLAIN, 32));
        button.setActionCommand("start");
        button.setForeground(Color.RED);
        button.addActionListener(this);

        snoLable.setFont(new Font("宋体", Font.BOLD, 38));
        snoLable.setForeground(Color.BLUE);
        snameLable.setFont(new Font("宋体", Font.BOLD, 48));

        infoTextArea.setFont(new Font("宋体", Font.PLAIN, 18));
        infoTextArea.setLineWrap(true);

        add(snoLable);
        add(snameLable);
        add(scrollPane);
        add(button);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 480);
        this.setLocation(300, 200);
        Cursor cu = new Cursor(Cursor.HAND_CURSOR);
        this.setCursor(cu);
        this.setVisible(true);

        inputMonitorThread = new Thread(new InputMonitor());
        inputMonitorThread.start();
    }

    void showInScreen(Student stu) {
        snoLable.setText(stu.getSno());
        snameLable.setText(stu.getSname());
    }

    public static void main(String[] args) {
        //new GoodLuckyStudent(args[0]);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (args.length >= 1)
                    new GoodLuckyStudent(args[0]);
                else
                    System.err.println("缺少学生名单文件名作为程序参数.");
            }
        });
    }

    void startRandomProcess() {

        if (course.getStudents().size() == 0) {
            infoTextArea.append("该课程没有学生或已经抽查完毕.\n");
            System.out.println("该课程没有学生或已经抽查完毕.\n");
            return;
        }
        button.setText("抽查");
        button.setActionCommand("stop");

        randomTask = new RandomListStudents();
        randomListStudentsThread = new Thread(randomTask);
        randomListStudentsThread.start();
    }

    void stopRandomProcess() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                randomListStudentsThread.interrupt();
                try {
                    randomListStudentsThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();// wait are not allowed in EDT thread.
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        synchronized (course) {
                            course.removeStudent(randomTask.currentSelected);

                            showInScreen(randomTask.currentSelected);
                            button.setText("随机名单");
                            button.setActionCommand("start");

                            step++;
                            String str = step + ":" + randomTask.currentSelected;
                            infoTextArea.append(str + "\n");
                            infoTextArea.selectAll();
                            infoTextArea.setCaretPosition(infoTextArea.getSelectedText().length());
                            //infoTextArea.requestFocus();
                        }
                    }
                });
            }
        }).start();


    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("start")) {
            startRandomProcess();
        } else {
            stopRandomProcess();
        }
    }

    private class InputMonitor implements Runnable {
        public InputMonitor() {
        }

        @Override
        public void run() {
            Scanner input = new Scanner(System.in);
            while (!Thread.currentThread().isInterrupted()) {
                input.nextLine();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (button.getActionCommand().equals("start")) {
                            startRandomProcess();
                        } else {
                            stopRandomProcess();
                        }
                    }
                });

            }
        }
    }

    private class RandomListStudents implements Runnable {

        public Student currentSelected = null;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                currentSelected = course.nextRandomStudent();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        showInScreen(currentSelected);
                    }
                });

                System.out.print("\r" + currentSelected);

                try {
                    Thread.sleep(75);
                    //Thread.yield();
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    break;
                }
            }
            System.out.println();
        }

    }
}
