package hevs.gdx2d.lib.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Simple logging facility using underlying libgdx.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
public class Logger {

	public static final int NONE = Application.LOG_NONE;
	public static final int ERROR = Application.LOG_ERROR;
	public static final int INFO = Application.LOG_INFO;
	public static final int DEBUG = Application.LOG_DEBUG;
	private static final String TAG = "GDX2DLib";

	static {
		Gdx.app.setLogLevel(Logger.INFO);
	}

	public static void log(String msg) {
		log(TAG, msg);
	}

	public static void log(String tag, String msg) {
		Gdx.app.log(String.format("[%s]", tag), msg); // Print debug message
	}

	public static void error(String msg) {
		error(TAG, msg);
	}

	public static void error(String tag, String msg) {
		Gdx.app.error(String.format("[%s]", tag), "error - " + msg); // Print error in red
	}

	public static void setLogLevel(int logLevel) {
		Gdx.app.setLogLevel(logLevel);
	}
}
