package ch03.c34;

import java.util.Date;
import java.util.Random;


public class JavaAPIDemo {

	public static void main(String[] args) {
		Date a = new Date();
		System.out.println(a);

		java.sql.Date d;

		Random r = new Random();

		for (int i = 0; i < 10; i++) {
			System.out.println(r.nextInt(100));
			
		}

	}

}
