package test;

class SaleTicket implements Runnable{
    private int tickets = 100;
    Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (obj) {
                if (tickets > 0) {
                try{Thread.sleep(10);} catch (InterruptedException e) {};
                    System.out.println(Thread.currentThread().getName()+"..." + tickets--);
                }
            }
        }
    }
}


public class TIcketDemo {
    public static void main(String[] args) {
        SaleTicket t = new SaleTicket();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);
        Thread t5 = new Thread(t);
        Thread t6 = new Thread(t);
        Thread t7 = new Thread(t);
        Thread t8 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
    }
}
