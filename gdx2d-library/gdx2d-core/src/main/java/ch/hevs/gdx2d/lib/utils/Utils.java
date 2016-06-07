package ch.hevs.gdx2d.lib.utils;

import com.badlogic.gdx.Gdx;

/**
 * Various utils for checking things
 *
 * @author Nils Chatton (chn)
 * @version 1.0
 */
public class Utils {

	private Utils() {
	}

	/**
	 * Checks that gdx is correctly loaded.
	 * <p/>
	 * BitmapImage, DebugRenderer, MusicPlayer etc. cannot be created until Gdx
	 * is not loaded.
	 *
	 * @param msg The message to log if not loaded
	 */
	public static void assertGdxLoaded(String msg) {
		if (Gdx.graphics.getGL20() == null) {
			Logger.error(msg);
			Gdx.app.exit();
			throw new UnsupportedOperationException(msg);
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
			try {
				throw new Exception(
						"This new instance shall be created in a call subsequent from the onInit() method "
								+ "from the class implementing PortableApplication");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public static void callCheckExcludeGraphicRender() {
		String className = "hevs.gdx2d.components.Game";
		String method = "render";

		boolean callFromWrongLocation = false;

		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

		for (StackTraceElement elem : callStack) {
			if (elem.getClassName().equals(className)
					&& elem.getMethodName().equals(method)) {
				callFromWrongLocation = true;
				break;
			}
		}

		if (callFromWrongLocation) {
			try {
				throw new Exception(
						"For performance issues, this new instance shall not be created in "
								+ "the onGraphicRender() method from the class implementing PortableApplication.");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}
}
