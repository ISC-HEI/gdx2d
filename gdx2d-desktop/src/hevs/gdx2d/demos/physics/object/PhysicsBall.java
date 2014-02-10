package hevs.gdx2d.demos.physics.object;

import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * A simple physics ball that can be drawn
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class PhysicsBall extends PhysicsCircle implements DrawableObject{
	Color c;
	static boolean transparentRendering = false;
	
	public PhysicsBall(Vector2 position, int radius, Color c) {
		this("ball", position, radius, c);
	}

	public PhysicsBall(Vector2 position, int radius) {
		this("ball", position, radius);
	}
	
	public PhysicsBall(String name, Vector2 position, int radius, Color c) {
		super(name, position, radius);
		this.c = c;
	}
	
	public PhysicsBall(String name, Vector2 position, int radius) {
		super(name, position, radius);
		this.c = Color.PINK;
	}
		
	@Override
	public void draw(GdxGraphics g) {
		Vector2 position = getBodyPosition(); 
		float radius = getBodyRadius();
		
		if(transparentRendering)
			g.drawCircle(position.x, position.y, radius);
		else{			
			g.drawCircle(position.x, position.y, radius, Color.BLACK);
			g.drawFilledCircle(position.x, position.y, radius-1, c);
//			g.drawFilledBorderedCircle(position.x, position.y, radius, c, Color.BLACK);
		}
			
	}
	
	public static void change_rendering() {
		transparentRendering = !transparentRendering;
	}
}
