package hevs.gdx2d.demos.image_drawing;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * This demo is similar to {@link DemoSimpleImage} but demonstrates
 * how to use the alpha parameter to draw images
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoAlphaImage extends PortableApplication {
	BitmapImage imgBitmap, backgroundBitmap;

	float alpha1 = 0.06f, alpha2 = 0.3f, alpha3 = 0.6f, alpha4 = 0.94f;
	int dir1 = 1, dir2 = 1, dir3 = 1, dir4 = 1;

	public static void main(String[] args) {
		new DemoAlphaImage();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Alpha transparency modification for images, mui 2013");

		// Loads the image that will be displayed in the middle of the screen
		imgBitmap = new BitmapImage("data/images/Android_PI_48x48.png");

		// Load the background image
		backgroundBitmap = new BitmapImage("data/images/back1_512.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		/**
		 * Rendering
		 */
		// Clear the screen
		g.clear();

		// Render an with mirror
		g.drawBackground(backgroundBitmap, 0, 0);

		if (alpha1 <= 0.05f || alpha1 >= 0.95f) dir1 *= -1;
		if (alpha2 <= 0.05f || alpha2 >= 0.95f) dir2 *= -1;
		if (alpha3 <= 0.05f || alpha3 >= 0.95f) dir3 *= -1;
		if (alpha4 <= 0.05f || alpha4 >= 0.95f) dir4 *= -1;

		alpha1 += dir1 > 0 ? 0.01f : -0.01f;
		alpha2 += dir2 > 0 ? 0.01f : -0.01f;
		alpha3 += dir3 > 0 ? 0.01f : -0.01f;
		alpha4 += dir4 > 0 ? 0.01f : -0.01f;

		g.drawAlphaPicture(getWindowWidth() / 3.0f, getWindowHeight() / 3.0f, alpha1, imgBitmap);
		g.drawAlphaPicture(getWindowWidth() * 2.0f / 3.0f, getWindowHeight() / 3.0f, alpha2, imgBitmap);
		g.drawAlphaPicture(getWindowWidth() / 3.0f, getWindowHeight() * 2.0f / 3.0f, alpha3, imgBitmap);
		g.drawAlphaPicture(getWindowWidth() * 2.0f / 3.0f, getWindowHeight() * 2.0f / 3.0f, alpha4, imgBitmap);

		g.drawFPS();        // Draws the number of frame per second
		g.drawSchoolLogo(); // Draws the school logo
	}

	@Override
	public void onDispose() {
		super.onDispose();
		imgBitmap.dispose();
		backgroundBitmap.dispose();
	}
}
