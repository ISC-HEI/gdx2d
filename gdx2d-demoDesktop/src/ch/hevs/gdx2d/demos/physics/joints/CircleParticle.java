package ch.hevs.gdx2d.demos.physics.joints;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.components.physics.primitives.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple class to display physics circles with a color
 *
 * @author Pierre-André Mudry
 */
public class CircleParticle extends PhysicsCircle implements DrawableObject {
	Color c = Color.WHITE;

	public CircleParticle(String name, Vector2 position, int radius) {
		super(name, position, radius, 1, 0.001f, 60.2f);
	}

	public CircleParticle(Vector2 position, int radius, Color c, float restitution, float friction) {
		super("", position, radius, 1, restitution, friction);
		this.c = c;
	}

	@Override
	public void draw(GdxGraphics g) {
		// We have to convert meters (physics) to pixels (display)
		Vector2 position = getBodyPosition();
		float radius = getBodyRadius();

		// The color is dependent from the last collision time
		g.drawFilledCircle(position.x, position.y, radius, c);
	}
}
