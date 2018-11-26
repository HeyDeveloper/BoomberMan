package core;

import java.awt.event.KeyEvent;

public class GameOverUIAdapter {

	StartGame gameframe = null;
	
	public GameOverUIAdapter(StartGame startGame) {
		super();
		this.gameframe = startGame;
	}

	public void gameOverUIAdapter(int key) {
		gameframe.GOUI.gameOverUI_Add();
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
				gameframe.dispose();			//退出位置监听到回车，关闭窗口
				new StartGame();
			}
		}
	}
}
