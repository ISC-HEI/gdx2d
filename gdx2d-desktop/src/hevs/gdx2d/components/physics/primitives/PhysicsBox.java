package hevs.gdx2d.components.physics.primitives;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

/**
 * A physical shape which collides as a box, see {@link AbstractPhysicsObject}.
 * <p/>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsBox extends AbstractPhysicsObject {

	/**
	 * Create a physics box.
	 *
	 * @param name     An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width    The width of the physics box
	 * @param height   The height of the physics box
	 */
	public PhysicsBox(String name, Vector2 position, float width, float height) {
		super(Type.Polygon, name, position, width, height, 1.0f, 0.3f, 0.3f, true);
	}

	/**
	 * Create a physics box.
	 *
	 * @param name     An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width    The width of the physics box
	 * @param height   The height of the physics box
	 * @param angle    The angle of the object (trig angle)
	 */
	public PhysicsBox(String name, Vector2 position, float width, float height, float angle) {
		super(Type.Polygon, name, position, width, height, 1.0f, 0.3f, 0.3f, angle, true);
	}

	/**
	 * Create a physics box.
	 *
	 * @param name        An optional name for the object (for debug)
	 * @param position    The position of the center point
	 * @param width       The width of the physics box
	 * @param height      The height of the physics box
	 * @param density     The density of the object, in kg/ms2
	 * @param restitution The restitution factor (energy given back on collision). 1 means all the energy is restituted, 0 means no energy is given back
	 * @param friction    The friction factor (between 0 and 1)
	 */
	public PhysicsBox(String name, Vector2 position, float width, float height, float density, float restitution, float friction) {
		super(Type.Polygon, name, position, width, height, density, restitution, friction, true);
	}

	/**
	 * Create a physics box.
	 *
	 * @param name        An optional name for the object (for debug)
	 * @param position    The position of the center point
	 * @param width       The width of the physics box
	 * @param height      The height of the physics box
	 * @param density     The density of the object, in kg/ms2
	 * @param restitution The restitution factor (energy given back on collision). 1 means all the energy is restituted, 0 means no energy is given back
	 * @param friction    The friction factor (between 0 and 1)
	 * @param angle       The angle of the object (trig angle)
	 */
	public PhysicsBox(String name, Vector2 position, float width, float height, float density, float restitution, float friction, float angle) {
		super(Type.Polygon, name, position, width, height, density, restitution, friction, angle, true);
	}
}
