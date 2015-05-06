package hevs.gdx2d.demos.physics.pinball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.PhysicsWorld;

public class Renderer {
	public void draw(GdxGraphics g) {
		World world = PhysicsWorld.getInstance();
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		
		for (int i = 0 ; i < bodies.size; i++) {
			Object o = bodies.get(i).getUserData();
			
			if (o instanceof DrawableObject) {
				((DrawableObject) o).draw(g);
			}
		}
	}
}
