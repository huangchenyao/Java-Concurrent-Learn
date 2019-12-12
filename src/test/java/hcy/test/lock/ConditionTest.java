package hcy.test.lock;

import hcy.test.utils.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition conditionA = lock.newCondition();
    public static Condition conditionB = lock.newCondition();
    public static Condition conditionC = lock.newCondition();
    public static int index = 0;

    @Test
    public void test() {
        new ThreadC().start();
        new ThreadB().start();
        new ThreadA().start();
        SleepUtils.second(1);
    }

    private static class ThreadA extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                try {
                    lock.lock();
                    System.out.println("A进程输出" + " : " + ++index);
                    conditionB.signal();
                    conditionA.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ThreadB extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                try {
                    lock.lock();
                    System.out.println("B进程输出" + " : " + ++index);
                    conditionC.signal();
                    conditionB.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ThreadC extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                try {
                    lock.lock();
                    System.out.println("C进程输出" + " : " + ++index);
                    conditionA.signal();
                    conditionC.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
