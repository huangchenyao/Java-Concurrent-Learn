package hcy.test.lock;

import hcy.test.utils.SleepUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockTest {
    @Test
    public void test_sync_rw() {
        new Thread(() -> syncGet(Thread.currentThread())).start();
        new Thread(() -> syncGet(Thread.currentThread())).start();
        SleepUtils.second(3);
    }

    @Test
    public void test_rw_lock() {
        new Thread(() -> rwLockGet(Thread.currentThread())).start();
        new Thread(() -> rwLockGet(Thread.currentThread())).start();
        SleepUtils.second(2);
    }

    @Test
    public void test_r_and_w() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> write(lock, Thread.currentThread()));
        service.execute(() -> read(lock, Thread.currentThread()));
        SleepUtils.second(3);
    }

    @Test
    public void test_w_and_w() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> write(lock, Thread.currentThread()));
        service.execute(() -> write(lock, Thread.currentThread()));
        SleepUtils.second(3);
    }

    @Test
    public void test_r_and_r() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> read(lock, Thread.currentThread()));
        service.execute(() -> read(lock, Thread.currentThread()));
        SleepUtils.second(3);
    }

    private synchronized static void syncGet(Thread thread) {
        System.out.println("start time: " + LocalDateTime.now());
        for (int i = 0; i < 5; i++) {
            SleepUtils.millisecond(200);
            System.out.println(thread.getName() + ": reading...");
        }
        System.out.println(thread.getName() + ": finish read.");
        System.out.println("end time:" + LocalDateTime.now());
    }

    private static void rwLockGet(Thread thread) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        System.out.println("start time: " + LocalDateTime.now());
        for (int i = 0; i < 5; i++) {
            SleepUtils.millisecond(200);
            System.out.println(thread.getName() + ": reading...");
        }
        System.out.println(thread.getName() + ": finish read.");
        System.out.println("end time:" + LocalDateTime.now());
        lock.readLock().unlock();
    }

    private static void write(ReentrantReadWriteLock lock, Thread thread) {
        lock.writeLock().lock();
        if (lock.isWriteLocked()) {
            System.out.println("current is write lock.");
        }
        System.out.println("start time: " + LocalDateTime.now());
        for (int i = 0; i < 5; i++) {
            SleepUtils.millisecond(200);
            System.out.println(thread.getName() + ": writing...");
        }
        System.out.println(thread.getName() + ": finish write.");
        System.out.println("end time:" + LocalDateTime.now());
        lock.writeLock().unlock();
    }

    private static void read(ReentrantReadWriteLock lock, Thread thread) {
        lock.readLock().lock();
        if (!lock.isWriteLocked()) {
            System.out.println("current is read lock.");
        }
        System.out.println("start time: " + LocalDateTime.now());
        for (int i = 0; i < 5; i++) {
            SleepUtils.millisecond(200);
            System.out.println(thread.getName() + ": reading...");
        }
        System.out.println(thread.getName() + ": finish read.");
        System.out.println("end time:" + LocalDateTime.now());
        lock.readLock().unlock();
    }
}
