package hevs.gdx2d.lib.interfaces;

import com.badlogic.gdx.Input;

/**
 * An interface to interact with the application using a keyboard.<br>
 * Use {@link Input.Keys} to read the key code.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public interface KeyboardInterface {

	/**
	 * Invoked when a key is typed.
	 * Use {@link Input.Keys} to read the key code.
	 *
	 * @param keycode the code key, see {@link Input.Keys}
	 */
	void onKeyDown(int keycode);

	/**
	 * Invoked when a key is released.
	 * Use {@link Input.Keys} to read the key code.
	 *
	 * @param keycode the code key, see {@link Input.Keys}
	 */
	void onKeyUp(int keycode);
}