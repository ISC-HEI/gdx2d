package hevs.gdx2d.lib.interfaces;

/**
 * An interface for Android actions that should be triggered from libgdx side
 *
 * @author Pierre-Andre Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.1
 */
public interface AndroidResolver {

	public static int LENGTH_LONG = 0x01; // Toast.LENGTH_LONG
	public static int LENGTH_SHORT = 0x00; // Toast.LENGTH_SHORT

	/**
	 * Display an about dialog Activity.
	 */
	public void showAboutBox();

	/**
	 * Force to dismiss the about dialog. Must be called when the screen
	 * orientation change.
	 */
	public void dismissAboutBox();

	/**
	 * Show an Android Toast on the screen with default position on the screen
	 * (bottom center).
	 *
	 * @param text     The text of the Toast
	 * @param duration How long to display the message. Either {@link LENGTH_SHORT}
	 *                 or {@link LENGTH_LONG}
	 */
	public void showToast(CharSequence text, int duration);
}
