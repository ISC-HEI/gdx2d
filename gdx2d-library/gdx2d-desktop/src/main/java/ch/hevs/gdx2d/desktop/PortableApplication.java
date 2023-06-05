package ch.hevs.gdx2d.desktop;

import java.awt.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ch.hevs.gdx2d.lib.interfaces.*;

/**
 * The base class that should be sub-classed by all {@code gdx2d} applications. To get the functionality required you
 * simply have to overload the required methods.
 *
 * @see TouchInterface
 * @see KeyboardInterface
 * @see GameInterface
 * @see GestureInterface
 * @see ControllersInterface
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
public abstract class PortableApplication
		implements TouchInterface, KeyboardInterface, GameInterface, GestureInterface, ControllersInterface {

	// Default window dimensions
	private static final int DEFAULT_HEIGHT = 500;
	private static final int DEFAULT_WIDTH = 500;

	@Deprecated
	protected final boolean onAndroid = onAndroid();

	/**
	 * {@code true} if the application is running on Android or {@code false} if running on desktop.
	 */
	private AndroidResolver resolver = null;

	/**
	 * Detect android
	 *
	 * @return true when running on Android.
	 */
	public boolean onAndroid() {
		try {
			Class.forName("android.app.Activity");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * Creates an application using {@code gdx2d}. Use a default windows size.
	 *
	 * @param onAndroid {@code true} if running on Android, {@code false} otherwise
	 */
	@Deprecated
	public PortableApplication(boolean onAndroid) {
		this(onAndroid, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates an application using {@code gdx2d}. Use a default windows size.
	 */
	public PortableApplication() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates an application using {@code gdx2d}. Screen dimension are ignored when running on Android.
	 *
	 * @param onAndroid {@code true} if running on Android, {@code false} otherwise
	 * @param width The width of the screen (only for desktop)
	 * @param height The height of the screen (only for desktop)
	 */
	@Deprecated
	public PortableApplication(boolean onAndroid, int width, int height) {
		this(onAndroid, width, height, false);
	}

	/**
	 * Creates an application using {@code gdx2d}. Screen dimension are ignored when running on Android.
	 *
	 * @param width The width of the screen (only for desktop)
	 * @param height The height of the screen (only for desktop)
	 */
	public PortableApplication(int width, int height) {
		this(width, height, false);
	}

	/**
	 * Create a full-screen {@code gdx2d} application.
	 *
	 * @param width The width of the screen
	 * @param height The height of the screen
	 * @param fullScreen Indicates if the application should be launched full screen on desktop, or in default resolution
	 */
	public PortableApplication(int width, int height, boolean fullScreen) {
		if (onAndroid()) {
			fullScreen = false;
		}

		if (fullScreen) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			width = gd.getDisplayMode().getWidth();
			height = gd.getDisplayMode().getHeight();
		}

		// We only create a context when we were not built from the DemoSelector
		if (!onAndroid() && !fromDemoSelector() && CreateLwjglApplication)
			createLwjglApplication(width, height, fullScreen);
	}

	/**
	 * Creates an application using {@code gdx2d}, running full screen or not on desktop. Screen dimension are ignored
	 * when running on Android.
	 *
	 * @param onAndroid {@code true} if running on Android, {@code false} otherwise
	 * @param width The width of the screen (only for desktop)
	 * @param height The height of the screen (only for desktop)
	 * @param fullScreen {@code true} to create a fullscreen application (only for desktop)
	 */
	@Deprecated
	public PortableApplication(boolean onAndroid, int width, int height, boolean fullScreen) {
		if (!onAndroid && !fromDemoSelector() && CreateLwjglApplication)
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

	/* TouchInterface callbacks */

	/**
	 * Change the title of the application window (works only on desktop). This method has no effect (ignored) when
	 * running on Android.
	 *
	 * @param title the application title
	 */
	public void setTitle(String title) {
		Gdx.graphics.setTitle(title);
	}

	/**
	 * Rendering surface information
	 *
	 * @return The height of the display surface (window)
	 */
	public int getWindowHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * Rendering surface information
	 *
	 * @return The width of the display surface (window)
	 */
	public int getWindowWidth() {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Schedule an exit from the application. On android, this will cause a call to {@link #onPause()} and
	 * {@link #onDispose()} some time in the future, it will not immediately finish your application.
	 */
	public void exit() {
		Gdx.app.exit();
	}

	/* KeyboardInterface callbacks */

	@Override
	public void onClick(int x, int y, int button) {
	}

	@Override
	public void onDrag(int x, int y) {
	}

	/* GameInterface callbacks */

	// Must be implemented in every subclasses:
	// void onInit();
	// void onGraphicRender(GdxGraphics g);

	@Override
	public void onRelease(int x, int y, int button) {
	}

	@Override
	public void onScroll(int amount) {
	}

	@Override
	public void onKeyDown(int keycode) {
		if (keycode == Input.Keys.MENU) {
			Gdx.input.vibrate(300);
		}

		// Exit when the {@code ESCAPE} key is pressed
		if (keycode == Input.Keys.ESCAPE) {
			Gdx.app.exit();
		}
	}

	@Override
	public void onKeyUp(int keycode) {
	}

	/* GestureInterface callbacks */

	@Override
	public void onDispose() {
	}

	@Override
	public void onPause() {
		// Android only
	}

	@Override
	public void onGameLogicUpdate() {
	}

	@Override
	public void onResume() {
		// Android only
	}

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

	/* ControllersInterface callbacks */

	@Override
	public void onControllerConnected(Controller controller) {
		// FIXME: not called on Windows with XBox One game-pad
	}

	@Override
	public void onControllerDisconnected(Controller controller) {

	}

	@Override
	public void onControllerKeyDown(Controller controller, int buttonCode) {

	}

	@Override
	public void onControllerKeyUp(Controller controller, int buttonCode) {

	}

	@Override
	public void onControllerAxisMoved(Controller controller, int axisCode, float value) {

	}

	@Override
	public void onControllerXSliderMoved(Controller controller, int sliderCode, boolean value) {
		// FIXME: not called on Windows with XBox One game-pad
	}

	@Override
	public void onControllerYSliderMoved(Controller controller, int sliderCode, boolean value) {
		// FIXME: not called on Windows with XBox One game-pad
	}

	@Override
	public void onControllerAccelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// FIXME: not called on Windows with XBox One game-pad
	}

	/**
	 * Return the {@link AndroidResolver} to use Android actions.
	 *
	 * @return The resolver, or null if no running on Android
	 * @since 1.1
	 */
	public AndroidResolver getAndroidResolver() {
		return resolver;
	}

	/**
	 * Set the {@link AndroidResolver} when the application is created on Android. The resolver must be create in an
	 * Android Activity with its Context
	 *
	 * @param resolver the Android resolver
	 * @since 1.1
	 */
	public void setAndroidResolver(AndroidResolver resolver) {
		this.resolver = resolver;
	}

	// TODO This is ugly and only required for the DemoSwingIntegration to prevent the creation of context
	public static boolean CreateLwjglApplication = true;

	private void createLwjglApplication(int width, int height, boolean fullScreen) {
		assert (!onAndroid());
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		Lwjgl3ApplicationConfiguration config = GdxConfig.getLwjglConfig(width, height, fullScreen);

		Game2D theGame = new Game2D(this);
    Lwjgl3Application a = new Lwjgl3Application(theGame, config);
	}
}
