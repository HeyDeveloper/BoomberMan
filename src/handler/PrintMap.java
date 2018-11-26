package handler;

import core.StartGame;

import static core.StartGame.*;

public class PrintMap {
    /**
     * 几种不同的打印地图方式
     * @param gameframe
     * @param type
     */
    public static void print(StartGame gameframe, int type) {
        System.out.println("================================   类型: "+type);
        
        System.out.print("       		    ");
        for(int i = 0;i<gameframe.dates.length; i++) {
        	if(i > 9)
        		System.out.print(" "+1);
        }
        System.out.println();
        
        for(int i = 0;i<gameframe.dates.length; i++) 
        	System.out.print(" "+i%10);
        
        System.out.println();
        
        System.out.println("_______________________________");
        for (int i = 0; i < gameframe.dates.length; i++) {
        	System.out.print("|");
        	for (int j = 0; j < gameframe.dates[i].length; j++) {
        		if(gameframe.dates[i][j] == type) {
        			System.out.print("■|");
        		}else {
        			System.out.print(" |");
        		}
        	}
        	System.out.println(" "+i);
        }
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
    }
    
}
