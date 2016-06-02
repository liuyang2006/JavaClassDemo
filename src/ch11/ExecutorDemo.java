package ch11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        // Create a fixed thread pool with maximum three threads
        ExecutorService executorPool = Executors.newFixedThreadPool(3);

        // Submit runnable tasks to the executor
        executorPool.execute(new PrintChar('a', 100));
        executorPool.execute(new PrintChar('b', 100));
        executorPool.execute(new PrintNum(100));

        // Shut down the executor
        executorPool.shutdown();
    }
}
