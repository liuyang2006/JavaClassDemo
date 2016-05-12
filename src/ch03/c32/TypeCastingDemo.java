package ch03.c32;

class Employee{}

class Teacher extends Employee {}

class Experimenter extends Employee {}

class Administrator extends Employee {}

public class TypeCastingDemo {

    public static void main(String[] args) {
        Teacher tom = new Teacher();
        Administrator jack = new Administrator();
        Employee a = jack;
        tom = (Teacher) a; //问: 该语句会出现什么情况?

        Teacher b = new Teacher();
        a = b;
        //b = a; //问: 赋值语句为什么出错?
    }
}
