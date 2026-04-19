package Threads;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static void main() {

//        MyThread thread = new MyThread();
//        thread.start();
//
//
//        Thread thread2 = new Thread(new MyRunnable());
//        thread2.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            executorService.submit(()->print(finalI));
        }
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
    executorService.shutdown();
    }

    private static void print(int number)
    {
        for (int i=0;i<4;i++)
        {
            System.out.print(" "+number+" ");
            try {
                int timeToSleep = new Random().nextInt(400,1000);
                Thread.sleep(timeToSleep);
            }
            catch (InterruptedException e)
                {
                throw new RuntimeException(e);
                }
        }

    }
}
