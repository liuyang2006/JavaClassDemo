package ch04.string;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ArraysUtilDemo {

    public static final String STUDENTS_SER_FILENAME = "students.ser";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int n = 100;
        int a[] = new int[n];
        Vector vector;
        vector = new Vector();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int j = random.nextInt(n);
            a[i] = j;
            vector.add(i);
        }

        System.out.println("随机数组\t" + Arrays.toString(a));
        Arrays.sort(a);
        System.out.println("\n排序后数组\t" + Arrays.toString(a));

        printArray1(a);
        printArray2(a);

        System.out.println("\n有序向量\t" + vector.toString());
        Collections.shuffle(vector);
        System.out.println("\n洗牌算法后向量\t" + vector);
        Collections.sort(vector);
        System.out.println("\n排序后向量\t" + vector);
        vector.add(n);
        vector.add(n + 1);
        vector.remove(n);
        //...

        printVector(vector);

        List intList;
        intList = Arrays.stream(a).boxed().collect(Collectors.toList());
        System.out.println("\nint数组转List\t" + intList);

        Integer[] A = new Integer[intList.size()];
        intList.toArray(A);
        System.out.println("\nList转Integer数组\t" + Arrays.toString(A));

        A = Arrays.stream(a).boxed().collect(Collectors.toList()).toArray(new Integer[0]);
        System.out.println("\nint数组转Integer数组\t" + Arrays.toString(A));

        intList = Arrays.asList(A);
        System.out.println("\nInteger数组转List\t" + intList);

        int m = 26;
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

        List studentList;
        studentList = Arrays.asList(students);
        System.out.println("\nStu数组转List:\t" + studentList.toString());

        students = (Student[]) studentList.toArray();
        System.out.println("\nList转Stu数组:\t" + Arrays.toString(students));

        saveStudents(studentList, STUDENTS_SER_FILENAME);
        System.out.println("保存 学生List 成功!");

        List list2;
        list2 = loadStudents(STUDENTS_SER_FILENAME);
        System.out.println("读取 学生List 结果为:\t" + list2.toString());

        System.out.printf("Fori循环列表的结果:\t");
        for (int i = 0; i < list2.size(); i++) {
            Student stu = (Student) list2.get(i);
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

    private static void printVector(Vector<Integer> vector) {
        System.out.println("\nfor each printVector:\t");
        for (Integer v : vector) {
            System.out.printf("%d ", v);
        }
        System.out.println();
    }

    private static void printArray2(int[] a) {
        System.out.printf("\nforeach printIntArray:\t");
        for (int ai : a) {
            System.out.printf("%d ", ai);
        }
        System.out.println();
    }

    private static void printArray1(int[] a) {
        System.out.printf("\nfor loop printArray:\t");
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
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

class SidComprator implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Student t1 = (Student) arg0;
        Student t2 = (Student) arg1;
        return t1.sid - t2.sid;
    }
}

class SnameComprator implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Student t1 = (Student) arg0;
        Student t2 = (Student) arg1;
        return t1.sname.compareTo(t2.sname);
    }
}

class SGradeComprator implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Student t1 = (Student) arg0;
        Student t2 = (Student) arg1;
        return t2.grade - t1.grade;
    }
}