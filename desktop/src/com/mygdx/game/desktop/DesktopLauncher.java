package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Snake Catching";
		config.width = 1800;
		config.height = 1000;
		config.pauseWhenMinimized = true;
		config.pauseWhenBackground = true;
		config.resizable = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.forceExit = false;

		new LwjglApplication(new MainGame(), config);

	}
}
