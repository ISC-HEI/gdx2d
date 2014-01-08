package hevs.gdx2d.lib.interfaces;

import hevs.gdx2d.lib.GdxGraphics;

public interface GameInterface {

	/*
	 * Called when the application starts. Initializations and creation of new new objects
	 * must be done here.
	 */
	public abstract void onInit();
	
	/*
	 * Called when the application pauses (on Android).
	 */
	public abstract void onPause();
	
	/*
	 * Called when the application stops. Tidying up must be done here
	 */
	public abstract void onDispose();
	
	/*
	 * Called when the application starts. Initializations and creation of new new objects
	 * must be done here.
	 */
	public abstract void onResume();
	
	/*
	 * Called when the screen is refreshed, in sync with VSync
	 */
	public void onGraphicRender(GdxGraphics g);
	
	/**
	 * Called when the logic has to be updated. Might not be called
	 */
	public abstract void onGameLogicUpdate();
}