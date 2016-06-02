package ch11;

public class TaskThreadDemo {
    public static void main(String[] args) {
        // Create tasks
        Runnable printA = new PrintChar('a', 100);
        Runnable printB = new PrintChar('b', 100);
        Runnable print100 = new PrintNum(100);

        // Create threads
        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(print100);

//        thread1.setPriority(Thread.MIN_PRIORITY);
//        thread2.setPriority(Thread.MIN_PRIORITY);
//        thread3.setPriority(Thread.MAX_PRIORITY);
        thread1.setName("printA 100 times");
        thread2.setName("printB 100 times");
        thread3.setName("print100");

        System.out.println(Thread.currentThread());

        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("exit of main thread");
        return;

    }
}

// The task for printing a specified character in specified times
class PrintChar implements Runnable {
    private char charToPrint; // The character to print
    private int times; // The times to repeat

    /**
     * Construct a task with specified character and number of
     * times to print the character
     */
    public PrintChar(char c, int t) {
        charToPrint = c;
        times = t;
    }

    /**
     * Override the run() method to tell the system
     * what the task to perform
     */
    public void run() {
        System.out.println(Thread.currentThread());
        for (int i = 0; i < times; i++) {
            System.out.print(charToPrint + " ");
            Thread.yield();
        }
    }
}

// The task class for printing number from 1 to n for a given n
class PrintNum implements Runnable {
    private int lastNum;

    /**
     * Construct a task for printing 1, 2, ... i
     */
    public PrintNum(int n) {
        lastNum = n;
    }

    /**
     * Tell the thread how to run
     */
    public void run() {
        System.out.println(Thread.currentThread());
        for (int i = 1; i <= lastNum; i++) {
            System.out.print(i + " ");
            Thread.yield();
        }
    }
}
