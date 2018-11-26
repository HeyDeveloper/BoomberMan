package people;

import core.StartGame;
import goods.Bomb;
import handler.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static core.StartGame.BOMB_IN_ROUTE;
import static core.StartGame.ONE_STEP;

public class BomberMan {
	private static final int IMG_COLUMN = 3;  //人物图片单方向帧数
    private static final int IMG_ROW = 4;    //人物四个方向
    public boolean bomber_man_invincible = false; // 人物是否无敌
    public int bomb_count = 1;			//设置默认炸弹数量			max = 3
    public int blood_count = 3;		//设置默认生命数量  		max = 6
    public int single_offset = 1;		//设置默认火焰偏移量		max = 4
    public JLabel lab_man;   // 人物图层
    
    public ConcurrentHashMap<String, Bomb> bombMap = new ConcurrentHashMap<>();
    private static final int DOWN = 0;
    private static final int LEFT= 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private int direction;
    public int currentFrame = 0;  // 人物单方向当前帧数
    
    int mx;
    int my;
    int real_x;
    int real_y;
    StartGame gameframe = null;
    public BloodCount BC;				//new一个血量类
    Image image;

    int dx = 0;
    int dy = 0;
    public int moving_speed = 4;
    private int srcY;
    private int srcX;
    public int width;
    public int height;

    public BomberMan(StartGame gameframe, int man_x, int man_y) {
        //初始化人物
        this.mx = man_x;			//这里直接用坐标*ONE_STEP像素来设置具体位置
        this.my = man_y;			//坐标放标在数组地图中描述
        this.real_x = man_x *ONE_STEP;
        this.real_y = man_y *ONE_STEP;
        ImageIcon ic = new ImageIcon("src/images/man.png");		
        image = ic.getImage();
        this.lab_man = new JLabel(ic);
        lab_man.setBounds(real_x, real_y, ONE_STEP, ONE_STEP);
        this.gameframe = gameframe;
        gameframe.add(lab_man);
        try {
            bufimg = ImageIO.read(new File("src/images/man.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        moveAnimateThread();
        movingAnimate();
        getImageDimensions();
        setDeadImage();
    }

    public boolean speedPlusOne() {
        if (moving_speed < 10) {
            moving_speed += 2;
            return true;
        } else {
            return false;
        }
    }
    
    public int getSpeed() {
        return moving_speed;
    }


    // 释放按键，检查是否有其它方向按下，如果有，则不停止动画，向按下的方向前进，知道再次按下其他方向键
    private void releaseDiretion(int directon) {
        isKeyPress[directon] = false;
        switch (directon) {
            case LEFT:
                dx = 0;
                if (isKeyPress[RIGHT]) {
                    setDirection(RIGHT);
                }
                break;
            case RIGHT:
                dx = 0;
                if (isKeyPress[LEFT]) {
                    dx = -moving_speed;
                    setDirection(LEFT);
                }
                break;
            case UP:
                dy = 0;
                if (isKeyPress[DOWN]) {
                    dy = -moving_speed;
                    setDirection(DOWN);
                }
                break;
            case DOWN:
                dy = 0;
                //防止当多个按钮按下时，不走动
                if (isKeyPress[UP]) {
                    dy = moving_speed;
                    setDirection(UP);
                }
                break;
        }

        //此段代码遍历是否有其它按键按下，防止滑翔
        for (int i = 0; i < isKeyPress.length; i++) {
            if (isKeyPress[i]) {
                nextFrame = 1;
                break;
            }
            if (i >= isKeyPress.length - 1) {
                nextFrame = 0;
            }
        }
    }

    public void setBloodCount() {		//设置血的数量UI
    	BC = new BloodCount(this.gameframe,gameframe.aBomberMan,blood_count);
    }


    //使人物无敌
    public void setManInvincible(boolean b) {
        if(b){
            bomber_man_invincible = true;
        }
        else{
            bomber_man_invincible= false;
        }
    }



    //检查对应方向的位置是否可以走
    public boolean checkRoute(int direction) {
        int src_realX = lab_man.getX();
        int src_realY = lab_man.getY();

        double tempY;
        double tempX;
        int temp;
        switch (direction) {
            case Direction.UP:
                tempY = (int) Math.floor((src_realY - 1)/(double)ONE_STEP);
                tempX = Math.round(src_realX / (double)ONE_STEP);
                temp = gameframe.dates[(int) tempY][(int) tempX];
//                this.real_x = (int)tempX * ONE_STEP;
                if (temp == 4 || temp == 9) {
                    return true;
                }
                break;
            case Direction.DOWN:
                tempY = (int) Math.ceil((src_realY + 1)/(double)ONE_STEP);
                tempX = Math.round(src_realX / (double)ONE_STEP);
                temp = gameframe.dates[(int) tempY][(int) tempX];
//                this.real_x = (int)tempX * ONE_STEP;
                if (temp == 4 || temp == 9) {
                    return true;
                }
                break;
            case Direction.LEFT:
                tempY = Math.round(src_realY / (double)ONE_STEP);
                tempX = (int) Math.floor((src_realX -1)/ (double)ONE_STEP);
                temp = gameframe.dates[(int) tempY][(int) tempX];
//                this.real_y = (int)tempY * ONE_STEP;
                if (temp == 4 || temp == 9) {
                    return true;
                }
                break;
            case Direction.RIGHT:
                tempY = Math.round(src_realY / (double)ONE_STEP);
                tempX = (int) Math.ceil((src_realX + 1)/ (double)ONE_STEP);
                temp = gameframe.dates[(int) tempY][(int) tempX];
//                System.out.println("src:" + src_realX +":" + src_realY+ "  temp:" + tempX + ":" + tempY + "->" + temp);
//                this.real_y = (int)tempY * ONE_STEP;
                if (temp == 4 || temp == 9) {
                    return true;
                }
                break;
        }
        return false;
    }


    //四个方向,用于储存该方向是否被按下，按下则true, 松开则false
    boolean[] isKeyPress = new boolean[4];
    public void keyPress(KeyEvent e) {
        nextFrame = 1;
        int key = e.getKeyCode();
        //32为空格
        int x = lab_man.getX();	//获取人物的xy
        int y = lab_man.getY();	//遇到空格投放炸弹
        if(key == 32) {
            //防止炸弹偏移
            double temp_x = Math.round(lab_man.getX() / (double)ONE_STEP) * ONE_STEP;
            double temp_y = Math.round(lab_man.getY() / (double)ONE_STEP) * ONE_STEP;
            real_x= (int) temp_x;
            real_y = (int) temp_y;
            setBomb((int) temp_x, (int) temp_y);
        }
        //左
        if(key == KeyEvent.VK_LEFT) {
            setDirection(LEFT);
        }
        //上
        if(key == KeyEvent.VK_UP) {
            setDirection(UP);
        }
        //右
        if(key == KeyEvent.VK_RIGHT) {
            setDirection(RIGHT);
        }
        //下
        if(key == KeyEvent.VK_DOWN) {
            setDirection(DOWN);
        }
    }


    private void setDirection(int direction) {

        this.direction= direction;
        isKeyPress[direction] = true;
        switch (direction) {
            case LEFT:
                dx = -moving_speed;
                break;
            case RIGHT:
                dx = moving_speed;
                break;
            case UP:
                dy = -moving_speed;
                break;
            case DOWN:
                dy = moving_speed;
                break;
        }
    }

    public void keyRelease(KeyEvent event) {
        nextFrame = 0;
        int key = event.getKeyCode();
        if(key == KeyEvent.VK_LEFT) {
            releaseDiretion(LEFT);
        }
        //上
        if(key == KeyEvent.VK_UP) {
            releaseDiretion(UP);
        }
        //右
        if(key == KeyEvent.VK_RIGHT) {
            releaseDiretion(RIGHT);
        }
        //下
        if(key == KeyEvent.VK_DOWN) {
            releaseDiretion(DOWN);
        }
    }
    



    public void moveAnimateThread() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getImage();
                updateImage();
                gameframe.repaint();
                move();
                if(BC!=null)
        		BC.setUI();		//每次移动都重新设置血条坐标		

            }
        }, 0, 30);
    }

    BufferedImage bufimg;
    private void updateImage() {
            //原图片是X轴方向是同个方向的人物移动分解图，需要在该方向上进行来回切换以达到动画效果
            srcX = currentFrame * width;
            //Y轴方向是不同方向的图
            srcY = getDirection() * height;
    }

    private int getDirection() {
        return direction;
    }


    protected void getImageDimensions() {
        //图片的总宽度除于单行人物数量就是单张人物的宽度
        width = image.getWidth(null) / IMG_COLUMN;
        height = image.getHeight(null) / IMG_ROW;
    }

    public void getImage() {
        image = bufimg.getSubimage(srcX,srcY,width,height);
        ImageIcon ic = new ImageIcon(image);
        lab_man.setIcon(ic);
    }

    Timer t1 = new Timer();
    int nextFrame = 0;
    public void movingAnimate(){
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                //动画的关键，横排位置切换, frame作为开关, 当frame = 0;currentFrame不变，当frame = 1, currentFrame不断从0 - 3三个帧之间切换
                currentFrame =  (currentFrame + nextFrame)  % IMG_COLUMN;
            }
        }, 0, 100); //100 是(三张图)动画切换速度,单位毫秒,该动画由上面的Timer线程不断执行

    }


    public void move()  {
        if (dx == moving_speed && checkRoute(Direction.RIGHT)) {
            real_x += dx;
        }
        if (dx == -moving_speed && checkRoute(Direction.LEFT)) {
            real_x += dx;
        }
        if (dy == moving_speed && checkRoute(Direction.DOWN)) {
            real_y += dy;
        }
        if (dy == -moving_speed && checkRoute(Direction.UP)) {
            real_y += dy;
        }
        lab_man.setLocation(real_x, real_y);
    }



    //设置炸弹
    public void setBomb(int map_x, int map_y) {
        if(bombMap.size()  < bomb_count && gameframe.dates[map_y / ONE_STEP][map_x/ONE_STEP] != BOMB_IN_ROUTE) {
            Bomb b = new Bomb(gameframe, map_x, map_y, single_offset);
            bombMap.put(String.valueOf(map_x) + String.valueOf(map_y), b);
            gameframe.add(b.bomb_obj);
//            bomb_count--;
        }
    }
    JLabel man_dead;
    public void setDeadImage() {
    	Icon i = new ImageIcon("src/images/Dead.png");
    	man_dead = new JLabel(i);
    	man_dead.setBounds(real_x, real_y, 48, 48);
    	gameframe.add(man_dead);
    	man_dead.setVisible(false);
    }

    public void getDeadImage() {
    	man_dead.setBounds(real_x, real_y, 48, 48);
    	man_dead.setVisible(true);
    	
    }
}
