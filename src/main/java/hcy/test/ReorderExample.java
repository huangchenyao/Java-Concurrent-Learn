package hcy.test;

public class ReorderExample {
    public static void main(String[] args) {
        ReorderExample reorder = new ReorderExample();
        Thread t1 = new Thread(reorder::writer);
        Thread t2 = new Thread(reorder::reader);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private int a = 0;
    private boolean flag = false;

    public void writer() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            int i = a;
            System.out.println(i);
        }
    }
}
