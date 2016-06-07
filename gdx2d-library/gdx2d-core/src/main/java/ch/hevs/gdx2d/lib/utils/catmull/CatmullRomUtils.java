package ch.hevs.gdx2d.lib.utils.catmull;

import com.badlogic.gdx.math.Vector2;

/**
 * CatmullRom spline interpolation classes
 * Adapted from http://hawkesy.blogspot.ch/2010/05/catmull-rom-spline-curve-implementation.html
 */
public class CatmullRomUtils {
	private CatmullRomUtils() {
	}

	/**
	 * Creates catmull spline curves between the points array.
	 *
	 * @param points       The current 2D points array
	 * @param subdivisions The number of subdivisions to add between each of the points.
	 * @return A larger array with the points subdivided.
	 */
	public static Vector2[] subdividePoints(Vector2[] points, int subdivisions) {
		assert points != null;
		assert points.length >= 3;

		Vector2[] subdividedPoints = new Vector2[((points.length - 1) * subdivisions) + 1];

		float increments = 1f / (float) subdivisions;

		for (int i = 0; i < points.length - 1; i++) {
			Vector2 p0 = i == 0 ? points[i] : points[i - 1];
			Vector2 p1 = points[i];
			Vector2 p2 = points[i + 1];
			Vector2 p3 = (i + 2 == points.length) ? points[i + 1] : points[i + 2];

			CatmullRomSpline2D crs = new CatmullRomSpline2D(p0, p1, p2, p3);

			for (int j = 0; j <= subdivisions; j++) {
				subdividedPoints[(i * subdivisions) + j] = crs.q(j * increments);
			}
		}

		return subdividedPoints;
	}


	public static void main(String[] args) {
		Vector2[] pointArray = new Vector2[4];

		pointArray[0] = new Vector2(1f, 1f);
		pointArray[1] = new Vector2(2f, 2f);
		pointArray[2] = new Vector2(3f, 2f);
		pointArray[3] = new Vector2(4f, 1f);

		Vector2[] subdividedPoints = CatmullRomUtils.subdividePoints(pointArray, 4);

		for (Vector2 point : subdividedPoints) {
			System.out.println("" + point);
		}
	}
}