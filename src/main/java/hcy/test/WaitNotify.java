package hcy.test;

import hcy.test.utils.SleepUtils;

import java.time.LocalDateTime;

public class WaitNotify {
    static boolean flag = true;
    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    private static class Wait implements Runnable {
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 当条件不满足时，继续wait，同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true." +
                                " waiting @ " + LocalDateTime.now());
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                // 条件满足时，完成工作
                System.out.println(Thread.currentThread() + " flag is false." +
                        " running @ " + LocalDateTime.now());
            }
        }
    }

    private static class Notify implements Runnable {
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁
                System.out.println(Thread.currentThread() + " hold lock." +
                        " notify @ " + LocalDateTime.now());
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again." +
                        " sleep @ " + LocalDateTime.now());
                SleepUtils.second(5);
            }
        }
    }
}
