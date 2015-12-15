package ch.hevs.gdx2d.components.physics.utils;

import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;

/**
 * Creates solid boundaries around the screen so everything will stay inside it for physics simulation.
 * <p/>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsScreenBoundaries {

	/**
	 * Create a screen boundaries centered on the screen.
	 *
	 * @param width  The width of the boundaries box
	 * @param height The height of the boundaries box
	 */
	public PhysicsScreenBoundaries(float width, float height) {
		new PhysicsStaticBox("ground", new Vector2(width / 2, 0), width, 1f);
		new PhysicsStaticBox("leftwall", new Vector2(0, height / 2), 1f, height);
		new PhysicsStaticBox("rightwall", new Vector2(width, height / 2), 1f, height);
		new PhysicsStaticBox("ceiling", new Vector2(width / 2, height), width, 1f);
	}
}
