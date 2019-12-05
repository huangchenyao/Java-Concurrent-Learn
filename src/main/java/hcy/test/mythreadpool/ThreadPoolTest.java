package hcy.test.mythreadpool;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) throws Exception {
        ThreadPool<SimpleJob> pool = new DefaultThreadPool<>(5);
//        pool.addWorkers(5);
        TimeUnit.SECONDS.sleep(5);
        pool.removeWorker(4);

        for (int i = 0; i < 10; ++i) {
            pool.execute(new SimpleJob("SimpleJob" + i));
        }
        TimeUnit.SECONDS.sleep(10);
        System.out.println("------------------------");

        pool.shutdown();
    }
}

class SimpleJob implements Runnable {
    private String name;

    public SimpleJob(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(name);
            System.out.println(LocalDateTime.now());
        } catch (InterruptedException ignore) {
        }
    }
}