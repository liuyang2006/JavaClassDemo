package ch11;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TaskRunnable taskRunnable = new TaskRunnable(1000);
        Thread threadRunnable = new Thread(taskRunnable);
        threadRunnable.start();

        TaskCallable taskCallable = new TaskCallable(1000);
        FutureTask<Integer> futureTask = new FutureTask<Integer>(taskCallable);
        new Thread(futureTask).start();

        Thread.sleep(1000);
        System.out.println("主线程在执行任务");
        threadRunnable.join();
        System.out.println("TaskRunnable = " + taskRunnable.getResult());
        System.out.println("TaskCallable = " + futureTask.get());
        System.out.println("所有任务执行完毕");
    }
}

class TaskRunnable implements Runnable {

    private final int n;

    private int result;

    public TaskRunnable(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("子线程在进行计算 " + Thread.currentThread());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += i;
        result = sum;
    }

    public int getResult() {
        return result;
    }
}

class TaskCallable implements Callable<Integer> {
    private int n;

    public TaskCallable(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算 " + Thread.currentThread());
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += i;
        return sum;
    }
}