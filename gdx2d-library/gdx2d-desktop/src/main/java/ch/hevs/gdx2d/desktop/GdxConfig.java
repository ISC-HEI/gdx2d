package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Default configuration for {@code gdx2d} applications running on desktop.
 *
 * @author Pierre-André Mudry (mui)
 */
public class GdxConfig {

	// FIXME: only used for desktop applications, must not be included in the library project

	static public LwjglApplicationConfiguration getLwjglConfig(int width, int height, boolean fullScreen) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.useGL30 = false;
		config.height = height;
		config.width = width;
		config.fullscreen = fullScreen;
		config.title = "Gdx2d desktop application";
		config.vSyncEnabled = true; // Ignored under Linux
		config.foregroundFPS = 60; // Target value if vSync not working
		config.backgroundFPS = config.foregroundFPS;
		config.samples = 3; // Multi-sampling enables anti-alias for lines
		config.forceExit = false; // Setting true calls system.exit(), with no coming back

		final String os = System.getProperty("os.name").toLowerCase();

		// Under windows, the icon *must* be the small one
		if (os.contains("win")) {
			config.addIcon("res/lib/desktop/icon16.png", Files.FileType.Internal);
		}

		config.addIcon("res/lib/desktop/icon32.png", Files.FileType.Internal);
		config.addIcon("res/lib/desktop/icon64.png", Files.FileType.Internal);

		return config;
	}

}
