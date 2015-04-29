package hevs.gdx2d.lib.interfaces;

import hevs.gdx2d.lib.GdxGraphics;

/**
 * Contains the methods to handle the application lifecycle.
 * This lifecycle is independent from the hardware it runs on.
 * <p></p>
 * Methods {@link GameInterface#onPause()} and {@link GameInterface#onResume()} are only
 * invoked when the application runs on Android.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public interface GameInterface {

	/**
	 * Called when the application starts.<br>
	 * Initializations and creation of new new objects must be done here.
	 */
	void onInit();

	/**
	 * Called when the application stops.
	 * Tidying up must be done here.
	 */
	void onDispose();

	/**
	 * Called when the screen is refreshed, in sync with VSync.
	 */
	void onGraphicRender(GdxGraphics g);

	/**
	 * Called when the logic has to be updated. Might not be called.
	 */
	void onGameLogicUpdate();

    /* Android only */

	/**
	 * Invoked when the application is paused. <b>Called only when running on Android.</b>
	 */
	void onPause();

	/**
	 * Called when the application is restarted. <b>Called only when running on Android.</b><br>
	 * Initializations and creation of new new objects must be done here.
	 */
	void onResume();
}