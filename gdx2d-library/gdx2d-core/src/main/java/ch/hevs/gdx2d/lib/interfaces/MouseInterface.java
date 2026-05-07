package ch.hevs.gdx2d.lib.interfaces;

/**
 * An interface that applications implement to interact with the user using the mouse.
 * <p>
 * Coordinates origin is the lower left corner.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
public interface MouseInterface {

	/**
	 * Invoked when the mouse button is pressed (once).
	 *
	 * @param x      the screen X coordinate (origin is the lower left corner)
	 * @param y      the screen Y coordinate (origin is the lower left corner)
	 * @param button {@link com.badlogic.gdx.Input.Buttons#LEFT} or
	 *               {@link com.badlogic.gdx.Input.Buttons#RIGHT} button clicked
	 */
	void onClick(int x, int y, int button);

	/**
	 * Invoked when the mouse is moved while a button is down.
	 *
	 * @param x the screen X coordinate (origin is the lower left corner)
	 * @param y the screen Y coordinate (origin is the lower left corner)
	 */
	void onDrag(int x, int y);

	/**
	 * Invoked when the mouse button is released.
	 *
	 * @param x      the screen X coordinate (origin is the lower left corner)
	 * @param y      the screen Y coordinate (origin is the lower left corner)
	 * @param button {@link com.badlogic.gdx.Input.Buttons#LEFT} or
	 *               {@link com.badlogic.gdx.Input.Buttons#RIGHT} button clicked
	 */
	void onRelease(int x, int y, int button);

	/**
	 * Invoked when the mouse scroll (wheel) has been moved.
	 * <p>
	 * The amount can be positive or negative depending on the direction the
	 * wheel was scrolled.
	 *
	 * @param amount the scroll amount
	 */
	void onScroll(int amount);
}
