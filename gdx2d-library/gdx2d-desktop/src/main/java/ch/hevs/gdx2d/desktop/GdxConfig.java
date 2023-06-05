package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Default configuration for {@code gdx2d} applications running on desktop.
 *
 * @author Pierre-André Mudry (mui)
 */
public class GdxConfig {

	// FIXME: only used for desktop applications, must not be included in the library project

	static public Lwjgl3ApplicationConfiguration getLwjglConfig(int width, int height, boolean fullScreen) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES20, 0, 0);
    config.setResizable(false);
    config.setForegroundFPS(60);
    config.setIdleFPS(60);
    if (fullScreen) {
      config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
    } else {
      config.setWindowedMode(width, height);
    }

		config.setTitle("Gdx2d desktop application");
    config.useVsync(true);
    config.setBackBufferConfig(8,8,8,8,16,0,3);
    //config.forceExit = false; // Setting true calls system.exit(), with no coming back

		final String os = System.getProperty("os.name").toLowerCase();

		// Under windows, the icon *must* be the small one
		if (os.contains("win")) {
      //config.addIcon("res/lib/icon16.png", Files.FileType.Internal);
		}

    //config.addIcon("res/lib/icon32.png", Files.FileType.Internal);
    //config.addIcon("res/lib/icon64.png", Files.FileType.Internal);

		return config;
	}

}
