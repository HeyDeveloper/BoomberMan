package goods;

import core.StartGame;
import people.BomberMan;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 道具类-血条
 * @author Guzhz
 * @date 2018年11月9日
 * @time 上午12:38:08
 */

/**
 * 增加道具-炸弹
 * @author Guzhz
 * @date 2018年11月9日
 * @time 下午9:00:21
 */

public class Prop {
	
	private static final int ONE_STEP = 48;
	private StartGame gameframe = null;
	private JLabel lab_bomberman;
	private BomberMan bomberman = null;
	private int man_X;
	private int man_Y;
	private String man_XY;
	private int[][] dates;			//接收过来，用来遍历箱子

	/**
	 * 设置初始化道具数量
	 */

	private int bloodPropCount = 3;		//自定义血量道具数量		3+3
	private int bombPropCount = 2;		//自定义炸弹道具数量		1+2
	private int firePropCount = 3;		//自定义火焰道具数量		1+3
	private int shoesPropCount = 2;		//自定义火焰道具数量		4+2*2

	public Prop(StartGame gameframe,BomberMan bomberman,int[][] dates) {
		this.gameframe = gameframe;
		this.bomberman = bomberman;
		this.lab_bomberman = bomberman.lab_man;
		this.dates = dates;
		System.out.println();
		checkLoop();
	}

//	public void getCoords() {			//获取数组坐标
//		Direction.Coords coords = Direction.getArrayCoords(lab_bomberman, Direction.MIDDLE);
//		man_X = coords.X;
//		man_Y = coords.Y;
//		//转化为字符串
//		man_XY = Integer.toString(coords.X)+Integer.toString(coords.Y);
//	}

	public void getCoords() {            //获取数组坐标
	    int real_X = gameframe.aBomberMan.lab_man.getX();
		int real_Y = gameframe.aBomberMan.lab_man.getY();
		man_Y = real_X / ONE_STEP + (real_X % ONE_STEP > ONE_STEP/2 ? 1:0);
		man_X = real_Y / ONE_STEP + (real_Y % ONE_STEP > ONE_STEP/2 ? 1:0);

		man_XY = man_X + "" + man_Y;
	}

	// 新建线程实时判断
	public void checkLoop() {
		Timer t = new Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				checkProp();
			}
		},0,10);
	}


	
	public void checkProp() {				//检测是否有吃到道具
		getCoords();
		for(String key : bloodPropMap.keySet()) {	//键位遍历
			if(man_XY.equals(key)) {
	            JLabel ablood = bloodPropMap.get(String.valueOf(man_X) + String.valueOf(man_Y));
	            ablood.setVisible(false);
	            bloodPropMap.remove(key);		//移除键值
				bomberman.blood_count++;					//真实血量值+1
				bomberman.BC.increaseBloodUI();				//设置血条UI
				System.out.println("生命道具已经被吃掉了！现在生命数量"+bomberman.blood_count);
			}
		}
		
		for(String key : bombPropMap.keySet()) {	//键位遍历
			if(man_XY.equals(key)) {
	            JLabel abomb = bombPropMap.get(String.valueOf(man_X) + String.valueOf(man_Y));
	            abomb.setVisible(false);
	            bombPropMap.remove(key);			//移除键值
				bomberman.bomb_count++;					//真实血量值+1
				System.out.println("炸弹道具已经被吃掉了！现在炸弹数量："+bomberman.bomb_count);
			}
		}		
		
		for(String key : firePropMap.keySet()) {	//键位遍历
			if(man_XY.equals(key)) {
	            JLabel afire = firePropMap.get(String.valueOf(man_X) + String.valueOf(man_Y));
	            afire.setVisible(false);
	            firePropMap.remove(key);			//移除键值
	            bomberman.single_offset++;
	            System.out.println("这里是火焰啊！");
				System.out.println("火焰道具已经被吃掉了！现在火焰偏移量："+bomberman.single_offset);
			}
		}
		
		for(String key : shoesPropMap.keySet()) {	//键位遍历
			if(man_XY.equals(key)) {
	            JLabel ashoes = shoesPropMap.get(String.valueOf(man_X) + String.valueOf(man_Y));
	            ashoes.setVisible(false);
	            shoesPropMap.remove(key);			//移除键值
	            bomberman.speedPlusOne();
	            
	            System.out.println("这里是鞋子啊！");
				System.out.println("鞋子道具已经被吃掉了！现在鞋子量："+bomberman.moving_speed);
			}
		}
	}
	
	public ConcurrentHashMap<String, JLabel> bloodPropMap = new ConcurrentHashMap<String, JLabel>();
	
	/**
	 * 设置血量道具
	 */
	public void setBloodProp() {
		int ran = (int)(Math.random()*100+1)%10;			//设置随机生成道具：
		int ranCount = 1;									//辅助设计随机的计数器
		
		int temp_bloodPropCount = 1;		//临时血量
		
		for(int i = 0; i<dates.length; i++) {
			if(temp_bloodPropCount > bloodPropCount)
				break;
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1)
				{
					if(temp_bloodPropCount > bloodPropCount)
						break;
					if((ranCount++) <= ran)
						continue;
					else {
						System.out.println("随机位置在："+i+" "+j);
						JLabel lab_Prop_blood = new JLabel(new ImageIcon("src/images/Prop_blood.png"));
						lab_Prop_blood.setBounds(ONE_STEP*j+1, ONE_STEP*i+1, 45, 45);
						gameframe.add(lab_Prop_blood);
						bloodPropMap.put(String.valueOf(i) + String.valueOf(j),lab_Prop_blood);
//						dates[i][j] = 11;			//设置了这个道具后，把值设为11，不让后面的道具重复遍历到这个位置
						
						ran = (int)(Math.random()*100+1)%10;
						ranCount = 1;
						temp_bloodPropCount++;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 设置炸弹道具
	 */
	public ConcurrentHashMap<String, JLabel> bombPropMap = new ConcurrentHashMap<String, JLabel>();
	
	public void setBombProp() {
		int ran = (int)(Math.random()*100+1)%10;			//设置随机生成道具：
		int ranCount = 1;									//辅助设计随机的计数器
		
		int temp_bombPropCount = 1;		//临时炸弹数量
		
		for(int i = 0; i<dates.length; i++) {
			if(temp_bombPropCount > bombPropCount)
				break;
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1 && differentPosition(i,j))		//加多是否相同位置判断
				{
					if(temp_bombPropCount > bombPropCount)
						break;
					if((ranCount++) <= ran)
						continue;
					else {
						System.out.println("炸弹随机位置在："+i+" "+j);
						JLabel lab_Prop_bomb = new JLabel(new ImageIcon("src/images/Prop_bomb.png"));
						lab_Prop_bomb.setBounds(ONE_STEP*j+1, ONE_STEP*i+1, 45, 45);
						gameframe.add(lab_Prop_bomb);
						bombPropMap.put(String.valueOf(i) + String.valueOf(j),lab_Prop_bomb);
						
						ran = (int)(Math.random()*100+1)%10;
						ranCount = 1;
						temp_bombPropCount++;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 设置火焰道具
	 */
public ConcurrentHashMap<String, JLabel> firePropMap = new ConcurrentHashMap<String, JLabel>();
	
	public void setFireProp() {
		int ran = (int)(Math.random()*100+1)%10;			//设置随机生成道具：
		int ranCount = 1;									//辅助设计随机的计数器
		
		int temp_firePropCount = 1;		//临时火焰数量
		
		for(int i = 0; i<dates.length; i++) {
			if(temp_firePropCount > firePropCount)
				break;
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1 && differentPosition(i,j))		//加多是否相同位置判断
				{
					if(temp_firePropCount > firePropCount)
						break;
					if((ranCount++) <= ran)
						continue;
					else {
						System.out.println("火焰随机位置在："+i+" "+j);
						JLabel lab_Prop_fire = new JLabel(new ImageIcon("src/images/Prop_fire.png"));
						lab_Prop_fire.setBounds(ONE_STEP*j+1, ONE_STEP*i+1, 45, 45);
						gameframe.add(lab_Prop_fire);
						firePropMap.put(String.valueOf(i) + String.valueOf(j),lab_Prop_fire);
						
						ran = (int)(Math.random()*100+1)%10;
						ranCount = 1;
						temp_firePropCount++;
						break;
					}
				}
			}
		}
	}
	
public ConcurrentHashMap<String, JLabel> shoesPropMap = new ConcurrentHashMap<String, JLabel>();
	
	public void setShoesProp() {
		int ran = (int)(Math.random()*100+1)%10;			//设置随机生成道具：
		int ranCount = 1;									//辅助设计随机的计数器
		
		int temp_shoesPropCount = 1;		//临时火焰数量
		
		for(int i = 0; i<dates.length; i++) {
			if(temp_shoesPropCount > shoesPropCount)
				break;
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1 && differentPosition(i,j))		//加多是否相同位置判断
				{
					if(temp_shoesPropCount > shoesPropCount)
						break;
					if((ranCount++) <= ran)
						continue;
					else {
						System.out.println("鞋子随机位置在："+i+" "+j);
						JLabel lab_Prop_shoes = new JLabel(new ImageIcon("src/images/Prop_shoes1.png"));
						lab_Prop_shoes.setBounds(ONE_STEP*j+1, ONE_STEP*i+1, 45, 45);
						gameframe.add(lab_Prop_shoes);
						shoesPropMap.put(String.valueOf(i) + String.valueOf(j),lab_Prop_shoes);
						
						ran = (int)(Math.random()*100+1)%10;
						ranCount = 1;
						temp_shoesPropCount++;
						break;
					}
				}
			}
		}
	}
	
	
	public boolean differentPosition(int x,int y) {
		String same_XY = Integer.toString(x)+Integer.toString(y);
		for(String key : bloodPropMap.keySet()) {				//键位遍历
			if(same_XY.equals(key)) {
				return false;
			}
		}
		for(String key : bombPropMap.keySet()) {				//键位遍历
			if(same_XY.equals(key)) {
				return false;
			}
		}
		for(String key : firePropMap.keySet()) {				//键位遍历
			if(same_XY.equals(key)) {
				return false;
			}
		}
		return true;
	}
	
}
