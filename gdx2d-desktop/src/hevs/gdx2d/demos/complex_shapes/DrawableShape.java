package hevs.gdx2d.demos.complex_shapes;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

/**
 * A drawable shape class for the {@link DemoComplexShapes} demonstration
 * 
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
public class DrawableShape {

	private static Random rnd = new Random();
	
	int width, height;
	int x, y;
	float offset;
	Color c;

	DrawableShape(int w, int h, int x, int y, Color c) {
		this.width = w;
		this.height = h;
		this.x = x;
		this.y = y;
		this.c = c;
		this.offset = rnd.nextFloat() * 45;
	}
}
