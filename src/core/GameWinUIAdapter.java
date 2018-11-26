package core;

import java.awt.event.KeyEvent;

public class GameWinUIAdapter {
StartGame gameframe = null;
	
	public GameWinUIAdapter(StartGame startGame) {
		super();
		this.gameframe = startGame;
	}

	public void gameWinUIAdapter(int key) {
		gameframe.GOUI.gameWinUI_Add();
		if(key == KeyEvent.VK_DOWN) {
			gameframe.GOUI.selectMonster_up();
			gameframe.exit_flag = true;
		}
		if(key == KeyEvent.VK_UP) {
			gameframe.GOUI.selectMonster_down();
			gameframe.exit_flag = false;
		}
		
		if(key == KeyEvent.VK_ENTER) {
			if(gameframe.exit_flag) {
				gameframe.dispose();			//退出位置监听到回车，关闭窗口
			}
			else {
				gameframe.dispose();			
				new StartGame();
			}
		}
	}
}
