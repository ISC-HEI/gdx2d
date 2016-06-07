package ch.hevs.gdx2d.lib.utils;

import ch.hevs.gdx2d.lib.Version;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Simple logging facility using underlying {@code libgdx}.
 * <p/>
 * Use {@link #setLogLevel(int)} to set the logger more or less verbose.
 * <ul>
 * <li>For snapshot versions, debug, info and error messages are displayed by
 * default.</li>
 * <li>For release versions, only info and error messages are displayed by
 * default.</li>
 * </ul>
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.3
 */
public class Logger {

	/**
	 * Mute all logging messages.
	 */
	public static final int NONE = Application.LOG_NONE;

	/**
	 * Logs all messages (default for snapshot versions).
	 */
	public static final int DEBUG = Application.LOG_DEBUG;

	/**
	 * Log error and normal messages (default for release versions).
	 */
	public static final int INFO = Application.LOG_INFO;

	/**
	 * Log only error messages.
	 */
	public static final int ERROR = Application.LOG_ERROR;

	private static final String TAG = "gdx2d";

	static {
		if (Version.isSnapshot)
			Gdx.app.setLogLevel(Logger.DEBUG);
		else
			Gdx.app.setLogLevel(Logger.INFO);
	}

	private Logger() {
	}

	/**
	 * Log a message.
	 *
	 * @param msg logging message
	 */
	public static void log(String msg) {
		log(TAG, msg);
	}

	/**
	 * Log a message.
	 *
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void log(String tag, String msg) {
		Gdx.app.log(String.format("[%s]", tag), msg); // Print log message
	}

	/**
	 * Log a message.
	 *
	 * @param tag logging tag
	 * @param format logging message format
	 * @param args logging message args
	 */
	public static void log(String tag, String format, Object... args) {
		log(tag, String.format(format, args));
	}

	/**
	 * Log an error.
	 *
	 * @param msg logging message
	 */
	public static void error(String msg) {
		error(TAG, msg);
	}

	/**
	 * Log an error.
	 *
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void error(String tag, String msg) {
		Gdx.app.error(String.format("[%s]", tag), "error - " + msg); // Print error
																																	// in red
	}

	/**
	 * Log an error.
	 *
	 * @param tag logging tag
	 * @param format logging message format
	 * @param args logging message args
	 */
	public static void error(String tag, String format, Object... args) {
		error(tag, String.format(format, args));
	}

	/**
	 * Log a debug message.
	 *
	 * @param msg logging message
	 */
	public static void dbg(String msg) {
		dbg(TAG, msg);
	}

	/**
	 * Log a debug message.
	 *
	 * @param tag logging tag
	 * @param msg logging message
	 */
	public static void dbg(String tag, String msg) {
		Gdx.app.log(String.format("[%s]", tag), msg); // Print debug message
	}

	/**
	 * Log a debug message.
	 *
	 * @param tag logging tag
	 * @param format logging message format
	 * @param args logging message args
	 */
	public static void dbg(String tag, String format, Object... args) {
		dbg(tag, String.format(format, args));
	}

	/**
	 * Set the logger more or less verbose.
	 *
	 * @param logLevel the log level
	 */
	public static void setLogLevel(int logLevel) {
		Gdx.app.setLogLevel(logLevel);
	}
}
