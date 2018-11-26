package goods;

import core.StartGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static core.StartGame.*;


public class Bomb  extends Goods{
    Lock lock = new ReentrantLock();
    public Fire fire_obj;
    private static final int BOMB_COUNT_DOWN = 2000;
    private Icon ic = new ImageIcon("src/images/boom.png");
    public JLabel bomb_obj = new JLabel(ic);
    private StartGame gameframe;
    int srcBomb_x, srcBomb_y;
    public ArrayList<JLabel> fire_set = new ArrayList<JLabel>();
    Timer timer =  new Timer();
    public int isBombed = 0;
    
    public int single_offset;

    //炸弹初始化,传递游戏框架， 炸弹的实际位置x 和 y，单反向的偏移量
    public Bomb(StartGame gameframe, int bomb_x, int bomb_y,int single_offset) {
        super(gameframe);
        init(bomb_obj); //炸弹图形初始化
        this.srcBomb_x = bomb_x;
        this.srcBomb_y = bomb_y;
        this.gameframe = gameframe;
        this.single_offset = single_offset;
        gameframe.add(bomb_obj);
        setCoord(this.bomb_obj, bomb_x, bomb_y);

        if (!isFirePlace()) {
            countDown(); //倒计时爆炸
        } else {
            bombRightNow();
        }
        setPlaceBolck(bomb_y/ONE_STEP, bomb_x/ONE_STEP);
    }


    public void bombRightNow() {
        isBombed++;
        if (isBombed <= 1) {
            cleanBomb(srcBomb_x, srcBomb_y);
        }
    }


    public boolean  isFirePlace() {
        if (gameframe.dates[srcBomb_y / ONE_STEP][srcBomb_x / ONE_STEP] == FIRE_IN_ROUTE) {
            return true;
        }
        return false;
    }


    private void setPlaceBolck(int map_x, int map_y) {
        gameframe.dates[map_x][map_y] = BOMB_IN_ROUTE;
    }

    private void countDown() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setPlaceDanger(srcBomb_y/ONE_STEP, srcBomb_x/ONE_STEP);
            }
        }, BOMB_COUNT_DOWN);
    }


    public void cleanBomb(int map_x, int map_y) {
            System.out.println("Bombing..." + this);
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()  + "bomb_obj:" + bomb_obj);
                bomb_obj.setVisible(false);
                gameframe.remove(bomb_obj);
                try {
                    String str = Integer.toString(srcBomb_x) + Integer.toString(srcBomb_y);
                    System.out.println("String" + str);
                    Bomb temp_bomb = gameframe.aBomberMan.bombMap.get(str);
                    if (temp_bomb != null && temp_bomb.bomb_obj != null) {
                        gameframe.aBomberMan.bombMap.remove(str);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("数组越界" + e);
                } catch (NullPointerException e) {
                    System.out.println("空指针");
                }
                //爆炸放火焰
                fire_obj = new Fire(this, fire_set, gameframe, map_x, map_y, single_offset);
            } finally {
                lock.unlock();
            }
    }
}
