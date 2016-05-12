package ch03.c32.s1;

class Employee{
    int workYear;
}

class Teacher extends Employee{}

class Experimenter extends Employee{}

class Administrator extends Employee{}

public class InstanceofDemo {

    public static void main(String[] args) {
        Teacher tom = new Teacher();
        Administrator jack = new Administrator();
        Employee a = jack;

        Teacher b;
        if (a instanceof Teacher) {
            b = (Teacher) a;
            System.out.println("a is an Teacher.");
        }
        if (a instanceof Administrator) {
            System.out.println("a is an Administrator.");
        }
        if (a instanceof Experimenter) {
            System.out.println("a is an Experimenter.");
        }
        if (a instanceof Employee) {
            System.out.println("a is an Employee.");
        }
    }
}

