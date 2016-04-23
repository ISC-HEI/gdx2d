package ch.hevs.gdx2d.demos.complex_shapes;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * First try at reproducing http://lab.hakim.se/hypnos/
 * Not done yet...
 *
 * @author Pierre-André Mudry, mui
 * @version 1.0, April 2015
 */

class Coord {
	float x, y, r;

	Coord(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
}

public class DemoHypnos extends PortableApplication {

	final ArrayList<Coord> points = new ArrayList<Coord>();
	private float quality = 100;
	private float layerSize;
	private float radius;
	private int screenWidth, screenHeight;
	private float angle = 0;

	public static void main(String[] args) {
		new DemoHypnos();
	}

	protected void generateObjects() {
		for (int i = 0; i < quality; i++) {
			float x = screenWidth / 2 + (float) Math.sin(i / quality * 2 * Math.PI) * radius - layerSize;
			float y = screenHeight / 2 + (float) Math.cos(i / quality * 2 * Math.PI) * radius - layerSize;
			float r = (float) ((i / quality) * Math.PI);
			Coord c = new Coord(x, y, r);
			points.add(c);
		}
	}

	@Override
	public void onInit() {
		this.setTitle("Demo shapes, mui 2013");
		screenWidth = getWindowWidth();
		screenHeight = getWindowHeight();
		radius = Math.min(screenWidth, screenHeight) * 0.2f;
		layerSize = radius * 0.25f;
		generateObjects();
	}

	private void update() {
		for (Coord c : points) {
			c.r += 0.3f;
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		angle = angle >= 360 ? 0 : angle + 0.2f;
		g.clear(Color.WHITE);
		update();

		for (Coord c : points) {
			g.setColor(Color.WHITE);
			g.drawFilledRectangle(c.x, c.y, 50, 50, c.r);
			g.setColor(Color.BLACK);
			g.drawRectangle(c.x, c.y, 50, 50, c.r);
		}

		Coord beg = points.get(0);
		g.setColor(Color.WHITE);
		g.drawFilledRectangle(beg.x, beg.y, 50, 50, beg.r);
		g.setColor(Color.BLACK);
		g.drawRectangle(beg.x, beg.y, 50, 50, beg.r);


		for (int i = (int) (quality - 30); i < quality; i++) {
			Coord c = points.get(i);
			g.setColor(Color.WHITE);
			g.drawFilledRectangle(c.x, c.y, 50, 50, c.r);
			g.setColor(Color.BLACK);
			g.drawRectangle(c.x, c.y, 50, 50, c.r);
		}

		g.drawSchoolLogo();
		g.drawFPS();
	}
}
