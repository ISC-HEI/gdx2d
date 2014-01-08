package hevs.gdx2d.components.graphics;

import hevs.gdx2d.components.geometry.Vector2D;
import hevs.gdx2d.lib.utils.Utils;

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
	private float[] triangulatedVertices;
	public LinkedList<Vector2> vectorList;
	private EarClippingTriangulator ect;
	private List<Vector2> earClippedVertices;
	private Vector2[] gdxpoints;	
	
	public Polygon(Vector2D[] points) {	
		int j = 0;
		Utils.callCheckExcludeGraphicRender();
		
		gdxpoints = new Vector2[points.length];

		for (int i = 0; i < points.length; i++) {
			gdxpoints[i] = new Vector2(points[i].x, points[i].y);
		}

		vectorList = new LinkedList<Vector2>(Arrays.asList(gdxpoints));

		vertices = new float[points.length * 2];
		for (int i = 0; i < points.length; i++) {
			vertices[2 * i] = points[i].x;
			vertices[(2 * i) + 1] = points[i].y;
		}

		ect = new EarClippingTriangulator();
		earClippedVertices = ect.computeTriangles(vectorList);
		triangulatedVertices = new float[earClippedVertices.size() * 2];
		
		for (Vector2 v : earClippedVertices) {
			triangulatedVertices[j++] = v.x;
			triangulatedVertices[j++] = v.y;
		}			
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public float[] getEarClippedVertices() {
		return triangulatedVertices;
	}

	/**
	 * Check if a point is in the polygon. Use the math Intersector of Gdx.
	 * 
	 * @param p
	 *            point coordinates
	 * @return true if the point is in the polygon
	 */
	public boolean contains(Vector2D p) {
		boolean collides = Intersector.isPointInPolygon(vectorList,	new Vector2(p.x, p.y));
		return collides;
	}
}