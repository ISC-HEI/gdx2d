package ch.hevs.gdx2d.lib;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Default configuration for a {@code gdx2d} application running on the desktop.
 *
 * @author Pierre-André Mudry (mui)
 */
public class GdxConfig {

	// FIXME: only used to desktop applications

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
			config.addIcon("icon16.png", Files.FileType.Internal);
		}

		config.addIcon("icon32.png", Files.FileType.Internal);
		config.addIcon("icon64.png", Files.FileType.Internal);

		return config;
	}

}
