package ch.hevs.gdx2d.hello;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.desktop.PortableApplication;

/**
 * Hello World demo.
 *
 * @author Christopher Métrailler (mei)
 * @author Pierre-André Mudry (mui)
 * @version 1.01
 */
public class HelloWorld extends PortableApplication {

	private BitmapImage imgBitmap;

	@Override
	public void onInit() {
		setTitle("Hello World - mei,mui 2014");
		//
		imgBitmap = new BitmapImage("res/lib/icon64.png" /* "images/hei-pi.png" */);
	}

	/**
	 * Animation related variables
	 */
	int direction = 1;
	float currentTime = 0;
	final float ANIMATION_LENGTH = 2f; // Animation length (in seconds)
	final float MIN_ANGLE = -20;
	final float MAX_ANGLE = 20;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		// Compute the angle of the image using an elastic interpolation
		float t = computePercentage();
		float angle = Interpolation.bounce.apply(MIN_ANGLE, MAX_ANGLE, t);

		// Draw everything
		g.drawTransformedPicture(getWindowWidth() / 2.0f, getWindowHeight() / 2.0f, angle, 1.0f, imgBitmap);
		g.drawStringCentered(getWindowHeight() * 0.8f, "Welcome to GDX2D!");
		g.drawFPS();
		g.drawSchoolLogo();
	}

	/**
	 * Compute time percentage for making
	 * a looping animation
	 *
	 * @return the current normalized time
	 */
	private float computePercentage() {
		if (direction == 1)
			currentTime += Gdx.graphics.getDeltaTime();
		else
			currentTime -= Gdx.graphics.getDeltaTime();

		if (currentTime >= ANIMATION_LENGTH || currentTime <= 0)
			direction *= -1;

		return currentTime / ANIMATION_LENGTH;
	}

	public static void main(String[] args) {
		new HelloWorld();
	}
}

