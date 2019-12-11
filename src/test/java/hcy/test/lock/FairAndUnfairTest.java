package hcy.test.lock;

import hcy.test.utils.SleepUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        for (int i = 0; i < 5; ++i) {
            Job job = new Job(lock);
            job.setName(String.valueOf(i));
            job.start();
        }
        SleepUtils.second(6);
    }

    private static class Job extends Thread {
        private Lock lock;

        private Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            //连续2次打印当前的Thread和等待队列中的Thread
            for (int i = 0; i < 2; ++i) {
                lock.lock();
                try {
                    Collection<Thread> queuedThreads = ((ReentrantLock2) lock).getQueuedThreads();
                    String collect = queuedThreads.stream().map(Thread::getName).collect(Collectors.joining(","));
                    System.out.println("Lock by[" + Thread.currentThread().getName() + "], Waiting by [" + collect + "]");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            List<Thread> threads = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(threads);
            return threads;
        }
    }
}
