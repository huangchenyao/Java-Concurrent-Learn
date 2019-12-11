package hcy.test.thread;

import hcy.test.utils.SleepUtils;
import org.junit.Test;

public class ThreadTest {
    @Test
    public void test_thread() {
        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();
        thread1.start();
        thread2.start();
        SleepUtils.second(2);
    }

    @Test
    public void test_runnable() {
        Runnable job = new MyRunnable();
        Thread thread1 = new Thread(job);
        Thread thread2 = new Thread(job);
        thread1.start();
        thread2.start();
        SleepUtils.second(2);
    }

    @Test
    public void test_runnable_with_thread_local() {
        Runnable job = new MyRunnableWithThreadLocal();
        Thread thread1 = new Thread(job);
        Thread thread2 = new Thread(job);
        thread1.start();
        thread2.start();
        SleepUtils.second(2);
    }

    private class MyThread extends Thread {
        private int count = 5;

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                if (count > 0) {
                    System.out.println(Thread.currentThread().getName() + " -> " + --count);
                }
            }
        }
    }

    private class MyRunnable implements Runnable {
        private int count = 5;

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                if (count > 0) {
                    System.out.println(Thread.currentThread().getName() + " -> " + --count);
                }
            }
        }
    }

    private class MyRunnableWithThreadLocal implements Runnable {
        private ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 5);

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                if (count.get() > 0) {
                    count.set(count.get() - 1);
                    System.out.println(Thread.currentThread().getName() + " -> " + count.get());
                }
            }
        }
    }
}
