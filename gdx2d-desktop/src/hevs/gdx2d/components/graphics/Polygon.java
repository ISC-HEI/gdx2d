package hevs.gdx2d.components.graphics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

/**
 * A polygon class for rendering stuff
 * TODO: implement {@link com.badlogic.gdx.math.Polygon} in order to be able to scale, translate and rotate the polygon
 * @author Nils Chatton (chn) 
 */
public class Polygon {

	private float[] vertices;
	private float[] triangulatedVertices = null;
	public LinkedList<Vector2> vectorList;
	private List<Vector2> earClippedVertices;
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
	
	public Vector2 getVertex(int i){
		assert(i < vertices.length /2);
		int t = i * 2;
		return new Vector2(vertices[t], vertices[t+1]);
	}
	
	public float[] getVertices() {
		return vertices; 
	}

	public float[] getEarClippedVertices() {
		if(triangulatedVertices == null){
			int j = 0;
			EarClippingTriangulator ect;
			vectorList = new LinkedList<Vector2>(Arrays.asList(gdxpoints));
			ect = new EarClippingTriangulator();
			earClippedVertices = ect.computeTriangles(vectorList);
			triangulatedVertices = new float[earClippedVertices.size() * 2];
			
			for (Vector2 v : earClippedVertices) {
				triangulatedVertices[j++] = v.x;
				triangulatedVertices[j++] = v.y;
			}			
		}
		
		return triangulatedVertices;
	}

	/**
	 * Check if a point is in the polygon. Use the math Intersector of Gdx.
	 * 
	 * @param p
	 *            point coordinates
	 * @return true if the point is in the polygon
	 */
	public boolean contains(Vector2 p) {
		boolean collides = Intersector.isPointInPolygon(vectorList,	new Vector2(p.x, p.y));
		return collides;
	}
	
	/**
	 * Converts a vector2 to a float array containing x and y components
	 * of the original vector, each one after the other
	 * @param v
	 * @return
	 */
	public static float[] vec2floatArray(Vector2[] v){
		float[] r = new float[2*v.length];
		
		int i = 0;
		for (Vector2 f : v) {
			r[i] = f.x;
			r[i+1] = f.y;
			i += 2;
		}
		
		return r;
	}
	
	/**
	 * Converts a float array to a Vector2[] array containing x and y components
	 * of the original vector, each one after the other
	 * @param v
	 * @return
	 */
	public static Vector2[] float2vec2(float[] f){
		assert (f.length % 2 == 0) : "The number of coordinates in a polygon must be even";
		Vector2[] v = new Vector2[f.length / 2];
		
		for(int i = 0; i < f.length; i+=2){
			Vector2 n = new Vector2(f[i], f[i+1]);
			v[i/2] = n;
		}
		
		return v;
	}
}