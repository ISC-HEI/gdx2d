package hevs.gdx2d.lib.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Simple logging facility using underlying libgdx.
 * @author Pierre-André Mudry (mui)
 * @version 1.01
 */
public class Logger {
	
	static{
		Gdx.app.setLogLevel(Application.LOG_INFO);
	}
	
	public static final int NONE = Application.LOG_NONE;
	public static final int ERROR = Application.LOG_ERROR;
	public static final int INFO = Application.LOG_INFO;
	public static final int DEBUG = Application.LOG_DEBUG;
	
	public static void log(String msg){
		Gdx.app.log("[GDX2DLib]", msg);
	}

	public static void setLogLevel(int logLevel){
		Gdx.app.setLogLevel(logLevel);
	}
}
