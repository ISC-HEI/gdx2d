package hevs.gdx2d.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

/**
 * A physical circle that does not move, see {@link AbstractPhysicsObject}.
 * <p/>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsStaticCircle extends AbstractPhysicsObject {

	/**
	 * Create a static circle.
	 *
	 * @param name     An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param radius   The radius of the object, in pixels
	 */
	public PhysicsStaticCircle(String name, Vector2 position, float radius) {
		super(Type.Circle, name, position, radius, radius, 10f, 0.6f, 0.6f, false);
	}

	/**
	 * Create a static circle.
	 *
	 * @param name        An optional name for the object (for debug)
	 * @param position    The position of the center point
	 * @param radius      The radius of the object, in pixels
	 * @param density     The density of the object, in kg/ms2
	 * @param restitution The restitution factor (energy given back on collision). 1 means all the energy is restituted, 0 means no energy is given back
	 * @param friction    The friction factor (between 0 and 1)
	 */
	public PhysicsStaticCircle(String name, Vector2 position, float radius, float density, float restitution, float friction) {
		super(Type.Circle, name, position, radius, radius, density, restitution, friction, false);
	}
}
