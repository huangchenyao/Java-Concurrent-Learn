package hcy.test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    int a = 0;
    ReentrantLock lock = new ReentrantLock();

    public void writer() {
        lock.lock();
        try {
            ++a;
        } finally {
            lock.unlock();
        }
    }
    public void reader() {
        lock.lock();
        try {
            int i = a;
            System.out.println(i);
        } finally {
            lock.unlock();
        }
    }
}
