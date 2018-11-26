package handler;

import javax.swing.*;

import static core.StartGame.ONE_STEP;

public class Direction {
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final int MIDDLE = 5;


    public static void moveSloly(JLabel monster, int direction) {
        int step = ONE_STEP;
        switch (direction) {
            case Direction.RIGHT:
                while (step > 0) {
                    try {
                        Thread.sleep(10);
                        monster.setLocation(monster.getX() + 1, monster.getY());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    step--;
                }
                break;
            case Direction.LEFT:
                while (step > 0) {
                    try {
                        Thread.sleep(10);
                        monster.setLocation(monster.getX() - 1, monster.getY());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    step--;
                }
                break;
            case Direction.UP:
                while (step > 0) {
                    try {
                        Thread.sleep(10);
                        monster.setLocation(monster.getX(), monster.getY() - 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    step--;
                }
                break;
            case Direction.DOWN:
                while (step > 0) {
                    try {
                        Thread.sleep(10);
                        monster.setLocation(monster.getX(), monster.getY() + 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    step--;
                }
                break;
        }

    }
    
    
    /**
     *
     * @param lab_obj 传递要移动的Jpanel
     * @param DIRECTION  传递方向，分别有Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
     */
    static public void go(JLabel lab_obj, int DIRECTION) {
        switch (DIRECTION) {
            case LEFT:
                lab_obj.setLocation(lab_obj.getX() - ONE_STEP, lab_obj.getY());
                break;
            case RIGHT:
                lab_obj.setLocation(lab_obj.getX() + ONE_STEP, lab_obj.getY());
                break;
            case UP:
                lab_obj.setLocation(lab_obj.getX(), lab_obj.getY() - ONE_STEP);
                break;
            case DOWN:
                lab_obj.setLocation(lab_obj.getX(), lab_obj.getY() + ONE_STEP);
                break;
        }
    }
    static public void go(JLabel lab_obj, int coordXInArray, int coordYInArray) {
    	lab_obj.setLocation(coordXInArray *ONE_STEP, coordYInArray * ONE_STEP);
    }
    
    

    public class Coords {
        public int X;
        public int Y;
        public int direction;
        Coords (int x, int y, int direction) {
            this.X = x;
            this.Y = y;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coords coords = (Coords) o;
            return X == coords.X &&
                    Y == coords.Y;
        }

        @Override
        public String toString() {
            return "Coords{" +
                    "X=" + X +
                    ", Y=" + Y +
                    ", direction=" + direction +
                    '}';
        }
    }


    /**
     * 获取真实坐标
     * @param lab_obj
     * @param DIRECTION
     * @return
     */
     public static Coords getRealCoords(JLabel lab_obj, int DIRECTION) {
        switch (DIRECTION) {
                case MIDDLE:
                    return new Direction().new Coords(lab_obj.getX(), lab_obj.getY(), MIDDLE);
                case LEFT:
					return new Direction().new Coords(lab_obj.getX()-ONE_STEP, lab_obj.getY(), LEFT);
				case RIGHT:
                    return new Direction().new Coords(lab_obj.getX() + ONE_STEP, lab_obj.getY(), RIGHT);
				case UP:
                    return new Direction().new Coords(lab_obj.getX(), lab_obj.getY() - ONE_STEP, UP);
				case DOWN:
                    return new Direction().new Coords(lab_obj.getX(), lab_obj.getY() + ONE_STEP, DOWN);
        }
        return null;
    }

    /**
     * 获取数组上的坐标
     * @param lab_obj
     * @param DIRECTION
     * @return
     */
     public static Coords getArrayCoords(JLabel lab_obj, int DIRECTION) {
        switch (DIRECTION) {
            case MIDDLE:
                return new Direction().new Coords((lab_obj.getY()) / ONE_STEP, (lab_obj.getX()) / ONE_STEP, MIDDLE);
            case LEFT:
                return new Direction().new Coords((lab_obj.getY()) / ONE_STEP, (lab_obj.getX() - ONE_STEP) / ONE_STEP, LEFT);
            case RIGHT:
                return new Direction().new Coords((lab_obj.getY()) / ONE_STEP, (lab_obj.getX() + ONE_STEP) / ONE_STEP, RIGHT);
            case UP:
                return new Direction().new Coords((lab_obj.getY() - ONE_STEP) / ONE_STEP, (lab_obj.getX()) / ONE_STEP, UP);
            case DOWN:
                return new Direction().new Coords((lab_obj.getY() + ONE_STEP) / ONE_STEP, (lab_obj.getX()) / ONE_STEP, DOWN);
        }
        return null;
    }

    /**
     * 判断两个Jlabel标签的坐标是否相同
     * @param jlabel1
     * @param jlabel2
     * @return
     */
    static public boolean isCoordsEqual(JLabel jlabel1, JLabel jlabel2) {
        if(getArrayCoords(jlabel1, MIDDLE).equals(getArrayCoords(jlabel2, MIDDLE))) {
            return true;
        }
        return false;
    }
}