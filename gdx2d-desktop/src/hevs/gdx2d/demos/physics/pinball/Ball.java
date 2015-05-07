package hevs.gdx2d.demos.physics.pinball;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * WORK IN PROGRESS, DO NOT USE
 */
public class Ball extends PhysicsCircle implements DrawableObject{
	
	protected TextureRegion[] sprites;

	public Ball(String name, Vector2 position, float radius, TextureRegion sprites[]) {
		super(name, position, radius);
		this.sprites = sprites;
	}

	@Override
	public void draw(GdxGraphics g) {
		int currentBallFrame = ((int) (getBodyPosition().x + getBodyPosition().y)) % sprites.length;
		
		// FIXME : cleanup : handle those variables
		g.draw(sprites[currentBallFrame], getBodyPosition().x-10, getBodyPosition().y-10, 10, 10, 20, 20, 1, 1, getBodyAngleDeg());
	}
}
