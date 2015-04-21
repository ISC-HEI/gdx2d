package hevs.gdx2d.demos.image_drawing;

import com.badlogic.gdx.graphics.GL11;
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
public class DemoBlendingFunctions extends PortableApplication {
	BitmapImage imgBitmap, backgroundBitmap;

	float w, h;
	int[] src_functions = {
			GL11.GL_ZERO,
			GL11.GL_ONE,
			GL11.GL_SRC_COLOR,
			GL11.GL_ONE_MINUS_SRC_COLOR,
			GL11.GL_SRC_ALPHA,
			GL11.GL_ONE_MINUS_SRC_ALPHA
	};

	int[] dst_functions = {
			GL11.GL_ZERO,
			GL11.GL_ONE,
			GL11.GL_DST_COLOR,
			GL11.GL_ONE_MINUS_DST_COLOR,
			GL11.GL_SRC_ALPHA,
			GL11.GL_ONE_MINUS_SRC_ALPHA
	};

	/**
	 * Constructor
	 *
	 * @param onAndroid tells if we are currently running on Android or not
	 */
	public DemoBlendingFunctions(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoBlendingFunctions(false);
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Blending functions for images, mui 2013");

		// Loads the image that will be displayed in the middle of the screen
		imgBitmap = new BitmapImage("data/images/texture.png");

		// Load the background image
		backgroundBitmap = new BitmapImage("data/images/back1_512.png");

		// Dimensions of the image
		w = imgBitmap.getImage().getWidth();
		h = imgBitmap.getImage().getHeight();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		/**
		 * Rendering of the scene
		 */
		g.clear();

		g.drawBackground(backgroundBitmap, 0, 0);

		//g.spriteBatch.setBlendFunction(GL11.GL_ZERO, GL11.GL_ZERO);

		// Use the different blending mode combinations
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				g.spriteBatch.setBlendFunction(src_functions[i], dst_functions[j]);
				g.drawPicture(60 + w * i + 10 * i, 60 + h * j + 10 * j, imgBitmap);
			}
		}

		g.spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
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
