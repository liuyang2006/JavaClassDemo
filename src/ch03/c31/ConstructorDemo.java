package ch03.c31;
class A{
    protected void finalize() {System.out.println("A is deleted.");}
}
class B{
    public B() {}

    protected void finalize() {System.out.println("B is deleted.");}
}
class C{
    public C() {System.out.println("Construct an object of C.");}
}
public class ConstructorDemo {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();
        a = null;
        b = null;
        c = null;
        System.gc();
        System.runFinalization();
    }
}
