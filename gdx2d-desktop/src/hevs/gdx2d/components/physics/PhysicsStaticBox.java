package hevs.gdx2d.components.physics;

import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * A physical box that does not move, see {@link AbstractPhysicsObject}
 * Note that all the dimensions are all in pixels
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsStaticBox extends AbstractPhysicsObject {
	
	/**
	 * @param name An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width The width of the physics box
	 * @param height The height of the physics box
	 */
	public PhysicsStaticBox(String name, Vector2 position, float width, float height) {
		super(Type.Polygon, name, position, width, height, 20, 0.1f, 0.3f, false);
	}

	/**
	 * @param name An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width The width of the physics box
	 * @param height The height of the physics box
	 * @param angle The angle of the box (trig angle)
	 */
	public PhysicsStaticBox(String name, Vector2 position, float width, float height, float angle) {
		super(Type.Polygon, name, position, width, height, angle, 0.1f, 0.3f, angle, false);
	}
}
