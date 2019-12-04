package hcy.test;

import hcy.test.utils.SleepUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deprecated {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        SleepUtils.second(3);

        // 将线程暂停
        printThread.suspend();
        System.out.println("main suspend PrintThread at " + formatter.format(LocalDateTime.now()));
        SleepUtils.second(3);

        // 将线程恢复
        printThread.resume();
        System.out.println("main resume PrintThread at " + formatter.format(LocalDateTime.now()));
        SleepUtils.second(3);

        // 将线程终止
        printThread.stop();
        System.out.println("main stop PrintThread at " + formatter.format(LocalDateTime.now()));
        SleepUtils.second(3);
    }

    static class Runner implements Runnable {
        @Override
        public void run() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (true) {
                System.out.println(Thread.currentThread().getName() + " Run at " + formatter.format(LocalDateTime.now()));
                SleepUtils.second(1);
            }
        }
    }
}
