package people;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import core.StartGame;
import handler.Direction;

/**
 * 定义血条类、配合坐标获取类
 * @author Guzhz
 * @date 2018年11月7日
 * @time 上午3:37:57
 */

public class BloodCount {
	JLabel lab_bomberman;				//把人物标签传进来
	int blood_count;					//定义个血条
	int bloodX;							//血条的真实X坐标
	int bloodY;							//血条的真实X坐标
	int length = 0;						//默认6格血，宽度为43个像素，死一格-7个像素
	StartGame gameframe = null;
	Icon i = new ImageIcon("src/images/bloodborder.png");
	Icon ic = new ImageIcon("src/images/blood.png");
	JLabel lab_bloodborder = new JLabel(i);
	JLabel lab_blood = new JLabel(ic);
	
	
	public BloodCount(StartGame gameframe, BomberMan bomberman, int blood_count) { 
		this.gameframe = gameframe;
		this.lab_bomberman = bomberman.lab_man;
		this.blood_count = blood_count;	
		length = length + (blood_count*7);		//1格血加一个宽度
		setUI();								//血条初始化
	}

	public void getCoords() {			//获取坐标
		Direction.Coords coords = Direction.getRealCoords(lab_bomberman, Direction.MIDDLE);
		bloodX = coords.X+2;
		bloodY = coords.Y-4;
	}
	
	
	
	public void setUI() {			
		getCoords();		//获取真实坐标设置血条
		lab_bloodborder.setBounds(bloodX, bloodY, 43, 8);
		lab_blood.setBounds(bloodX, bloodY, length, 8);
		gameframe.add(lab_bloodborder);
		gameframe.add(lab_blood);
	}
	
	public void reduceBloodUI() {		//血条增加
		blood_count--;
		length = length - 7;
		setUI();
	}
	
	public void increaseBloodUI() {		//血条减少
		blood_count++;
		length = length + 7;
		setUI();
	}

}
