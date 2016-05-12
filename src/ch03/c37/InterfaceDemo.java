package ch03.c37;

interface I1 {
    void showInfo();
}

interface I2 {
    int add(int a, int b);
}

interface I3 extends I1,I2{}

class A0{int a;}

class A extends A0 implements I1, I2 {

    @Override
    public void showInfo() {
        System.out.println("A Object");
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        A a = new A();
        a.showInfo();
        int result = a.add(3,4);

        A0 a0 = new A();
        I1 i1 = new A();
        I2 i2 = new A();

        i1 = new I1() {
            int a;
            @Override
            public void showInfo() {

            }
        };

        I3 i3 = new I3() {
            @Override
            public void showInfo() {

            }

            @Override
            public int add(int a, int b) {
                return 0;
            }
        };
    }
}
