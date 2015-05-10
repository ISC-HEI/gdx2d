/**
 * A physical line that does not move, see {@link AbstractPhysicsObject}.
 * <p/>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Marc Pignat (pim)
 * 
 * Implemented using a polygon of 1 pixel height. 
 */

package hevs.gdx2d.components.physics.primitives;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class PhysicsStaticLine extends AbstractPhysicsObject {

	private static Vector2 center(Vector2 p1, Vector2 p2) {
		return new Vector2(p2).add(p1).scl(.5f);
	}

	private static float length(Vector2 p1, Vector2 p2) {
		return p1.dst(p2);
	}

	private static float angle(Vector2 p1, Vector2 p2) {
		return new Vector2(p2).sub(p1).angleRad();
	}

	/**
	 * Create PhysicsStaticLine between two points
	 * 
	 * @param name for debug purposes
	 * @param p1 start point
	 * @param p2 destination point
	 */
	public PhysicsStaticLine(String name, Vector2 p1, Vector2 p2) {
		super(Type.Polygon, name, center(p1, p2), length(p1, p2), 1, 1.0f,
				0.3f, 0.3f, angle(p1, p2), false);
		System.out.println("center : " + center(p1, p2));
		System.out.println("length : " + length(p1, p2));
		System.out.println("angle : " + angle(p1, p2));
	}

	/**
	 * Create PhysicsStaticLine between two points
	 * 
	 * @param name for debug purposes
	 * @param p1 start point
	 * @param p2 destination point
	 * @param density
	 * @param restitution
	 * @param friction
	 * 
	 * @see AbstractPhysicsObject
	 */
	public PhysicsStaticLine(String name, Vector2 p1, Vector2 p2,
			float density, float restitution, float friction) {
		super(Type.Polygon, name, center(p1, p2), length(p1, p2), 1, 1.0f,
				density, restitution, angle(p1, p2), false);
	}
}
