package hevs.gdx2d.lib;

import hevs.gdx2d.lib.interfaces.AndroidResolver;
import hevs.gdx2d.lib.interfaces.GameInterface;
import hevs.gdx2d.lib.interfaces.KeyboardInterface;
import hevs.gdx2d.lib.interfaces.TouchInterface;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

/**
 * The base class that should be sub-classed by all
 * <code>gdx2d</code> applications. To get the functionalities you simply have
 * to overload the methods you need.
 * 
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.1
 */
public abstract class PortableApplication implements TouchInterface,
		KeyboardInterface, GameInterface {

	/**
	 * Default window dimensions
	 */
	private static final int DEFAULT_HEIGHT = 500;
	private static final int DEFAULT_WIDTH = 500;
	
	protected boolean onAndroid;

	private AndroidResolver resolver = null;
	
	/**
	 * Changes the title of the window (Desktop only)
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		if (Gdx.app.getType() == ApplicationType.Android)
			Gdx.app.log("Warning", "Title can not be set on Android");

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

	@Override
	public void onGameLogicUpdate() {				
	}
	
	/**
	 * Invoked when the pointer is depressed (once)
	 */
	@Override
	public void onClick(int x, int y, int button) {
	}

	/**
	 * Invoked when the pointer (mouse or touch) is moved and down
	 */
	@Override
	public void onDrag(int x, int y) {
	}

	/**
	 * Invoked when the mouse is crolled
	 */
	@Override
	public void onScroll(int amount) {
	}
	
	/**
	 * Invoked when the pointer (mouse or touch) is released
	 */
	@Override
	public void onRelease(int x, int y, int button) {
	}

	/**
	 * Invoked when a key is typed. Use Input.Keys.xy to read the keycode
	 */
	@Override
	public void onKeyDown(int keycode) {
		if(keycode == Input.Keys.MENU){
			Gdx.input.vibrate(300);			
		}
	}

	/**
	 * Invoked when a key is released. Use Input.Keys.xy to read the keycode
	 */
	@Override
	public void onKeyUp(int keycode) {
	}			
			
	/**
	 * Invoked for a zoom gesture on Android
	 * @param initialDistance
	 * @param distance
	 * @return
	 */
	public void onZoom(float initialDistance, float distance) {						
	}
	
	/**
	 * Invoked for a tap gesture on Android (see {@link GestureListener})
	 * @param x
	 * @param y
	 * @param count
	 * @param button
	 * @return
	 */
	public void onTap(float x, float y, int count, int button) {				
	}
		
	/**
 	 * Invoked for a ping gesture on Android (see {@link GestureListener})
	 * @param initialPointer1
	 * @param initialPointer2
	 * @param pointer1
	 * @param pointer2
	 * @return
	 */
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {				
	}
		
	/**
	 * Invoked for a pan gesture on Android (see {@link GestureListener})
	 * @param x
	 * @param y
	 * @param deltaX
	 * @param deltaY
	 * @return
	 */
	public void onPan(float x, float y, float deltaX, float deltaY) {	
		
	}
		
	/**
	 * Invoked for a long press on Android (see {@link GestureListener})
	 * @param x
	 * @param y
	 * @return
	 */
	public void onLongPress(float x, float y) {		
	}
	
	/**
	 * Invoked on a fling gesture on Android (see {@link GestureListener})
	 * @param velocityX
	 * @param velocityY
	 * @param button
	 * @return
	 */
	public void onFling(float velocityX, float velocityY, int button) {		
	}
	
	/**
	 * Invoked when the application is terminated (on Android and desktop)
	 */
	@Override
	public void onDispose() {
	}
	
	/**
	 * Invoked when the application is paused (on Android)
	 */
	@Override
	public void onPause() {						
	}
	
	/**
	 * Invoked when the application is restarted (on Android)
	 */
	@Override
	public void onResume() {			
	}

	/**
	 * Set the {@link AndroidResolver} when the application
	 * is created on Android.
	 * The resolver must be create in an Android Activity with its Context
	 * @since 1.1
	 * @param resolver the Android resolver
	 */
	public void setAndroidResolver(AndroidResolver resolver) {
		this.resolver = resolver;
	}
	
	/**
	 * Return the {@link AndroidResolver} to use Android actions.
	 * @since 1.1
	 * @return The resolver, or null if no running on Android
	 */
	public AndroidResolver getAndroidResolver() {
		return resolver;
	}
	
	/**
	 * Creates an application using GDX2D
	 * @param onAndroid true if running on Android
	 * @param width The width of the screen (if running desktop)
	 * @param height The height of the screen (if running desktop)
	 */
	public PortableApplication(boolean onAndroid, int width, int height) {
		this.onAndroid = onAndroid;
		
		if (!onAndroid)			
		{
			// Not sure if usable
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.resizable = false;
			config.useGL20 = true;
			config.height = height;
			config.width = width;
			config.fullscreen = false;
			config.title = "gdx2d application";
			config.vSyncEnabled = true; // Ignored under Linux						
			config.foregroundFPS = 60; // Target value if vSync not working
			config.backgroundFPS = config.foregroundFPS;
			config.samples = 2; // Multi-sampling enables anti-alias for lines
			
			String os = System.getProperty("os.name").toLowerCase();
			
			// Under windows, the icon *must* be the small one
			if(os.contains("win")){						
				config.addIcon("data/icon16.png", FileType.Internal);
			}
			
			config.addIcon("data/icon32.png", FileType.Internal);
			config.addIcon("data/icon64.png", FileType.Internal);			
			new LwjglApplication(new Game2D(this), config);
		}
	}
	
	/**
	 * Creates an application using gdx2d
	 * @param onAndroid True if running on Android
	 */
	public PortableApplication(boolean onAndroid) {
		this(onAndroid, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}