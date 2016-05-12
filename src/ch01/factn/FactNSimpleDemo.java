package ch01.factn;

import java.math.BigInteger;
import java.util.Scanner;

public class FactNSimpleDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        BigInteger fact = BigInteger.valueOf(1);

        for (int i=1;i<n;i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }

        System.out.println(fact);
    }
}
