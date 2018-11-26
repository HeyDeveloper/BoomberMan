package handler;

import core.StartGame;

import javax.swing.*;

public class LifeHandler {
    //TODO 背景线程实时监控各物品的移动以及是否处于危险

    //结束生命
    public static void kill(StartGame gameframe, JLabel jlabel) {
        gameframe.remove(jlabel);
        gameframe.repaint();
    }

}
