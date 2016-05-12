package ch03.c33;

import java.util.Random;

class Employee {
    int workYear = 1;

    public void showInfo() {
        System.out.println("Employee Object.");
    }
}

class Teacher extends Employee {
    public void showInfo() {
        System.out.println("Teacher Object.");
    }
}

class Experimenter extends Employee {
    public void showInfo() {
        System.out.println("Experimenter Object.");
    }
}

class Administrator extends Employee {
    @Override
    public void showInfo() {
        System.out.println("Administrator Object.");
    }
}

public class PolymorphismDemo {

    public static void main(String[] args) {
        Employee a;
        Random rand = new Random();
        int countT = 0, countA = 0, countE = 0;

        for (int i = 0; i < 10; i++) {
            float test = rand.nextFloat();
            if (test <= (1.0 / 3)) {
                a = new Teacher();
                countT++;
            } else if (test <= (2.0 / 3)) {
                a = new Administrator();
                countA++;
            } else {
                a = new Experimenter();
                countE++;
            }
            System.out.println("Step " + (i + 1));
            a.showInfo(); //问: 将调用哪个类的成员方法?
            System.out.println("==================");
        }
        System.out.println("Total statistics: Teacher " + countT + ", Administrator "
                + countA + ", Experimenter " + countE + ".");
    }
}

