package Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static void main() {

        MyThread thread = new MyThread();
        thread.start();
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        ExecutorService executorService2 = Executors.newCachedThreadPool();

        for (int i = 0; i < 4; i++)
        {
            IO.print(" 2 ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
