package monster;

import core.GameOverUI;
import core.StartGame;
import handler.Direction;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

import static core.StartGame.FIRE_IN_ROUTE;
import static core.StartGame.NORMAL_ROUTE;

public class Monster implements  Runnable{
    private static final int ONE_STEP = 48;
    public JLabel monster;
    int init_x;
    int init_y;
    StartGame gameframe = null;
    long moving_speed;
    GameOverUI GOUI = null;
    MonsterCount mc = null;
    public int dx;
    public int dy;
    private int x;
    private int y;


    public Monster(StartGame gameframe, int init_x, int init_y, long moving_speed ,
    		MonsterCount mc,GameOverUI GOUI) {
    	
        this.gameframe = gameframe;
        this.init_x = init_x;
        this.init_y = init_y;
        this.x = init_x * ONE_STEP;
        this.y = init_y * ONE_STEP;
        this.moving_speed = moving_speed;
        this.mc = mc;
        this.GOUI = GOUI;
        monsterInit();
    }



    private void monsterInit() {
        //初始化怪物
//        init_x = 6;			//这里直接用坐标*ONE_STEP像素来设置具体位置
//        init_y = 3;			//坐标放标在数组地图中描述
        Icon ic = new ImageIcon("src/images/monster.png");
        monster = new JLabel(ic);
        monster.setBounds(init_x*ONE_STEP, init_y*ONE_STEP, ONE_STEP, ONE_STEP);
        gameframe.add(monster);
    }


    boolean is_monster_die = false;
    @Override
    public void run() {
        try {
            while (true) {
                new Thread().sleep(moving_speed);
//                System.out.println("x-->" +x + "...dx=" + dx );
                monsterMove();
            }
        } catch (Exception e) {
            System.out.println("Moving ..fail" + e);
        }
    }



    private final int LEFT = 0;
    private final int RIGHT= 1;
    private final int UP= 2;
    private final int DOWN= 3;
    Timer t = new Timer(true);

    public void monsterMove() {
        int[] coords = null;
        coords = checkRoute();
        if (coords == null) {
            System.out.println("No Route!");
            return;
        }

        if(srcX - coords[0] > 0) {
//            System.out.println("左");
//            moveSloly(Direction.LEFT);
            Direction.moveSloly(monster,Direction.LEFT);
        } else if(srcX - coords[0] < 0) {
//            System.out.println("右");
//            moveSloly(Direction.RIGHT);
            Direction.moveSloly(monster, Direction.RIGHT);
        } else if(srcY - coords[1] > 0) {
//            System.out.println("上");
//            moveSloly(Direction.UP);
            Direction.moveSloly(monster, Direction.UP);
        }else if(srcY - coords[1] < 0) {
//            System.out.println("下");
//            moveSloly(Direction.DOWN);
            Direction.moveSloly(monster, Direction.DOWN);
        }
    }

    /**
     * 返回一个可行的方向数组
     * @return
     */
    int srcX;
    int srcY;
    public int[] checkRoute() {
        //创建一个包含四个数组的容器，由于需要经常增删，故用链表
        LinkedList<Integer> set = new LinkedList<Integer>();
        for (int i = 0; i < 4; i++) {
            set.add(i);
        }
        srcX = monster.getX();
        srcY = monster.getY();
        int direction = -1;

        int checkingX;
        int checkingY;

        while(set.size() > 0) {
            checkingX = monster.getX();
            checkingY = monster.getY();
            int r = new Random().nextInt(set.size());
            try {
                direction = set.remove(r) + 1; //从四个方向中取一个，直到为空
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Monster数组越界:"+ e);
            }

            //根据方向生成临时坐标
            switch (direction) { 
                case 1:
                    checkingY = monster.getY() + ONE_STEP;
                    break;
                case 2:
                    checkingY = monster.getY() - ONE_STEP;
                    break;
                case 3:
                    checkingX = monster.getX() - ONE_STEP;
                    break;
                case 4:
                    checkingX = monster.getX() + ONE_STEP;
                    break;
            }

            //判读路径是否有障碍
            int map_x = checkingY/ONE_STEP;  //地图X轴
            int map_y = checkingX/ONE_STEP;  //地图Y轴
            if (gameframe.dates[map_x][map_y] == FIRE_IN_ROUTE) {
                Thread t = new Thread().currentThread();
                System.out.println("Danger!!!");
                is_monster_die = true;
                
                mc.setReduceCount();			//怪物执行一次死亡动作就执行一次减少数量操作
                if(mc.getState()) {
                	GOUI.gameWinUI_Add();		//如果数量为0返回真，就调用UI类的可视标签方法
                }
                
                Direction.go(this.monster, -1,0);
                this.monster.setVisible(false);
                t.interrupt();
            }
            //如果有路，返回该路的坐标
            if (gameframe.dates[map_x][map_y] == NORMAL_ROUTE) {
                return new int[]{checkingX, checkingY};
            }
        }
        return null;
    }

}


