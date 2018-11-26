package goods;

import core.StartGame;

import javax.swing.*;

import static core.StartGame.FIRE_IN_ROUTE;
import static core.StartGame.ONE_STEP;

public class Goods {
    public StartGame gameframe;
    public Goods(StartGame gameframe) {
        this.gameframe = gameframe;
    }

    /**
     * 物件初始化大小
     * @param obj
     */
    public static void init(JLabel obj) {
        obj.setBounds(ONE_STEP, ONE_STEP, ONE_STEP, ONE_STEP);
    }


    public void setCoord(JLabel jlab, int x, int y) {
        jlab.setLocation(x, y);
    }
    public void setPlaceDanger(int map_x, int map_y) {
        gameframe.dates[map_x][map_y] = FIRE_IN_ROUTE;
    }
}
