package hevs.gdx2d.components.physics;

import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * A physical shape which collides as a circle, see {@link AbstractPhysicsObject}
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsCircle extends AbstractPhysicsObject{
	
	public PhysicsCircle(String name, Vector2 position, float radius) {
		super(Type.Circle, name, position, radius, radius, 10f, 0.6f, 0.6f, true);			
	}
	
	public PhysicsCircle(String name, Vector2 position, float radius, float density, float restitution, float friction) {
		super(Type.Circle, name, position, radius, radius, density, restitution, friction, true);					
	}
}
