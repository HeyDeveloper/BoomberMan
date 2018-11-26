package test;

import java.util.Timer;
import java.util.TimerTask;

class MyTimer extends TimerTask {
    @Override
    public void run() {
        System.out.println("hello");
    }

    void show() {
        MyTimer t = new MyTimer();
        Timer ts = new Timer();
        ts.schedule(t, 3000);
    }
}

public class TimerTest {
    public static void main(String[] args) {
        MyTimer my = new MyTimer();
        System.out.println("main showing");
        my.show();
        System.out.println("main showing");
    }
}
