package hcy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CasCount {
    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final CasCount cas = new CasCount();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; ++j) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; ++i) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        ts.forEach(Thread::start);
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    private void safeCount() {
        for (; ; ) {
            int i = atomicI.get();
            boolean success = atomicI.compareAndSet(i, ++i);
            if (success) {
                break;
            }
        }
    }

    private void count() {
        ++i;
    }
}
