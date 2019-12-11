package hcy.test.utils;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignore) {
        }
    }

    public static final void millisecond(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ignore) {
        }
    }
}
