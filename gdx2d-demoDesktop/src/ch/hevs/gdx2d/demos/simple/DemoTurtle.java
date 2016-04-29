package ch.hevs.gdx2d.demos.simple;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to display something animated with the
 * turtle paradigm
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoTurtle extends PortableApplication {

	public static void main(String[] args) {
		/**
		 * Note that the constructor parameter is used to determine if running
		 * on Android or not. As we are in main there, it means we are on
		 * desktop computer.
		 */
		new DemoTurtle();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Moving turtle demo, mui 2016");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear(Color.DARK_GRAY);

		g.drawFPS();
		g.drawSchoolLogo();
	}
}
