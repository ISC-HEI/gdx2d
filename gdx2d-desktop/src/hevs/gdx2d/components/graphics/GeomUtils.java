package hevs.gdx2d.components.graphics;

import com.badlogic.gdx.math.Vector2;

/**
 * Various geometry utils for polygons and stuff
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
abstract public class GeomUtils {
	public static void translate(Vector2[] v, Vector2 t) {
		for (Vector2 x : v) {
			x.add(t);
		}
	}

	public static void rotate(Vector2[] v, float a) {
		for (Vector2 x : v) {
			x.rotate(a);
		}
	}

	public static void scale(Vector2[] v, float s) {
		for (Vector2 x : v) {
			x.scl(s);
		}
	}
}
