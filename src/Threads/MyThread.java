package Threads;

public class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 4; i++)
        {
            IO.print(" 1 ");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
