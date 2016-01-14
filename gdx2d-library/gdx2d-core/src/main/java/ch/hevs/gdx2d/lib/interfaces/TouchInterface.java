package ch.hevs.gdx2d.lib.interfaces;

/**
 * An interface that applications implement to interact with the user using mouse or touch.
 * <p/>
 * Coordinates origin is the lower left corner.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public interface TouchInterface {

	/**
	 * Invoked when the pointer (mouse or touch) is pressed (once).
	 * <p/>
	 * Note: on Android, the button parameter will always be {@link com.badlogic.gdx.Input.Buttons#LEFT}.
	 *
	 * @param x      the screen X coordinate (origin is the lower left corner)
	 * @param y      the screen Y coordinate (origin is the lower left corner)
	 * @param button {@link com.badlogic.gdx.Input.Buttons#LEFT} or
	 *               {@link com.badlogic.gdx.Input.Buttons#RIGHT} button clicked
	 */
	void onClick(int x, int y, int button);

	/**
	 * Invoked when the pointer (mouse or touch) is moved and down.
	 *
	 * @param x the screen X coordinate (origin is the lower left corner)
	 * @param y the screen Y coordinate (origin is the lower left corner)
	 */
	void onDrag(int x, int y);

	/**
	 * Invoked when the pointer (mouse or touch) is released.
	 * <p/>
	 * Note: on Android, the button parameter will always be {@link com.badlogic.gdx.Input.Buttons#LEFT}.
	 *
	 * @param x      the screen X coordinate (origin is the lower left corner)
	 * @param y      the screen Y coordinate (origin is the lower left corner)
	 * @param button {@link com.badlogic.gdx.Input.Buttons#LEFT} or
	 *               {@link com.badlogic.gdx.Input.Buttons#RIGHT} button clicked
	 */
	void onRelease(int x, int y, int button);

	/**
	 * Invoked when the mouse scroll (wheel) has been moved.
	 * <p/>
	 * The amount can be positive or negative depending on the direction the wheel was scrolled.
	 * 
	 * @param amount the scroll amount
	 */
	void onScroll(int amount);
}