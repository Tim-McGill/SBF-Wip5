package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main_desktop;

public class DesktopLauncher {
	static int nWidth=1280,nHight=720;
	static Boolean bFullScreen = false;
	public  void gamesettings(int nWidth_, int nHight_, Boolean bFullScreen_){

	}
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new  LwjglApplicationConfiguration();
		config.height=nHight;
		config.width=nWidth;
		config.fullscreen=bFullScreen;
		config.title = "Salty";
		new LwjglApplication(new Main_desktop(), config);
	}
}
