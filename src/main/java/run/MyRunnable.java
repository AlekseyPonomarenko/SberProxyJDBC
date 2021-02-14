package run;

public class MyRunnable implements Runnable {
    private Object t1, t2;

    public MyRunnable(Object t1, Object t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public void run() {
        System.out.println(Main.getService().doHardWork(t1, t2));
    }
}
