package ch.hevs.gdx2d.demos.physics.pinball;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import com.badlogic.gdx.math.Vector2;



public class Sensor extends PhysicsStaticBox {

	public Sensor(String name, Vector2 position, float width, float height) {
		super(name, position, width, height);
		setSensor(true);
	}
}
