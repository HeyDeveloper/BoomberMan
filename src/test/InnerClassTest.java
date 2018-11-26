package test;


public class InnerClassTest {


    class Coords {
        public int X;
        public int Y;
        public Coords(int x, int y) {
            this.X = x;
            this.Y = y;
        }
    }

    static class directions {
        Coords getLeftRealCoords() {
            return new InnerClassTest().new Coords(3, 4);
        }
    }



    public static void main(String[] args) {
        InnerClassTest.Coords c = new InnerClassTest().new Coords(1, 2);
        System.out.println(c.X);

    }
}
