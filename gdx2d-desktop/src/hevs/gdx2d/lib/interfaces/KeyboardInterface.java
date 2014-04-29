package hevs.gdx2d.lib.interfaces;

/**
 * An interface to interact with the application using a keyboard 
 * @author Pierre-André Mudry (mui)
 */
public interface KeyboardInterface {
	/**
	 * Invoked when a key is typed. Use Input.Keys.xy to read the key code
	 */
	public abstract void onKeyDown(int keycode);

	/**
	 * Invoked when a key is released. Use Input.Keys.xy to read the key code
	 */
	public abstract void onKeyUp(int keycode);

}