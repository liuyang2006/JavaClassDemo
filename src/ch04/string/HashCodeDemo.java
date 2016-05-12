package ch04.string;

public class HashCodeDemo {
    public static void main(String[] args) {
        A a = null;

        System.out.println(a);

        a = new A();
        System.out.println(a);
        System.out.println(Integer.toHexString(a.hashCode()));

        B b = new B();
        System.out.println(b);

        C c = new C();
        System.out.println(c);

        A[] aArray = new A[10];
        System.out.println(aArray);

        System.out.println(aArray.getClass());
    }
}

class A {

}

class B {
    @Override
    public int hashCode() {
        return 1;
    }
}

class C {
    @Override
    public String toString() {
        return "C{}";
    }
}