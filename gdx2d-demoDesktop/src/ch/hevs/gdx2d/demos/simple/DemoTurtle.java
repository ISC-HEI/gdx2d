package ch.hevs.gdx2d.demos.simple;

import com.badlogic.gdx.graphics.Color;

import ch.hevs.gdx2d.components.graphics.Turtle;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * A very simple demonstration on how to display something animated with the
 * turtle paradigm
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoTurtle extends PortableApplication {
	Turtle t;

	/**
	 * Draws the snowflake
	 * @param length Length of the side of the flake
	 * @param level The complexity level of the recursion
	 */
	void drawFlake(double length, int level) {
		drawSegment(length, level);
		t.turn(120);
		drawSegment(length, level);
		t.turn(120);
		drawSegment(length, level);
		t.turn(120);
	}

	/**
	 * Draws a side of the flake
	 * @param length length of the segment
	 * @param level complexity level for the recursion
	 */
	void drawSegment(double length, int level) {
		//Recursion shall end somewhere
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

	@Override
	public void onInit() {
		setTitle("Moving turtle demo, mui 2016");
	}

	int flakeSize = 2, animDirection = 1;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (t == null)
			t = new Turtle(g, g.getScreenWidth(), g.getScreenHeight());

		// Clears the screen
		g.clear(Color.DARK_GRAY);

		// Go to the top of the screen
		t.changeColor(Color.WHITE);

		/**
		 * Locate the turtle at the bottom of the screen
		 */
		t.penDown();
		t.setAngle(60);
		t.jump((float) (g.getScreenWidth() * 0.5), (float) (g.getScreenHeight() * 0.25));

		// Displays the snowflake
		drawFlake(20 + flakeSize * 2, 4);

		// Animate size
		flakeSize += animDirection;

		if (flakeSize == 100 || flakeSize == 2)
			animDirection *= -1;

		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoTurtle();
	}
}
