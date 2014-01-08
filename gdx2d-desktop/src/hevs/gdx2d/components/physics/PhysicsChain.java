package hevs.gdx2d.components.physics;

import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.demos.physics.DemoChainPhysics;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.catmull.CatmullRomUtils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A physics chain (!) that connects two points with a given number of rigid segments
 * that are generated randomly.
 *
 * The segments can either be generated randomly (with edges) or with a smooth
 * Catmull-Rom interpolated spline. See {@link DemoChainPhysics} demo
 * 
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
public class PhysicsChain implements DrawableObject{
	Body b;
	ArrayList<Vector2> vertices;
	ChainShape s = new ChainShape();

	private Vector2 start, stop;
	boolean transparentRendering = false;
	
	final float M2P = PhysicsConstants.METERS_TO_PIXELS; 
	
	public enum chain_type {RANDOM, CATMUL};
	
	public PhysicsChain(Vector2 start, Vector2 stop, int nPoints, chain_type t) {
		this.start = start;
		this.stop = stop;
		
		if(t == chain_type.RANDOM)
			random_chain(nPoints);
		else
			catmull_chain(nPoints);
	}
	
	/**
	 * Generate other points in the middle
	 * @param nPoints
	 */
	public void random_chain(int nPoints){
		World w = PhysicsWorld.getInstance();
		
		if(b != null)
			w.destroyBody(b);
		
		BodyDef bd = new BodyDef();
		b = w.createBody(bd);
		
		ChainShape s = new ChainShape();
		
		vertices = random_vertices(nPoints, 0.3f);
		s.createChain(vertices.toArray(new Vector2[0]));		
		b.createFixture(s, 1);
		s.dispose();
	}
	
	public void catmull_chain(int nPoints){
		World w = PhysicsWorld.getInstance();
		
		if(b != null)
			w.destroyBody(b);
		
		BodyDef bd = new BodyDef();
		b = w.createBody(bd);
		
		ChainShape s = new ChainShape();
		vertices = random_vertices(nPoints, 0.8f);		
		
		// Interpolates new points with a Catmull-Rom spline, using 9 subdivisions per segment
		Vector2[] spline = CatmullRomUtils.subdividePoints(vertices.toArray(new Vector2[0]), 9);
		
		s.createChain(spline);		
		b.createFixture(s, 1);
		
		// Replace the existing vertices by the spline generated
		vertices.clear();
		
		for(int i = 0; i < spline.length; i++){
			vertices.add(spline[i]);
		}
		
		s.dispose();		
	}
	
	private ArrayList<Vector2> random_vertices(int nPoints, float random_height){
		ArrayList<Vector2> vertices = new ArrayList<Vector2>();
		
		float width =  (stop.x - start.x) / (nPoints-1);
		float height = (stop.y - start.y) / (nPoints-1);		
		
		for(int i = 0; i < nPoints; i++){
			float h = (start.y + height * i);
			h += (float)(random_height * (Math.random() * h));
			Vector2 p = new Vector2(start.x + width * i, h);
			vertices.add(PhysicsConstants.coordPixelsToMeters(p));	
		}
		
		return vertices;
	}

	@Override
	public void draw(GdxGraphics g) {
		
		Vector2 first = vertices.get(0);
		
		g.setColor(Color.BLUE);
		
		/**
		 * Draws the segments in between
		 */
		g.setPenWidth(3);
		for (int j = 1; j < vertices.size(); j++) {
			Vector2 i = vertices.get(j);
			g.drawLine(first.x * M2P , first.y * M2P, i.x * M2P, i.y * M2P);
			first = i; 
		}
		g.setPenWidth(1);		
	}

}
