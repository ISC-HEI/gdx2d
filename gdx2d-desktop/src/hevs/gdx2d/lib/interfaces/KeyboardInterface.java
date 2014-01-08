package hevs.gdx2d.lib.interfaces;

public interface KeyboardInterface {

	/*
	 * Invoked when a key is typed. Use Input.Keys.xy to read the keycode
	 */
	public abstract void onKeyDown(int keycode);

	/*
	 * Invoked when a key is released. Use Input.Keys.xy to read the keycode
	 */
	public abstract void onKeyUp(int keycode);

}