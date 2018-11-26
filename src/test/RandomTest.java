package test;

import java.util.LinkedList;
import java.util.Random;

public class RandomTest {

    public static void move() {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for (int i = 0; i < 4; i++) {
            linkedList.add(i);
        }

        while (linkedList.size() > 0) {
            getRoute(linkedList);
        }
    }

    private static void getRoute(LinkedList<Integer> linkedList) {
        if (linkedList.size() > 0) {
            int r = new Random().nextInt(linkedList.size());
            System.out.println(linkedList.remove(r));
        }
    }


    public static void main(String[] args) {

        move();


//        LinkedList<Integer> set = new LinkedList<Integer>();
//        for (int i = 0; i < 4; i++) {
//            set.add(i);
//        }
//        System.out.println(set + ":" + set.size());
//        while (set.size() > 0) {
//            int r = new Random().nextInt(set.size());
//            System.out.println(set.remove(r));
//        }

    }
}
