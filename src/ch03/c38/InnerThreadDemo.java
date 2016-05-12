package ch03.c38;

import java.util.Scanner;

public class InnerThreadDemo {
    public static void startSubThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                    while (true) {
                    try {
                        Thread.sleep(3000);
                        System.out.println("In sub thread: Computing " + i++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        startSubThread();

        while (scanner.hasNext()) {
            String strIn = scanner.nextLine();
            if (strIn.equals("exit")) {
                System.out.println("In main thread: Bye Bye.");
                System.exit(0);
            }
            System.out.println("In main thread: Your input is " + strIn);
        }

    }
}
