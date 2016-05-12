package ch03.c38;

class A {
    int i = 10;
    static int s = 3;

    class B1 {

        class C{}
        public void showInfo() {
            System.out.println("i=" + i);
        }
    }

    static class B2 {
        public void showInfo() {
            System.out.println("s=" + s);
        }
    }
}
abstract class D {
    public abstract void showInfo();
}
interface I {
    void showInfo();
}

public class InnerDemo {
    public static void main(String[] args) {
        A a = new A();
        A.B1 b1 = a.new B1();
        A.B2 b2 = new A.B2();

        b1.showInfo();
        b2.showInfo();

        D d = new D() {
            @Override
            public void showInfo() {
                System.out.println("InnerDemo$1 Object.");
            }
        };
        I impl = new I() {
            @Override
            public void showInfo() {
                System.out.println("InnerDemo$2 Object.");
            }
        };

        d.showInfo();
        impl.showInfo();
    }
}
