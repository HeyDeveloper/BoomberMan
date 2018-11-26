package test;

import java.util.Timer;
import java.util.TimerTask;

public class FunctionOneTimesTest {
    public static void main(String[] args) {
        TimerTest1 t1 = new TimerTest1();
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
        t1.countDown(100, 200);
    }
}





class TimerTest1 {
    Timer t;
    int count;
    public TimerTest1() {
    }

    public void countDown(int srcX, int dirX) {
        t.scheduleAtFixedRate(new Task1(srcX, dirX), 0, 500);
    }
    public void showCount() {
        System.out.println(Thread.currentThread().getName() + ".." + count);
    }


    public class Task1 extends TimerTask {
        int srcX;
        int dirX;
        public Task1(int srcX, int dirX) {
            this.srcX = srcX;
            this.dirX = dirX;
        }

        @Override
        public void run() {
            if (checkCount()) {
                new Thread().start();

                count--;
                showCount();
            }
        }

        public boolean checkCount() {
            if (count > 0) {
                return true;
            }
            return false;
        }
    }

}
