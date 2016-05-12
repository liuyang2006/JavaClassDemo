package ch03.c36;

abstract class A {
}

abstract class B {
    public int i = 100;
    public static int count = 0;

    abstract public void showInfo();
    public final void sayHello() {
        System.out.println("Hello from B.");
    }
}

class C extends B {
    @Override
    public void showInfo() {
        System.out.println("B Object.");
    }
    // public void sayHello() {}
}

final class D {
}

//class E extends D{}

public class ReserveKeysDemo {
    public static void main(String[] args) {
        //A a = new A();
        A a = new A() {};
        a = new A() {};
        a = new A() {};

        B b = new B() {
            @Override
            public void showInfo() {
                System.out.println("Anonymous class Object based on A.");
            }
        };

        C c = new C();

        b.showInfo();
        b.count++;
        c.showInfo();
        c.count++;
    }
}
