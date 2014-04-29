package hevs.gdx2d.components.physics.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Declares useful physics simulation constants and conversion functions for the
 * box2D engine.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class PhysicsConstants {
	public static final float STEP_SIZE = 1 / 60f;
	public static final int VELOCITY_IT = 6;
	public static final int POSITION_IT = 5;
	public static final float GRAVITY_VALUE = -10;

	// For conversions
	/** Multiply this ratio to convert meters to pixels */
	public static float METERS_TO_PIXELS = 75;
	/** Multiply this ratio to convert pixels to meters */
	public static float PIXEL_TO_METERS = 1 / METERS_TO_PIXELS;
	public static final int SPEEDUP = 2;

	// Math constants
	public static final float RAD_TO_DEG = (float) (180.0 / Math.PI);
	public static final float DEG_TO_RAD = (float) (Math.PI / 180.0);

	/**
	 * Convert a {@link Vector2} with pixels coordinates to meter coordinates
	 * (using the {@link PhysicsConstants#METERS_TO_PIXELS} factor) to use it in
	 * Box2D.
	 * 
	 * @see PhysicsConstants#PIXEL_TO_METERS
	 * @param i
	 *            the input vector to convert. Will not be modified.
	 * @return the new converted vector
	 */
	public static Vector2 coordPixelsToMeters(Vector2 i) {
		return new Vector2(i).scl(PIXEL_TO_METERS);
	}

	/**
	 * Convert a {@link Vector2} with meter coordinates to pixels coordinates.
	 * 
	 * @see PhysicsConstants#METERS_TO_PIXELS
	 * @param i
	 *            the input vector to convert. Will not be modified.
	 * @return the new converted vector
	 */
	public static Vector2 coordMetersToPixels(Vector2 i) {
		return new Vector2(i).scl(METERS_TO_PIXELS);
	}
}
