package test;


import java.util.Iterator;
import java.util.LinkedList;

public class LinkedLIstTest {
    public static void main(String[] args) {
        LinkedList<String> link = new LinkedList<String>();
        link.add("a");
        link.add("a");
        link.add("a");
        link.add("a");
        link.removeFirst();

        for (Iterator<String> iterator = link.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            System.out.println(next);
        }
    }
}
