package ru.geekbrains.stargame.desktop;

/**
 * Created by Andrey Zverkov on 27.12.2017.
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.geekbrains.stargame.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3f/4f;

		config.height = 500;
		config.width = (int) (config.height * aspect);
		new LwjglApplication(new Star2DGame(), config);
	}
}
