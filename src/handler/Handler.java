package handler;

import core.StartGame;
import goods.Bomb;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Handler {
    StartGame gameframe = null;
    Lock lock = new ReentrantLock();

    public Handler(StartGame gameframe) {

        this.gameframe = gameframe;
    }
    public void check() {
        checkBomberMan();
        checkFire();
    }

    private void checkBomberMan() {
        gameframe.isPeopleInDanger();
        gameframe.repaint();
    }


    public void checkFire() {
        try {
            lock.lock();
            Set<Map.Entry<String, Bomb>> set = gameframe.aBomberMan.bombMap.entrySet();
            for (Iterator<Map.Entry<String, Bomb>> iterator = set.iterator(); iterator.hasNext(); ) {
//            try {
                Map.Entry<String, Bomb> next = iterator.next();
                if (next!=null && next.getValue().isFirePlace() && next.getValue().isBombed <= 1) {
                    next.getValue().bombRightNow();
                }
//            } catch (ConcurrentModificationException e) {
//                System.out.println(Thread.currentThread().getName() + "Fire Handler:异步修改出错" + e);
//            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }finally {

            lock.unlock();
        }

    }

}
