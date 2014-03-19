package hevs.gdx2d.demos.physics.joints;

import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * A simple class to display physics circles with a color
 * 
 * @author Pierre-André Mudry
 */
public class CircleParticle extends PhysicsCircle implements DrawableObject {
	// Constructor
	public CircleParticle(String name, Vector2 position, int radius) {
		super(name, position, radius);
	}

	@Override
	public void draw(GdxGraphics g) {
		// We have to convert meters (physics) to pixels (display)
		Vector2 position = getBodyPosition();
		float radius = getBodyRadius();

		// The color is dependent from the last collision time
		g.drawFilledCircle(position.x, position.y, radius, Color.WHITE);
	}
}
