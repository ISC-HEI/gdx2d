package ch.hevs.gdx2d.lib.utils;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Various utils for checking things.
 *
 * @author Nils Chatton (chn)
 * @author Christopher Métrailler (mei)
 * @version 1.2
 */
public class Utils {

	private Utils() {
		// Not allowed
	}

	/**
	 * Check if gdx is correctly loaded.
	 *
	 * @param msg The message to log if not loaded
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public static void assertGdxLoaded(String msg) {
    // Check if the OpenGL context is available or not
		if (Gdx.graphics == null || Gdx.graphics.getGL20() == null) {
			throw new GdxRuntimeException(msg);
		}
	}

	/**
	 * Checks that gdx is correctly loaded. If not, print an error message.
	 * <p/>
	 * The following classes cannot be created if Gdx is not loaded:
	 * <ul>
	 *     <li>{@link ch.hevs.gdx2d.components.bitmaps.BitmapImage}</li>
	 *     <li>{@link ch.hevs.gdx2d.components.audio.SoundSample}</li>
	 *     <li>{@link ch.hevs.gdx2d.components.audio.MusicPlayer}</li>
	 *     <li>{@link ch.hevs.gdx2d.components.bitmaps.Spritesheet}</li>
	 * </ul>
	 *
	 * @param c the class which cannot be created before Gdx is loaded
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public static void assertGdxLoaded(Class c) {
		if (Gdx.graphics == null || Gdx.graphics.getGL20() == null) {
			final String error = String.format("Gdx must be loaded to create a '%s'. It can only be created in the onInit "
			+ "method of a class extending PortableApplication (or must be called from within this method).",
			c.getSimpleName());
			throw new GdxRuntimeException(error);
		}
	}

	/**
	 * Checks that a method is called only in a given class
	 *
	 * @param className The name of the class the method belongs to
	 * @param method    The method name that should be contained
	 */
	public static void callCheck(String className, String method) {
		boolean callFromWrongLocation = true;

		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

		for (StackTraceElement elem : callStack) {
			if (elem.getClassName().equals(className)
					&& elem.getMethodName().equals(method)) {
				callFromWrongLocation = false;
				break;
			}
		}

		if (callFromWrongLocation) {
      		throw new GdxRuntimeException(
            		"This new instance shall be created in a call subsequent from the onInit() method "
              		+ "from the class implementing PortableApplication");
		}
	}
}
