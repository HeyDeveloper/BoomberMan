package core;


import goods.Prop;
import handler.Direction;
import handler.Handler;
import monster.Monster;
import monster.MonsterCount;
import people.BomberMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Guzhz
 * @date 2018年10月29日
 * @time 下午4:08:12
 */

/**
 * 
 * @author Guzhz
 * @date 2018年11月3日
 * @time 下午1:45:44
 */

public class StartGame extends JFrame implements KeyListener {
	

	private static final long serialVersionUID = 1L;
	boolean close_startUI = false;
	StartUI startUI;
	public static final int ONE_STEP = 48;
	public static final int FIRE_IN_ROUTE = 9;
	public static final int NORMAL_ROUTE = 4;
	public static final int BOX_IN_ROUTE = 1; 
	public static final int ROCK_IN_ROUTE = 0;
	public static final int BOMB_IN_ROUTE = 8;
	public static final int MAN_WIN = 1;			//人物赢了状态
	public static final int MAN_NORMAL = 0;			//人物正常状态
	public static final int MAN_DEAD = -1;			//人物死了状态
	int man_state = MAN_NORMAL;	//-1是死亡，0是正常，1是胜利		


	//0代表石头，1代表箱子，4代表陆地
	public int[][] dates = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,4,1,4,1,4,4,1,1,1,1,1,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,1,0,0,0,4,4,4,4,4,0,0,0,0,0},
			{0,4,4,4,4,4,4,0,0,4,4,4,4,4,0},
			{0,1,1,1,4,4,1,1,1,1,1,1,1,1,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,1,1,0,0,0,0,4,4,1,1,1,0,0,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,4,1,4,1,1,4,1,4,4,4,1,4,1,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,4,4,0,4,1,0,4,4,0,0,4,0,4,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,4,4,4,4,4,4,4,4,4,4,4,4,4,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}				//测试数组
	};
	
	public StartGame() {
		
		startUI = new StartUI(this);		//开始界面
		gameOverInit();			//结束界面初始化
		manInit();				//人物初始化
		boxInit();				//箱子初始化
		propInit();				//道具初始化
		blockInit();			//石头初始化
		setGameFrameUi();		//设置基本窗口
		handlerInit();	
				//处理工具初始化
	}
	
	public void Start() {		//游戏开始再初始化怪物
        monsterInit(2, 500); 	//怪物初始化,参数1：怪物数量，参数2：怪物移动速度
	}
	
	/**
	 * 	游戏界面初始化
	 */
	public GameOverUI GOUI;
	private void gameOverInit() {
		GOUI = new GameOverUI(this);
	}

	
	/**
	 * 	人物初始化
	 */
	public BomberMan aBomberMan;
	private void manInit() {
			//初始化人物
		aBomberMan = new BomberMan(this, 6, 3);		//放在数组  6 3 位置
		aBomberMan.setBloodCount();					//new好人物后，设置血量
	}
	
	/**
	 * 箱子初始化
	 */
	public HashMap<String, JLabel> boxMap = new HashMap<String, JLabel>();
	public void boxInit() {
		//初始化箱子1		同理设置箱子
		for(int i = 0; i<dates.length; i++) {
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1)
				{
					JLabel lab_case = new JLabel(new ImageIcon("src/images/case.png"));
					lab_case.setBounds(ONE_STEP*j, ONE_STEP*i, ONE_STEP, ONE_STEP);
					this.add(lab_case);
					//每个箱子对象存起来，方便爆炸后移除,加入box标记
//                  lab_case.setText("box");
					boxMap.put(String.valueOf(j) + String.valueOf(i),lab_case);
				}
			}
		}
		this.validate();
		this.repaint();
		this.revalidate();
	}
		
	/**
	 *   道具初始化
	 */
	public Prop prop;
	private void propInit() {
		prop = new Prop(this,aBomberMan,dates);
		prop.setBloodProp();
		prop.setBombProp();
		prop.setFireProp();
		prop.setShoesProp();
	}
	

	/**
	 * 	石头初始化
	 */
	private void blockInit() {
		//初始化石头0
		Icon ic = new ImageIcon("src/images/block.png");
		for(int i = 0; i<dates.length; i++) {				//遍历地图数组设置石头
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 0)
				{
					JLabel lab_block = new JLabel(ic);
					lab_block.setBounds(ONE_STEP*j, ONE_STEP*i, ONE_STEP, ONE_STEP);
					this.add(lab_block);
				}
			}
		}
	}

	/**
	 * 	怪物初始化
	 */
	public MonsterCount mc;	//建立一个专门处理怪物数量的类
	public ConcurrentHashMap<Thread, Monster> monsterMap = new ConcurrentHashMap<>();
	private void monsterInit(int monster_count, long moving_speed) {
		mc = new MonsterCount(monster_count);		
		for (int i = 0; i < monster_count; i++) {
			Monster m = new Monster(this, dates.length-2, dates.length-2, moving_speed, mc, GOUI);		
			Thread t = new Thread(m);
            t.start();
            monsterMap.put(t,m);
		}
	}

	/**
	 * 设置基本窗体
	 */
	public void setGameFrameUi() {
		//设置基本窗体
    	this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("炸弹人 v1.08   #GDPU 5-523爆破组");
		this.setSize(726,754);
		this.setLocation(500, 180);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.gray);
		this.getContentPane().setVisible(true);
		this.setVisible(true);
		this.addKeyListener(this);
	}
	
	/**
	 *  处理工具初始化
	 */
	private void handlerInit() {
		Handler fireHandler = new Handler(this);
		new Timer().schedule(new TimerTask() {
			public void run() {
				fireHandler.check();
			}
		}, 0, 20);
	}	
		
	
	public void keyTyped(KeyEvent e) {
		
	} 

	int bomberman_direction_with_bomb = 40;  //记录当前人物的方向,默认为40,因为40位键盘的下键盘，即正面
	boolean exit_flag = false;	//记录结束界面的退出选择是否按下了回车键
	//按下监听

	StartUIAdapter startadapter = new StartUIAdapter(this);
	GameOverUIAdapter gameOverUIAdapter = new GameOverUIAdapter(this);
	GameWinUIAdapter gameWinUIAdapter = new GameWinUIAdapter(this);
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(close_startUI == false) {
			startadapter.startUIAdapter(key);
		}
		else {
			Victory();	//优先判断是否胜利
			
			if(man_state == MAN_DEAD) {	  //死亡状态执行
				gameOverUIAdapter.gameOverUIAdapter(key);
			}
			
			if(man_state == MAN_NORMAL) {	//正常状态执行
			    aBomberMan.keyPress(e);
				prop.checkProp();			//每次移动都检测是否有道具
			}
			
			if(man_state == MAN_WIN) {		//胜利状态执行
				gameWinUIAdapter.gameWinUIAdapter(key);}
		}
	}

	public void keyReleased(KeyEvent e) {
		aBomberMan.keyRelease(e);
	    //键盘弹起，判断人物是否处于危险区域
	}

	
	public void Victory() {		//胜利则改为胜利状态
		if(mc.getState()) {
			man_state = MAN_WIN;
		}
	}
	
	void manGoDead() {
	    //如果无敌，则不死
	    if(isManInvincible()) {
	    	return;
		}
		aBomberMan.blood_count--;			//真实血量减一
		aBomberMan.BC.reduceBloodUI();			//判断人死后，生命值减一
	    aBomberMan.setManInvincible(true);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				aBomberMan.setManInvincible(false);
			}
		}, 2000);
		if(aBomberMan.blood_count > 0) {
			System.out.println("blood.." + aBomberMan.blood_count);
			return ;
		}
		man_state = MAN_DEAD;					//人物死亡状态置
		GOUI.gameOverUI_Add();					//死后弹出结束界面
		aBomberMan.lab_man.setVisible(false);
		aBomberMan.getDeadImage();
	}


	boolean isManInvincible() {
		if (aBomberMan.bomber_man_invincible == true) {
			return true;
		}
		return false;
	}
	
	//实时判断是否死亡
	public boolean isPeopleInDanger() {
		if (dates[aBomberMan.lab_man.getY()/ONE_STEP][aBomberMan.lab_man.getX()/ONE_STEP] == FIRE_IN_ROUTE 
				|| isManMeetMonster()) {
		    manGoDead();
			return true;
		}
		return false;
	}
	
	
	boolean isManMeetMonster () {
		Set<Map.Entry<Thread, Monster>> set = monsterMap.entrySet();
		for (Iterator<Map.Entry<Thread, Monster>> iterator = set.iterator(); iterator.hasNext(); ) {
			Map.Entry<Thread, Monster> monsterEntry = iterator.next();
			if(Direction.isCoordsEqual(monsterEntry.getValue().monster, aBomberMan.lab_man)) {
				return true;
			}
		}
		return false;
	}
	
}

