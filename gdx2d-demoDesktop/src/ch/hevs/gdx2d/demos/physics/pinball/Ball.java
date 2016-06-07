package ch.hevs.gdx2d.demos.physics.pinball;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/**
 * WORK IN PROGRESS, DO NOT USE
 */
public class Ball extends PhysicsCircle implements DrawableObject {
	protected TextureRegion[] sprites;
	private float radius;

	public Ball(String name, Vector2 position, float radius, TextureRegion[] sprites) {
		super(name, position, radius);
		this.sprites = sprites;
		this.radius = radius;
	}

	@Override
	public void draw(GdxGraphics g) {
		int i = ((int) (getBodyPosition().x + getBodyPosition().y)) % sprites.length;
		float w = sprites[i].getRegionWidth();
		float h = sprites[i].getRegionHeight();

		g.draw(sprites[i], getBodyPosition().x-w/2, getBodyPosition().y-h/2, w/2, h/2, w, h, 2*radius/w, 2*radius/h, getBodyAngleDeg());
	}
}
