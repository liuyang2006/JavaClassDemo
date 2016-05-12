package ch03.c39;

class A {
    int i = 10;
}

class B extends A {
    int i = 20;

    public void showInfo() {
        int i = 30;
        System.out.println(i);
        System.out.println(this.i);
        System.out.println(super.i);
    }
}

public class VariableScopeDemo {
    public static void testAdd(B b) {
        b.i = 500;
    }

    public static void main(String[] args) {
        B b = new B();
        b.showInfo();

        testAdd(b);
    }
}
