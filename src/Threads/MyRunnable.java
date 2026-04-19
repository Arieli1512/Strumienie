package Threads;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            IO.print(" 3 ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
