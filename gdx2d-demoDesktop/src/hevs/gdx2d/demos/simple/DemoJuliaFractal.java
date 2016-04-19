package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Compute a Julia fractal (Julia set).
 * <p/>
 * Some parameters can be used to tune the fractal, like
 * {@link DemoJuliaFractal#C1}, {@link DemoJuliaFractal#C2} coefficients,
 * {@link DemoJuliaFractal#MAX_ITER} and the {@link DemoJuliaFractal#SCALE}.<br>
 * For now, this demonstration is not available for Android because it is too
 * slow on mobile. To have better performances, RenderScript or shader must be
 * used.
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoJuliaFractal extends PortableApplication {

	/* Fractal parameters to be tuned */
	private final static int IMAGE_SIZE = 512; // create a N-by-N image (power of two)
	/* Used for pixels operations */
	private final static Color BCK_COLOR = new Color(0.0f, 0, 0.2f, 1.0f);
	private static int MAX_ITER = 115; // Stop after max iteration for a pixel
	private static float SCALE = 1 / 2f; // Scale factor for the fractal
	// Base fractal coefficients
	private static float C1_START = -0.55f;
	private static float C2_START = 0.65f;
	private static float C1 = C1_START, C2 = C2_START;
	private Pixmap pixmap = new Pixmap(IMAGE_SIZE, IMAGE_SIZE, Format.RGBA8888);
	private Texture currentTexture;
	private boolean isFractalGenerated = false;
	private long startTime, estimatedTime; // Time measurement

	// Julia fractal on each pixels
	private static void workPixel(int i, int j, Pixmap pixmap) {
		// Convert to mathematical coordinates with a custom scale
		final float x = i * SCALE * 2 / (float) IMAGE_SIZE - 1 * SCALE;
		final float y = j * SCALE * 2 / (float) IMAGE_SIZE - 1 * SCALE;

		int k = 0;
		float a = x, b = y;

		// Julia algorithm with a max upper bound
		while (k < MAX_ITER && (a * a + b * b) < 4) {
			float aCopy = a;
			a = (a * a - b * b) + C1;
			b = 2 * aCopy * b + C2;
			k++;
		}

		// Draw the current pixel
		if (k == MAX_ITER)
			// Draw red pixels when max iteration is reached
			pixmap.drawPixel(i, j, Color.rgba8888(1.0f, 0, 0, 1.0f));
		else {
			// Use HSV to have a better color contrast
			final Color color = ColorUtils.hsvToColor(k / ((float) MAX_ITER), 1.0f, 1.0f);
			pixmap.drawPixel(i, j, color.toIntBits()); // Convert to ABGR to draw
		}
	}

	// Java main for the desktop demonstration
	public static void main(String args[]) {
		new DemoJuliaFractal();
	}

	@Override
	public void onInit() {
		this.setTitle("Julia fractal, mei 2014");
		Logger.log("Click to generate a fractal with new parameters.");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// The fractal is generated only once. Takes about 1/10 seconds
		// depending on the fractal parameters.
		if (!isFractalGenerated) {
			pixmap.fill();   // Use a Pixamp because the setPixel method is very slow (issue #26)

			// Computation time estimation
			startTime = System.currentTimeMillis();
			for (int i = 0; i < IMAGE_SIZE; i++)
				for (int j = 0; j < IMAGE_SIZE; j++)
					// Compute the Julia fractal for each pixels
					workPixel(i, j, pixmap);
			estimatedTime = System.currentTimeMillis() - startTime;

			currentTexture = new Texture(pixmap, Format.RGBA8888, false);
			isFractalGenerated = true;
			Logger.log(String.format("New fractal: C1=%f, C2=%f.", C1, C2));
		}

		/* Drawing */
		g.clear(BCK_COLOR); // Add some blue for the fractal background

		// Draw the generated fractal
		g.drawBackground(currentTexture, 0, 0);
		g.drawSchoolLogo();

		// Display the fractal generation time
		final String info = String.format("Fractal genereated in %d ms.", estimatedTime);
		g.drawString(10, (int) (0.98 * g.getScreenHeight()), info);
	}

	@Override
	public void onClick(int x, int y, int button) {
		// Generate some new fractals
		C1 += 0.05f;
		if (C1 > 0.2f)
			C1 = C1_START;
		isFractalGenerated = false;
	}

	@Override
	public void onDispose() {
		pixmap.dispose();
	}
}