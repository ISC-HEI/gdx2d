package hevs.gdx2d.demos.image_drawing;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

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

	/**
	 * Constructor
	 *
	 * @param onAndroid tells if we are currently running on Android or not
	 */
	public DemoMirrorImage(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoMirrorImage(false);
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

		// Render an with mirror
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
