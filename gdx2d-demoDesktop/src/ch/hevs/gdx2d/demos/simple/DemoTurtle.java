package ch.hevs.gdx2d.demos.simple;

import ch.hevs.gdx2d.components.graphics.Turtle;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to display something animated with the
 * turtle paradigm
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoTurtle extends PortableApplication {

	Turtle t;
	private Random r = new Random();

	void drawSegment(double length, int level) {
		/**
		 * Recursion shall end somewhere
		 */
		if (level == 0) {
			t.forward(length);
			return;
		}

		double currentLevel = length / 3.0;
		drawSegment(currentLevel, level - 1);
		t.turn(-60);
		drawSegment(currentLevel, level - 1);
		t.turn(120);
		drawSegment(currentLevel, level - 1);
		t.turn(-60);
		drawSegment(currentLevel, level - 1);
	}

	void drawTriangle(double length, int level) {
		drawSegment(length, level);
		t.turn(120);
		drawSegment(length, level);
		t.turn(120);
		drawSegment(length, level);
		t.turn(120);
	}


	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Moving turtle demo, mui 2016");
	}

	int i = 2, direction = 1;
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (t == null)
			t = new Turtle(g, g.getScreenWidth(), g.getScreenHeight());

		// Clears the screen
		g.clear(Color.DARK_GRAY);

		// Go to the top of the screen
		t.changeColor(Color.WHITE);

		t.penDown();
		t.setAngle(60);
		t.jump((float)(g.getScreenWidth() * 0.5), (float)(g.getScreenHeight() * 0.25));
		
		drawTriangle(20+i*2, 5);
		
		i += direction;
		
		if(i == 100|| i == 1)
			direction *= -1;
		
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoTurtle();
	}
}
