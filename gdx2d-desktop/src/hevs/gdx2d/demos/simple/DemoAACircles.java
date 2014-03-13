package hevs.gdx2d.demos.simple;

import hevs.gdx2d.components.colors.Palette;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Random;

public class DemoAACircles extends PortableApplication {

	Random r = new Random();

	static final int Q = 1000;

	int[] x = new int[Q];
	int[] y = new int[Q];
	int[] rad = new int[Q];
	int[] p = new int[Q];

	public DemoAACircles() {
		super(false);
	}

	@Override
	public void onInit() {
		for (int i = 0; i < Q; i++) {
			x[i] = r.nextInt(500);
			y[i] = r.nextInt(500);
			rad[i] = r.nextInt(30) + 10;
			p[i] = r.nextInt(Palette.pastel2.length);
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		for (int i = 0; i < Q; i++) {
			g.drawAntiAliasedCircle(x[i], y[i], rad[i], Palette.pastel2[p[i]]);
		}

		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoAACircles();

	}

}
