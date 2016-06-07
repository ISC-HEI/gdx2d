package ch.hevs.gdx2d.lib.utils;

import ch.hevs.gdx2d.lib.Version;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Simple logging facility using underlying {@code libgdx}.
 * <p/>
 * Be default only log error and normal messages (see #INFO).
 * Use {@link #setLogLevel(int)} to set the logger more or less verbose.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.2
 */
public class Logger {

	/** Mute all logging. */
	public static final int NONE = Application.LOG_NONE;

	/** Logs all messages. */
	public static final int DEBUG = Application.LOG_DEBUG;

	/** Log only error messages. */
	public static final int ERROR = Application.LOG_ERROR;

	/** Log error and normal messages. */
	public static final int INFO = Application.LOG_INFO;

	private static final String TAG = "gdx2d";

	static {
		if(Version.isSnapshot)
			Gdx.app.setLogLevel(Logger.DEBUG);
		else
			Gdx.app.setLogLevel(Logger.INFO);
	}

	private Logger() {
	}

	/**
	 * Log a message.
	 * @param msg logging message
	 */
	public static void log(String msg) {
		log(TAG, msg);
	}

	/**
	 * Log a message.
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void log(String tag, String msg) {
		Gdx.app.log(String.format("[%s]", tag), msg); // Print log message
	}

	/**
	 * Log an error.
	 * @param msg logging message
	 */
	public static void error(String msg) {
		error(TAG, msg);
	}

	/**
	 * Log an error.
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void error(String tag, String msg) {
		Gdx.app.error(String.format("[%s]", tag), "error - " + msg); // Print error in red
	}

	/**
	 * Log a debug message.
	 * @param msg logging message
	 */
	public static void dbg(String msg) {
		dbg(TAG, msg);
	}

	/**
	 * Log a debug message.
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void dbg(String tag, String msg) {
		Gdx.app.log(String.format("[%s]", tag), msg); // Print debug message
	}

	/**
	 * Set the logger more or less verbose.
	 * @param logLevel the log level
	 */
	public static void setLogLevel(int logLevel) {
		Gdx.app.setLogLevel(logLevel);
	}
}
