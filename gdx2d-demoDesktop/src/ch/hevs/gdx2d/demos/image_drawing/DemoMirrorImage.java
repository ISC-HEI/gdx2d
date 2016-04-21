package ch.hevs.gdx2d.demos.image_drawing;


import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * This demo is similar to {@link DemoSimpleImage} but demonstrates
 * how to use the mirrorUpDown and mirrorLeftRight methods
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoMirrorImage extends PortableApplication {
	float counter = 0;
	BitmapImage imgBitmap;

	public static void main(String[] args) {
		new DemoMirrorImage();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Mirror image drawing, mui 2013");

		// Loads the image that will be displayed in the middle of the screen
		imgBitmap = new BitmapImage("data/images/Android_PI_48x48.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		/**
		 * Rendering
		 */
		// Clear the screen
		g.clear();

		// Renders the image mirrored
		g.drawPicture(getWindowWidth() / 3.0f, getWindowHeight() / 3.0f, imgBitmap);
		imgBitmap.mirrorUpDown();
		g.drawPicture(getWindowWidth() * 2.0f / 3.0f, getWindowHeight() / 3.0f, imgBitmap);
		imgBitmap.mirrorUpDown();

		g.drawPicture(getWindowWidth() / 3.0f, getWindowHeight() * 2.0f / 3.0f, imgBitmap);
		imgBitmap.mirrorLeftRight();
		g.drawPicture(getWindowWidth() * 2.0f / 3.0f, getWindowHeight() * 2.0f / 3.0f, imgBitmap);
		imgBitmap.mirrorLeftRight();

		g.drawFPS();        // Draws the number of frame per second
		g.drawSchoolLogo(); // Draws the school logo
	}
}
