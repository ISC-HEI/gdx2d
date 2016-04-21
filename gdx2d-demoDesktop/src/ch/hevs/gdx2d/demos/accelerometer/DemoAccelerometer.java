package ch.hevs.gdx2d.demos.accelerometer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A demonstration program for the accelerometers
 * This works only on Android.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoAccelerometer extends PortableApplication {
	private final double SMOOTHING = 30; // This value changes the dampening effect of the low-pass
	private BitmapImage compassBitmap;
	// For low-pass filtering
	private float smoothedValue = 0;

	public static void main(String args[]) {
		new DemoAccelerometer();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Read the accelerometers
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();

		// Low pass filtering of the value
		smoothedValue += (accelX - smoothedValue) / SMOOTHING;

		// Draws
		g.clear();
		g.drawTransformedPicture(getWindowWidth() / 2, getWindowHeight() / 2, smoothedValue * 20, 1, compassBitmap);
		g.drawSchoolLogo();

		g.drawString(10, 60, "Non filtered values:");
		g.drawString(15, 20, "X " + accelX);
		g.drawString(15, 40, "Y " + accelY);
	}

	@Override
	public void onInit() {
		boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);

		if (!available) {
			Gdx.app.error("Libgdx error", "Accelerometers are not available ! Exiting");
			Gdx.app.exit();
		}

		compassBitmap = new BitmapImage("data/images/compass_150.png");
	}
}
