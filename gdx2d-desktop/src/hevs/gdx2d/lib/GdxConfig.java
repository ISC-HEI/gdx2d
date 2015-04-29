package hevs.gdx2d.lib;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Pierre-André Mudry on 29.04.2015.
 * HES-SO Valais, 2015
 */
public class GdxConfig {

	static public LwjglApplicationConfiguration getLwjglConfig(int width, int height, boolean fullScreen) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.useGL30 = false;
		config.height = height;
		config.width = width;
		config.fullscreen = fullScreen;
		config.title = "GDX2D application";
		config.vSyncEnabled = true; // Ignored under Linux
		config.foregroundFPS = 60; // Target value if vSync not working
		config.backgroundFPS = config.foregroundFPS;
		config.samples = 3; // Multi-sampling enables anti-alias for lines
		config.forceExit = false; // Setting true calls system.exit(), with no coming back

		String os = System.getProperty("os.name").toLowerCase();

		// Under windows, the icon *must* be the small one
		if (os.contains("win")) {
			config.addIcon("lib/icon16.png", Files.FileType.Internal);
		}

		config.addIcon("lib/icon32.png", Files.FileType.Internal);
		config.addIcon("lib/icon64.png", Files.FileType.Internal);

		return config;
	}

}
