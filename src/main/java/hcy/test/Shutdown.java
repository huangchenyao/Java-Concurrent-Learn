package hcy.test;

import hcy.test.utils.SleepUtils;

public class Shutdown {
    public static void main(String[] args) {
        // 使用中断，安全地终止线程
        Runner one = new Runner();
        Thread countThread1 = new Thread(one, "CountThread1");
        countThread1.start();
        SleepUtils.second(1);
        countThread1.interrupt();

        // 使用自己定义的cancel方法，中断线程
        Runner two = new Runner();
        Thread countThread2 = new Thread(two, "CountThread2");
        countThread2.start();
        SleepUtils.second(1);
        two.cancel();
    }

    private static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
