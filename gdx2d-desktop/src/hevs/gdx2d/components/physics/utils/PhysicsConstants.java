package hevs.gdx2d.components.physics.utils;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Declares useful physics simulation constants
 * for the box2d engine.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.01
 */
public class PhysicsConstants {
	public static final float STEP_SIZE = 1 / 60f;
	public static final int VELOCITY_IT = 6;
	public static final int POSITION_IT = 5;	
	public static final float GRAVITY_VALUE = -10;
	
	// For conversions
	public static float METERS_TO_PIXELS = 75;
	public static float PIXEL_TO_METERS  = 1/METERS_TO_PIXELS;
	public static final int SPEEDUP = 2;
	
	// Math constants
	public static final float RAD_TO_DEG = (float)(180.0 / Math.PI);
	public static final float DEG_TO_RAD = (float)(Math.PI / 180.0);
	
	public static Vector2 coordPixelsToMeters(Vector2 i){
		return i.scl(PIXEL_TO_METERS);
	}
	
	public static Vector2 coordMetersToPixels(Vector2 i){
		return i.scl(METERS_TO_PIXELS);
	}
}
