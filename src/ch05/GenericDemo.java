package ch05;

import java.io.*;
import java.util.*;

public class GenericDemo {

    public static final String STUDENTS_SER_FILENAME = "students.ser";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int n = 10;

        Vector v = new Vector();
        v.add(1);
        v.add(3.1415926);
        v.add("zzu");
        System.out.println("不使用范型的向量可以存储任意类型对象 v=" + v);
        int a1 = (int) v.get(0);
        double a2 = (double) v.get(1);
        String a3 = (String) v.get(2);


        Vector<Integer> vector = new Vector();
        for (int i = 0; i < n; i++) {
            vector.add(i);
        }

        printVector1(vector);
        printVector2(vector);
        printVector3(vector);

        Collections.shuffle(vector);
        System.out.println("\n洗牌算法后向量\t" + vector);

        Collections.sort(vector);
        System.out.println("\n排序后向量\t" + vector);

        vector.add(n);
        vector.add(n + 1);
        vector.remove(n);
        //...

        int m = 5;
        Random random = new Random();
        Student[] students = new Student[m];
        for (int i = 0; i < m; i++) {
            students[i] = new Student();
            students[i].sid = i;
            students[i].sname = "A" + (char) (random.nextInt(26) + 'A');
            students[i].grade = random.nextInt(40) + 60;
        }

        System.out.println("\n随机学生数据:\t" + Arrays.toString(students));
        Arrays.sort(students, new SidComprator());
        System.out.println("\n学号排序结果:\t" + Arrays.toString(students));

        Arrays.sort(students, new SnameComprator());
        System.out.println("\n姓名排序结果:\t" + Arrays.toString(students));

        Arrays.sort(students, new SGradeComprator());
        System.out.println("\n成绩排序结果:\t" + Arrays.toString(students));

        List<Student> studentList;
        studentList = Arrays.asList(students);
        System.out.println("\nStu数组转List:\t" + studentList.toString());

        saveStudents(studentList, STUDENTS_SER_FILENAME);
        System.out.println("保存 学生List 成功!");

        List<Student> list2;
        list2 = loadStudents(STUDENTS_SER_FILENAME);
        System.out.println("读取 学生List 结果为:\t" + list2.toString());

        System.out.printf("Fori循环列表的结果:\t");
        for (int i = 0; i < list2.size(); i++) {
            Student stu = (Student) list2.get(i);
            System.out.printf(stu + " ");
        }
        System.out.println();


        System.out.printf("Foreach循环列表的结果:\t");
        for (Student stu : list2) {
            System.out.printf(stu + " ");
        }
        System.out.println();

        System.out.printf("Iterator遍历列表的结果:\t");
        Iterator iterator = list2.iterator();
        while (iterator.hasNext()) {
            Student stu = (Student) iterator.next();
            System.out.printf(stu + " ");
        }
        System.out.println();

        System.exit(0);
    }

    private static void printVector3(Vector<Integer> vector) {
        System.out.printf("\nIterator printVector:\t");
        Iterator<Integer> it = vector.iterator();
        while (it.hasNext()) {
            System.out.printf("%d ", it.next());
        }
        System.out.println();
    }


    private static void printVector2(Vector<Integer> vector) {
        System.out.printf("\nforeach printVector:\t");
        for (Integer ai : vector) {
            System.out.printf("%d ", ai);
        }
        System.out.println();
    }

    private static void printVector1(Vector<Integer> vector) {
        System.out.printf("\nfori printVector:\t");
        for (int i = 0; i < vector.size(); i++) {
            System.out.printf("%d ", vector.get(i));
        }
        System.out.println();
    }

    private static void saveStudents(List<Student> stuList, String fileName) throws IOException {
        FileOutputStream os = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(stuList);
    }

    private static List<Student> loadStudents(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Student> stuList = (List<Student>) ois.readObject();
        return stuList;
    }
}

class Student implements Serializable {
    int sid;
    String sname;
    int grade;

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", grade=" + grade +
                '}';
    }
}

class SidComprator implements Comparator<Student> {
    public int compare(Student arg0, Student arg1) {
        return arg0.sid - arg1.sid;
    }
}

class SnameComprator implements Comparator<Student> {
    public int compare(Student arg0, Student arg1) {
        return arg0.sname.compareTo(arg1.sname);
    }
}

class SGradeComprator implements Comparator<Student> {
    public int compare(Student arg0, Student arg1) {
        return arg1.grade - arg0.grade;
    }
}