package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

/**
 * Default configuration for {@code gdx2d} applications running on desktop.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class GdxConfig {

    /**
     * Get LWJGL configuration with default settings (500x500 window)
     *
     * @return LWJGL application configuration
     */
    public static Lwjgl3ApplicationConfiguration getLwjglConfig() {
        return getLwjglConfig(500, 500, false);
    }

    /**
     * Get LWJGL configuration
     * <p/>
     * Default resolution available for the windowed mode:
     * <ul>
     * <li>640 * 480 (4:3)
     * <li>800 * 600 (4:3)
     * <li>1024 * 768 (4:3)
     * <li>1280 * 720 (16:9)
     * <li>1366 * 768 (16:9)
     * <li>1600 * 900 (16:9)
     * <li>1920 * 1080 (16:9)
     * </ul>
     *
     * @param width      Window width
     * @param height     Window height
     * @param fullScreen Create a fullscreen window
     * @return The configuration for LWJGL
     */
    public static Lwjgl3ApplicationConfiguration getLwjglConfig(int width, int height, boolean fullScreen) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setResizable(false);
        config.useVsync(true);
        config.setTitle("Gdx2d desktop application");

        // Set window size
        config.setWindowedMode(width, height);

        if (fullScreen) {
            config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }

        // Set up initial background color to black
        config.setInitialBackgroundColor(Color.BLACK);

        // 4 samples for multi-sampling enables anti-alias for lines
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);

        // Use OpenGL 2.0 for compatibility (major=2, minor=0)
        config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL20, 2, 0);

        // Set window icons
        config.setWindowIcon("res/lib/icon16.png", "res/lib/icon32.png", "res/lib/icon64.png");

        return config;
    }
}