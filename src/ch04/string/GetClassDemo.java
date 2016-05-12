package ch04.string;

public class GetClassDemo {

    public static void main(String[] args) {
        int[] a = new int[10];
        double[][] b = new double[10][20];
        System.out.println(a.getClass());
        System.out.println(b.getClass());

        Point p1 = new Point();
        System.out.println(p1.getClass());
        System.out.println(Point.class.getSimpleName());

        Point[] p = new Point[10];
        System.out.println(p.getClass());
        Point[][] q = new Point[10][20];
        System.out.println(q.getClass());
    }
}

class Point {
}
