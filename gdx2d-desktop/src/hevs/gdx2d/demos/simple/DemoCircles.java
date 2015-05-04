package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoCircles extends PortableApplication {
	int radius[] = new int[8];
	int speed[] = new int[8];

	public static void main(String[] args) {
		/**
		 * Note that the constructor parameter is used to determine if running
		 * on Android or not. As we are in main there, it means we are on
		 * desktop computer.
		 */
		new DemoCircles();
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Moving circles demo, mui 2013");

		for (int i = 0; i < 8; i++) {
			radius[7 - i] = (i + 1) * (40 / 8);
			speed[i] = 1;
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear(Color.DARK_GRAY);

		// Draws the circles
		for (int i = 0; i < 8; i++) {
			if (radius[i] >= 40 || radius[i] <= 3) {
				speed[i] *= -1;
			}

			radius[i] += speed[i];

			g.drawFilledCircle(
					getWindowWidth() / 10 + i * (getWindowWidth() - getWindowWidth() / 10) / 8,
					getWindowHeight() / 2,
					radius[i],
					new Color(radius[i] / 40.0f, 0, 0, 1));
		}

		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		if (onAndroid())
			getAndroidResolver().showAboutBox();
	}

}
