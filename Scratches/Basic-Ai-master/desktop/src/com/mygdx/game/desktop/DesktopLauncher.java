package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
//import com.mygdx.game.Desktop_launch;
import com.mygdx.game.Main;

public class DesktopLauncher {
	static int nWidth=1280,nHeight=720;
	static Boolean bFullScreen = false;
	public  void gamesettings(int nWidth_, int nHight_, Boolean bFullScreen_){

	}
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new  LwjglApplicationConfiguration();
		config.height=nHeight;
		config.width=nWidth;
		config.fullscreen=bFullScreen;
		config.title = "Salty";
		new LwjglApplication(new Main(), config);
	}
}
