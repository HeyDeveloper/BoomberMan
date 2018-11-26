package core;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class StartUIAdapter {
	
	StartGame gameframe = null;
	public static final int MOS_X = 220;	//定义选择按键的怪物x坐标
	public static final int MOS_Y = 540;	//定义选择按键的怪物y坐标
	public static final int ONE_STEP = 48;	//一个单位像素为48
	
	public StartUIAdapter(StartGame startGame) {
		super();
		this.gameframe = startGame;
	}

	public void startUIAdapter(int key) {
	
		int x = (int)gameframe.startUI.lab_monster.getLocation().getX();	//获取怪物标签的x坐标方法
		int y = (int)gameframe.startUI.lab_monster.getLocation().getY();	//获取怪物标签的y坐标方法
		
		if(key == KeyEvent.VK_UP) {
			if(y == MOS_Y)	//设置障碍，即在开始游戏位置如果按了向上按钮，则直接return(即不做处理)								
				return;
			else {			//否则怪物移动，即怪物如果在退出按钮位置，按向上，则重写标签位置
				gameframe.startUI.lab_monster.setLocation(x,y-ONE_STEP);
				Icon i = new ImageIcon("src/images/monster.png");
				gameframe.startUI.lab_monster.setIcon(i);
			}
		}
		
		if(key == KeyEvent.VK_DOWN) {			
			if(y == (MOS_Y+ONE_STEP))	//判断小怪物的位置
				return;
			else {
				gameframe.startUI.lab_monster.setLocation(x,y+ONE_STEP);
				Icon i = new ImageIcon("src/images/monster.png");
				gameframe.startUI.lab_monster.setIcon(i);	
			}
		}
	
		//这里直接判断y的位置，并如果监听到回车按键，就做相应的处理
		if(y == MOS_Y && key == KeyEvent.VK_ENTER) {
			gameframe.startUI.closeAll();
			gameframe.close_startUI = true;
			gameframe.Start();
		}
		
		if(y==MOS_Y+48 && key==10  ) {
			gameframe.dispose();			//如果在退出按钮的位置，监听到回车，直接关闭窗口
		}
	}
	
}