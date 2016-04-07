package ch.hevs.gdx2d.components.physics.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Declares useful physics simulation constants and conversion functions for the {@code Box2D} engine.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class PhysicsConstants {
	/**
	 * Default simulation step (60 FPS).
	 */
	public static final float STEP_SIZE = 1 / 60f;

	/**
	 * Default gravity value.
	 */
	public static final float GRAVITY_VALUE = -10;

    /* Unit conversions */

	/**
	 * Multiply this ratio to convert meters to pixels.
	 *
	 * @see #coordMetersToPixels(Vector2)
	 */
	public static float METERS_TO_PIXELS = 75;

	/**
	 * @see #coordMetersToPixels(Vector2)
	 * @see #METERS_TO_PIXELS
	 */
	public static float M2P = METERS_TO_PIXELS;

	/**
	 * Multiply this ratio to convert pixels to meters.
	 *
	 * @see #coordPixelsToMeters(Vector2)
	 */
	public static float PIXEL_TO_METERS = 1 / METERS_TO_PIXELS;

	/**
	 * @see #coordPixelsToMeters(Vector2)
	 * @see #PIXEL_TO_METERS
	 */
	public static float P2M = PIXEL_TO_METERS;

	public static final int VELOCITY_IT = 6;
	public static final int POSITION_IT = 5;
	public static final int SPEEDUP = 2;

    /* Math constants */

	/**
	 * Multiply this ratio to convert radians to degrees.
	 */
	public static final float RAD_TO_DEG = (float) (180.0 / Math.PI);

	/**
	 * Multiply this ratio to convert degrees to radians.
	 */
	public static final float DEG_TO_RAD = (float) (Math.PI / 180.0);

	/**
	 * Convert a {@link Vector2} with pixels coordinates to meter coordinates
	 * (using the {@link PhysicsConstants#METERS_TO_PIXELS} factor) to use it in
	 * Box2D.
	 *
	 * @param i the input vector to convert. Will not be modified.
	 * @return the new converted vector
	 * @see PhysicsConstants#PIXEL_TO_METERS
	 */
	public static Vector2 coordPixelsToMeters(Vector2 i) {
		return new Vector2(i).scl(PIXEL_TO_METERS);
	}

	/**
	 * Convert a {@link Vector2} with meter coordinates to pixels coordinates.
	 *
	 * @param i the input vector to convert. Will not be modified.
	 * @return the new converted vector
	 * @see PhysicsConstants#METERS_TO_PIXELS
	 */
	public static Vector2 coordMetersToPixels(Vector2 i) {
		return new Vector2(i).scl(METERS_TO_PIXELS);
	}
}
