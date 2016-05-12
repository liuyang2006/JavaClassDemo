package ch04.string;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class StringConvertDemo {

    public static void main(String[] args) {
        String s = "1000";

        int i = Integer.valueOf(s);
        i = Integer.parseInt(s);
        i = Integer.valueOf(s);
        s = String.valueOf(i);

        s="3.14159265358979323846";
        double f = Double.valueOf(s);
        f = Double.parseDouble(s);
        s = String.valueOf(f);
        s = String.format("带格式的数字:%.4f", f);

        Integer a = i;
        i = a;
        s = a.toString();

        Double b = f;
        f = b;
        s = b.toString();

        boolean c = false;
        Boolean d = c;
        c = d;
        s = d.toString();

        int[] aa = new int[10];
        System.out.println(Arrays.toString(aa));

        List list = new Vector();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.toString());

    }
}
