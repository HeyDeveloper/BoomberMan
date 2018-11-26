package handler;

import core.StartGame;

import static core.StartGame.*;

public class MapTool {
    /**
     * 几种不同的打印地图方式
     * @param gameframe
     * @param type
     */
    public static void printMap(StartGame gameframe, int type) {
        System.out.println("=============================");
        for (int i = 0; i < gameframe.dates.length; i++) {
            for (int j = 0; j < gameframe.dates[i].length; j++) {
                if(gameframe.dates[i][j] == FIRE_IN_ROUTE)
                    switch (type) {
                        case 2:
                            System.out.print("|" + gameframe.dates[i][j]);
                            break;
                        case FIRE_IN_ROUTE:
                            System.out.print("|*");
                            break;
                        case BOX_IN_ROUTE:
                            System.out.print("|+");
                            break;
                        case ROCK_IN_ROUTE:
                            System.out.print("|#");
                            break;
                        case NORMAL_ROUTE:
                            System.out.println("| ");
                    }
                }
            System.out.println();
            }
        }
    }
