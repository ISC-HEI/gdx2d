package hevs.gdx2d.demos.physics.collisions;

import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Demonstrates how to implements collisions detection with box2d
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class BumpyBall extends PhysicsCircle implements DrawableObject{
	float lastCollision = 0.5f;	
	
	public BumpyBall(String name, Vector2 position, int radius) {
		super(name, position, radius);
	}
	
	/**
	 * Called for every collision
	 */
	@Override
	public void collision(AbstractPhysicsObject other, float energy) {
		// Big enough collisions have a visual effect
		if(energy > 0.1){
			Logger.log(name + " collided " + other.name + " with energy " + energy);
			lastCollision = 1.0f;
		}
	} 
	
	@Override
	public void draw(GdxGraphics g) {
		Vector2 position = getBodyPosition(); 
		float radius = getBodyRadius();
		
		//	The color is dependent from the last collision time
		Color c = ColorUtils.hsvToColor(0.8f, 0.98f, lastCollision);		
		g.drawFilledCircle(position.x, position.y, radius, c);

		// Return to the normal color after having been excited
		if(lastCollision >= 0.5f){
			lastCollision -= 0.01f;
		}
	}
}
