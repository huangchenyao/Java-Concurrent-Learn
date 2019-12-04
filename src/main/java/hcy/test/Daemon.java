package hcy.test;

import hcy.test.utils.SleepUtils;

public class Daemon {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
        SleepUtils.second(1);
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(2);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
