package ch03.c32;

class A {
    int i = 100;
}

class B extends A {}

class C extends B {
    public C() {
        this.i = 900;
    }
}

public class InheritanceDemo {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();

        a = new C(); //问: 引用型变量a指向了 A B C 哪个类型的对象?
        a.i = 1000;
    }
}

