package core;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class StartUI {

	public JLabel lab_monster;				
	public JLabel lab_st;
	public JLabel lab_ex;
	public JLabel lab_start;
	public static final int MOS_X = 220;	//定义选择按键的怪物x坐标
	public static final int MOS_Y = 540;	//定义选择按键的怪物y坐标
	public StartGame gameframe = null;

	public StartUI(StartGame gameframe) {
		super();
		this.gameframe = gameframe;
		setStartUI();
	}

	/**
	 * 选择按键的怪物移动方法
	 */
	private void setStartUI() {			
		Icon i = new ImageIcon("src/images/monster.png");	//定义怪物图片
		lab_monster = new JLabel(i);						
		lab_monster.setBounds(MOS_X, MOS_Y, 48, 48);		
		gameframe.add(lab_monster);								
		
		i = new ImageIcon("src/images/StGame.png");			//“开始游戏”为一张图片
		lab_st = new JLabel(i);
		lab_st.setBounds(275, 540, 192, 48);
		gameframe.add(lab_st);
		
		i = new ImageIcon("src/images/ExGame.png");			//“结束游戏”也为一张图片
		lab_ex = new JLabel(i);
		lab_ex.setBounds(275, 588, 192, 48);
		gameframe.add(lab_ex);
		
		i = new ImageIcon("src/images/start.png");			//设置开始界面的图片
		lab_start = new JLabel(i);
		lab_start.setBounds(0, 0, 720, 720);
		gameframe.add(lab_start);		
	}
	
	public void closeAll() {
		lab_monster.setVisible(false);			
		lab_st.setVisible(false);
		lab_ex.setVisible(false);
		lab_start.setVisible(false);
	}
}
