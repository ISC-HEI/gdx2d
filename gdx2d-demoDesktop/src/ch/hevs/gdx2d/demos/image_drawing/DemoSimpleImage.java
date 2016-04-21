package ch.hevs.gdx2d.demos.image_drawing;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Demo application to demonstrate how to load a file which
 * should be located in a {@code data} folder at the root
 * of the project.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleImage extends PortableApplication {
	float counter = 0;
	BitmapImage imgBitmap;

	public static void main(String[] args) {
		new DemoSimpleImage();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Simple image drawing, mui 2013");

		// Loads the image that will be displayed in the middle of the scren
		imgBitmap = new BitmapImage("data/images/compass_150.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		/**
		 * Rendering
		 */
		// Clear the screen
		g.clear();

		// Render an image which turns with scale
		g.drawPicture(getWindowWidth() / 2, getWindowHeight() / 2, imgBitmap);

		g.drawFPS();        // Draws the number of frame per second
		g.drawSchoolLogo(); // Draws the school logo
	}
}
