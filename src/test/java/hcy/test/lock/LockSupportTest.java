package hcy.test.lock;

import hcy.test.utils.SleepUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    @Test
    public void test1() {
        Thread thread1 = new Thread(() -> {
//            SleepUtils.second(2);
            System.out.println("begin: " + LocalDateTime.now());
            LockSupport.park();
            System.out.println("end: " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() -> {
//            SleepUtils.second(2);
            LockSupport.unpark(thread1);
            System.out.println("unpark");
        });

        thread1.start();
        thread2.start();

        SleepUtils.second(5);
    }
}
