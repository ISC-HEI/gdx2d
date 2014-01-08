package hevs.gdx2d.components.physics.utils;

import hevs.gdx2d.components.physics.PhysicsStaticBox;

import com.badlogic.gdx.math.Vector2;

/**
 * Creates solid boundaries around the screen so everything will stay inside it for physics
 * simulation.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsScreenBoundaries {

	public PhysicsScreenBoundaries(float width, float height) {
		new PhysicsStaticBox("ground", new Vector2(0, 0), width, .5f);
		new PhysicsStaticBox("leftwall", new Vector2(0,0), .5f, height);
		new PhysicsStaticBox("rightwall", new Vector2(width, 0), .5f, height);
		new PhysicsStaticBox("ceiling", new Vector2(0,height), width, .5f);
	}
}
