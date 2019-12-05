package hcy.test;

import hcy.test.utils.SleepUtils;

public class Profiler {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        THREAD_LOCAL.set(Thread.currentThread().getName());

        new Thread(() -> {
            THREAD_LOCAL.set(Thread.currentThread().getName());
            System.out.println(THREAD_LOCAL.get());
        }, "Thread1").start();
        new Thread(() -> {
            THREAD_LOCAL.set(Thread.currentThread().getName());
            System.out.println(THREAD_LOCAL.get());
        }, "Thread2").start();

        System.out.println(THREAD_LOCAL.get());
    }
}
