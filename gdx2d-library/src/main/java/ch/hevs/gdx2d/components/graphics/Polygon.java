package ch.hevs.gdx2d.components.graphics;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * A polygon class for rendering stuff.
 *
 * @author Nils Chatton (chn)
 */
public class Polygon {

	// TODO: implement {@link com.badlogic.gdx.math.Polygon} in order to be able to scale, translate and rotate the polygon
	private float[] vertices;
	private float[] triangulatedVertices = null;
	public Array<Vector2> vectorList;
	private short[] earClippedVertices;
	private Vector2[] gdxpoints;

	public Polygon(Vector2[] points) {
		gdxpoints = new Vector2[points.length];

		for (int i = 0; i < points.length; i++) {
			gdxpoints[i] = new Vector2(points[i].x, points[i].y);
		}

		vertices = new float[points.length * 2];
		for (int i = 0; i < points.length; i++) {
			vertices[2 * i] = points[i].x;
			vertices[(2 * i) + 1] = points[i].y;
		}
	}

	public Vector2 getVertex(int i) {
		assert (i < vertices.length / 2);
		int t = i * 2;
		return new Vector2(vertices[t], vertices[t + 1]);
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getEarClippedVertices() {
		if (triangulatedVertices == null) {
			EarClippingTriangulator ect;
			float[] vectorArray = new float[gdxpoints.length * 2];
			for (int i = 0; i < gdxpoints.length; i++) {
				vectorArray[2 * i + 0] = gdxpoints[i].x;
				vectorArray[2 * i + 1] = gdxpoints[i].y;
			}

			ect = new EarClippingTriangulator();
			earClippedVertices = ect.computeTriangles(vectorArray).toArray();
			triangulatedVertices = new float[earClippedVertices.length * 2];
			for (int i = 0; i < earClippedVertices.length; i++) {
				triangulatedVertices[2 * i + 0] = gdxpoints[earClippedVertices[i]].x;
				triangulatedVertices[2 * i + 1] = gdxpoints[earClippedVertices[i]].y;
			}
		}

		return triangulatedVertices;
	}

	/**
	 * Check if a point is in the polygon. Use the math Intersector of Gdx.
	 *
	 * @param p point coordinates
	 * @return true if the point is in the polygon
	 */
	public boolean contains(Vector2 p) {
		return Intersector.isPointInPolygon(vectorList, new Vector2(p.x, p.y));
	}

	/**
	 * Converts a {@link Vector2} to an array of {@link Float} values.
	 * The output array contains the {@code x} and the {@code y} values of the original {@link Vector2},
	 * each one after the other.
	 *
	 * @see Polygon#float2vec2(float[]) for the opposite operation
	 * @param v the array of vector to convert
	 * @return x and y values of each vectors
	 */
	public static float[] vec2floatArray(Vector2[] v) {
		float[] r = new float[2 * v.length];

		int i = 0;
		for (Vector2 f : v) {
			r[i] = f.x;
			r[i + 1] = f.y;
			i += 2;
		}

		return r;
	}

	/**
	 * Converts a float array to an array of {@link Vector2}.
	 * The input array must contains the {@code x} value first, then the {@code y} value.
	 *
	 * @see Polygon#float2vec2(float[]) for the opposite operation
	 * @param f coordinates of each vectors (x and y values)
	 * @return the converted array of vector
	 */
	public static Vector2[] float2vec2(float[] f) {
		assert (f.length % 2 == 0) : "The number of coordinates in a polygon must be even";
		Vector2[] v = new Vector2[f.length / 2];

		for (int i = 0; i < f.length; i += 2) {
			Vector2 n = new Vector2(f[i], f[i + 1]);
			v[i / 2] = n;
		}

		return v;
	}
}