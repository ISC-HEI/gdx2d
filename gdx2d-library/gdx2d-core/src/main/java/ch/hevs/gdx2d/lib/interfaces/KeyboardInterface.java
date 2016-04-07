package ch.hevs.gdx2d.lib.interfaces;

/**
 * An interface to interact with the application using a keyboard.
 * <p/>
 * Use {@link com.badlogic.gdx.Input.Keys} to read the key code.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public interface KeyboardInterface {

	/**
	 * Invoked when a key is typed.
	 * <p/>
	 * Use {@link com.badlogic.gdx.Input.Keys} to read the key code.
	 *
	 * @param keycode the code key, see {@link com.badlogic.gdx.Input.Keys}
	 */
	void onKeyDown(int keycode);

	/**
	 * Invoked when a key is released.
	 * <p/>
	 * Use {@link com.badlogic.gdx.Input.Keys} to read the key code.
	 *
	 * @param keycode the code key, see {@link com.badlogic.gdx.Input.Keys}
	 */
	void onKeyUp(int keycode);
}