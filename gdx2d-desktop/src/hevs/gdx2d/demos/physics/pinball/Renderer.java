package hevs.gdx2d.demos.physics.pinball;

import java.util.LinkedList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Renderer {
	public void draw(GdxGraphics g, World world, LinkedList<TemporaryDrawable> decorations) {
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		
		// Draw physical objects
		for (int i = 0 ; i < bodies.size; i++) {
			Object o = bodies.get(i).getUserData();
			
			if (o instanceof DrawableObject) {
				((DrawableObject) o).draw(g);
			}
		}
		
		// Draw decorations
		LinkedList<TemporaryDrawable> toRemove = new LinkedList<TemporaryDrawable>();
		for (TemporaryDrawable d:decorations) {
			d.draw(g);
			if (d.isDone()) {
				toRemove.add(d);
			}
		}
		
		// Remove finished decorations
		decorations.removeAll(toRemove);
	}
}
