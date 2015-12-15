package ch.hevs.gdx2d.components.physics.primitives;

import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * A physical box that does not move, see {@link AbstractPhysicsObject}.
 * <p/>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0.1
 */
public class PhysicsStaticBox extends AbstractPhysicsObject {

	private static final float DEFAULT_DENSITY = 20;
	private static final float DEFAULT_RESTITUTION = 0.1f;
	private static final float DEFAULT_FRICTION = 0.3f;

	/**
	 * Create a static box.
	 *
	 * @param name     An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width    The width of the physics box
	 * @param height   The height of the physics box
	 */
	public PhysicsStaticBox(String name, Vector2 position, float width, float height) {
		super(Type.Polygon, name, position, width, height, DEFAULT_DENSITY, DEFAULT_RESTITUTION, DEFAULT_FRICTION, false);
	}

	/**
	 * Create a static box.
	 *
	 * @param name     An optional name for the object (for debug)
	 * @param position The position of the center point
	 * @param width    The width of the physics box
	 * @param height   The height of the physics box
	 * @param angle    The angle of the box (trig angle)
	 */
	public PhysicsStaticBox(String name, Vector2 position, float width, float height, float angle) {
		super(Type.Polygon, name, position, width, height, DEFAULT_DENSITY, DEFAULT_RESTITUTION, DEFAULT_FRICTION, angle, false);
	}
}
