package hevs.gdx2d.components.physics;

import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * A physical polygon that does not move, see {@link AbstractPhysicsObject}
 * Note that all the dimensions are all in pixels
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsPolygon extends AbstractPhysicsObject {

	/**
	 * Returns the current polygon shape, the one which is simulated
	 * @return
	 */
	public Polygon getPolygon(){
		
		Fixture f = this.getBody().getFixtureList().get(0);
		assert(f.getType() == Type.Polygon);
									
		PolygonShape poly = (PolygonShape) f.getShape();
	
		assert(poly != null);
		
		Vector2[] vertices = new Vector2[poly.getVertexCount()];
		
		for(int i = 0; i < poly.getVertexCount(); i++){
			vertices[i] = Vector2.Zero;
			poly.getVertex(i, vertices[i]);
		}

		return new Polygon(vertices);		
	}
	
	/**
	 * @param name An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width The width of the physics box
	 * @param height The height of the physics box
	 */
	public PhysicsPolygon(String name, Vector2[] vertices, float density, float restitution, float friction, boolean dynamic) {
		super(name, Vector2.Zero, vertices, density, restitution, friction, dynamic);
	}

	/**
	 * @param name An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width The width of the physics box
	 * @param height The height of the physics box
	 */
	public PhysicsPolygon(String name, Vector2[] vertices, boolean dynamic) {
		super(name, Vector2.Zero, vertices, 1, 0.6f, 0.6f, dynamic);
	}
}
