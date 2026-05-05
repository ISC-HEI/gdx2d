package ch.hevs.gdx2d.desktop;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.interfaces.GameInterface;
import ch.hevs.gdx2d.lib.interfaces.GestureInterface;
import ch.hevs.gdx2d.lib.interfaces.KeyboardInterface;
import ch.hevs.gdx2d.lib.interfaces.MouseInterface;

/**
 * The base class that should be sub-classed by all {@code gdx2d} desktop
 * applications. To get the functionality required you simply have to overload
 * the required methods.
 *
 * @see MouseInterface
 * @see KeyboardInterface
 * @see GameInterface
 * @see GestureInterface
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 2.0
 */
public abstract class DesktopApplication
		implements MouseInterface, KeyboardInterface, GameInterface, GestureInterface {

	// Default window dimensions
	private static final int DEFAULT_HEIGHT = 500;
	private static final int DEFAULT_WIDTH = 500;

	/**
	 * Escape hatch used by the demo selector / Swing integration demos to
	 * construct a {@link DesktopApplication} without spinning up an LWJGL
	 * context. Set to {@code false} before calling {@code super(...)} when you
	 * intend to host the game in an existing Swing/AWT window.
	 */
	public static boolean CreateLwjglApplication = true;

	/**
	 * Creates an application using {@code gdx2d} with a default window size.
	 */
	public DesktopApplication() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates a windowed {@code gdx2d} application.
	 *
	 * @param width  The width of the window
	 * @param height The height of the window
	 */
	public DesktopApplication(int width, int height) {
		this(width, height, false);
	}

	/**
	 * Creates a {@code gdx2d} application, optionally full-screen.
	 *
	 * @param width      The width of the window (ignored in full-screen)
	 * @param height     The height of the window (ignored in full-screen)
	 * @param fullScreen {@code true} to launch full-screen
	 */
	public DesktopApplication(int width, int height, boolean fullScreen) {
		if (fullScreen) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			width = gd.getDisplayMode().getWidth();
			height = gd.getDisplayMode().getHeight();
		}

		// We only create a context when we were not built from the DemoSelector
		if (!fromDemoSelector() && CreateLwjglApplication)
			createLwjglApplication(width, height, fullScreen);
	}

	private boolean fromDemoSelector() {
		String className = "DemoSelectorGUI";

		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

		for (StackTraceElement elem : callStack) {
			if (elem.getClassName().contains(className)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Change the title of the application window.
	 *
	 * @param title the application title
	 */
	public void setTitle(String title) {
		Gdx.graphics.setTitle(title);
	}

	/**
	 * @return The height of the display surface (window)
	 */
	public int getWindowHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * @return The width of the display surface (window)
	 */
	public int getWindowWidth() {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Schedule an exit from the application.
	 */
	public void exit() {
		Gdx.app.exit();
	}

	/* MouseInterface callbacks */

	@Override
	public void onClick(int x, int y, int button) {
	}

	@Override
	public void onDrag(int x, int y) {
	}

	@Override
	public void onRelease(int x, int y, int button) {
	}

	@Override
	public void onScroll(int amount) {
	}

	/* KeyboardInterface callbacks */

	@Override
	public void onKeyDown(int keycode) {
		// Exit when the {@code ESCAPE} key is pressed
		if (keycode == Input.Keys.ESCAPE) {
			Gdx.app.exit();
		}
	}

	@Override
	public void onKeyUp(int keycode) {
	}

	/* GameInterface callbacks */

	// Must be implemented in every subclass:
	// void onInit();
	// void onGraphicRender(GdxGraphics g);

	@Override
	public void onDispose() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onGameLogicUpdate() {
	}

	@Override
	public void onResume() {
	}

	/* GestureInterface callbacks */

	@Override
	public void onZoom(float initialDistance, float distance) {
	}

	@Override
	public void onTap(float x, float y, int count, int button) {
	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
	}

	@Override
	public void onLongPress(float x, float y) {
	}

	@Override
	public void onFling(float velocityX, float velocityY, int button) {
	}

	private void createLwjglApplication(int width, int height, boolean fullScreen) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		LwjglApplicationConfiguration config = GdxConfig.getLwjglConfig(width, height, fullScreen);

		Game2D theGame = new Game2D(this);
		new LwjglApplication(theGame, config);
	}
}
