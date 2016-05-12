package ch03.c34;

import java.math.BigInteger;
import java.util.Scanner;

public class FactorialDemo {
    public static void main(String[] args) {
        int N = 10;
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();

        BigInteger fact = BigInteger.valueOf(1L);

        for (int i = 1; i < N; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }

        System.out.println(N + "!=" + fact);

    }
}
