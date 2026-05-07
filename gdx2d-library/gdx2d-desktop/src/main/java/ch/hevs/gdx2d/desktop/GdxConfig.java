package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.SharedLibraryLoader;

/**
 * Default configuration for {@code gdx2d} applications running on desktop.
 *
 * @author Pierre-André Mudry (mui)
 */
public class GdxConfig {

	static public Lwjgl3ApplicationConfiguration getLwjgl3Config(int width, int height, boolean fullScreen) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setResizable(false);
		config.useVsync(true); // Ignored under Linux
		config.setForegroundFPS(60); // Target value if vSync not working
		config.setIdleFPS(60);
		// Multi-sampling enables anti-alias for lines (MSAA = 3 samples)
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 3);
		config.setTitle("Gdx2d desktop application");

		if (fullScreen) {
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		} else {
			config.setWindowedMode(width, height);
		}

		// Under windows, the icon *must* be the small one (16x16 first)
		if (SharedLibraryLoader.isWindows) {
			config.setWindowIcon(Files.FileType.Internal,
					"res/lib/icon16.png", "res/lib/icon32.png", "res/lib/icon64.png");
		} else {
			config.setWindowIcon(Files.FileType.Internal,
					"res/lib/icon32.png", "res/lib/icon64.png");
		}

		return config;
	}

}
