package hevs.gdx2d.demos.simple.mistify;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A classical Mistify screen saver clone.<br>
 * <p/>
 * Code adapted from <a href="http://r3dux.org/2010/11/mystify-2-0/"> http://r3dux.org/2010/11/mystify-2-0</a>
 *
 * @author Pierre-André Mudry
 * @version 1.0
 * @see <a href="http://r3dux.org/2010/11/mystify-2-0/"> http://r3dux.org/2010/11/mystify-2-0</a> for the original source code
 */
public class DemoLines extends PortableApplication {
	final int N_SHAPES = 3;
	BounceShape[] s;
	int frame = 0;

	public static void main(String[] args) {
		new DemoLines();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Mistfy screensaver clone, mui 2013");

		// Allocate size for the shapes
		s = new BounceShape[N_SHAPES];

		// Initialize them
		for (int i = 0; i < N_SHAPES; i++) {
			s[i] = new BounceShape(this.getWindowWidth(), this.getWindowHeight());
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

		// Clears the screen
		g.clear();

		// Draws and moves the shapes
		for (BounceShape shape : s) {
			shape.drawShape(g);
			shape.moveShape(g.getScreenWidth(), g.getScreenHeight());
			shape.shiftShapeColour(frame++);
		}

		/**
		 *  TODO it would be nice to have something like an accumulation buffer that fades here
		 */
		g.drawFPS();
		g.drawSchoolLogo();
	}

}
