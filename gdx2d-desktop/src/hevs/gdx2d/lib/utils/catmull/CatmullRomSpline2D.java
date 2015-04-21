package hevs.gdx2d.lib.utils.catmull;

import com.badlogic.gdx.math.Vector2;

/**
 * 2D CatmullRom spline interpolation classes
 * Adapted from http://hawkesy.blogspot.ch/2010/05/catmull-rom-spline-curve-implementation.html
 */
public class CatmullRomSpline2D {
	private CatmullRomSpline splineXVals, splineYVals;

	public CatmullRomSpline2D(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3) {
		assert p0 != null : "p0 cannot be null";
		assert p1 != null : "p1 cannot be null";
		assert p2 != null : "p2 cannot be null";
		assert p3 != null : "p3 cannot be null";

		splineXVals = new CatmullRomSpline(p0.x, p1.x, p2.x, p3.x);
		splineYVals = new CatmullRomSpline(p0.y, p1.y, p2.y, p3.y);
	}

	public Vector2 q(float t) {
		return new Vector2(splineXVals.q(t), splineYVals.q(t));
	}
}