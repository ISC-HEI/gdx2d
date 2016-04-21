package ch.hevs.gdx2d.demos.shaders.simple;

import hevs.gdx2d.components.colors.Palette;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Random;

/**
 * Demonstrates the use of anti-aliased circles function
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoAACircles extends PortableApplication {

	// The number of circles to generate
	static final int Q = 5;
	Random r = new Random();
	// To store the various circles informations
	int[] x = new int[Q];
	int[] y = new int[Q];
	int[] rad = new int[Q];
	int[] p = new int[Q];
	boolean done = false;

	public static void main(String[] args) {
		new DemoAACircles();
	}

	@Override
	public void onInit() {
		/**
		 * Create all those circles, at various locations and using different
		 * colors from a palette
		 */
		for (int i = 0; i < Q; i++) {
			x[i] = r.nextInt(500);
			y[i] = r.nextInt(500);
			rad[i] = r.nextInt(30) + 10;
			p[i] = r.nextInt(Palette.pastel2.length);
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

		/**
		 * Draws all those circles. TODO : this is slow and performance should
		 * be improved, using texture?
		 */
		if (!done) {
			g.clear();
			for (int i = 0; i < Q; i++) {
				g.drawAntiAliasedCircle(x[i], y[i], rad[i],
						Palette.pastel2[p[i]]);
			}
			done = false;

			g.drawFPS();
			g.drawSchoolLogo();
		}

	}
}
