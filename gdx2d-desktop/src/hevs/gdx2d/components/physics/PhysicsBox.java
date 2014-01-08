package hevs.gdx2d.components.physics;

import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * A physical shape which collides as a box, see {@link AbstractPhysicsObject}
 * Note that all the dimensions are all in pixels
 * @author Pierre-André Mudry (mui)
 * @version 1.0 
 */
public class PhysicsBox extends AbstractPhysicsObject {
	
	public PhysicsBox(String name, Vector2 position, float width, float height) {		
		super(Type.Polygon, name, position, width, height, 1.0f, 0.3f, 0.3f, true);		
	}
	
	public PhysicsBox(String name, Vector2 position, float width, float height, float angle) {		
		super(Type.Polygon, name, position, width, height, 1.0f, 0.3f, 0.3f, angle, true);
	}
		
	public PhysicsBox(String name, Vector2 position, float width, float height, float density, float restitution, float friction) {
		super(Type.Polygon, name, position, width, height, density, restitution, friction, true);
	}	
	
}
