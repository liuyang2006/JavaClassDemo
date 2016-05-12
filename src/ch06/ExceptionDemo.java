package ch06;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ExceptionDemo {


    public static void main(String[] args) throws IOException {
        writeList1();
//        writeList2();
    }

    public static void writeList1() throws IOException {
        List<Integer> victor = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));

        for (int i = 0; i < victor.size(); i++)
            out.println("Value at: " + i + " = " + victor.get(i));
        out.close();
    }


    public static void writeList2() {

        List<Integer> victor = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        PrintWriter out = null;

        try {
            System.out.println("Entering try statement");
            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i <= victor.size(); i++)
                out.println("Value at: " + i + " = " + victor.get(i));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Caught ArrayIndexOutOfBoundsException: " +
                    e.getMessage());
            //e.printStackTrace(System.err);
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            //e.printStackTrace(System.err);
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } else {
                System.out.println("PrintWriter not open");
            }
        }
    }
}

