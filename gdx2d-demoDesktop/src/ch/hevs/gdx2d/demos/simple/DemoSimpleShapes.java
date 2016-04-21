package ch.hevs.gdx2d.demos.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A very simple demonstration on how to
 * display something with the library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleShapes extends PortableApplication {

	public static void main(String args[]) {
		new DemoSimpleShapes();
	}

	@Override
	public void onInit() {
		this.setTitle("Simple shapes, mui 2013");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Draws a yellow circle
		g.setColor(Color.YELLOW);
		g.drawCircle(250, 400, 20);

		// Draws a green rectangle
		g.setColor(Color.GREEN);
		g.drawRectangle(20, 250, 40, 40, 0);

		g.drawFilledCircle(50, 50, 20, Color.PINK);
		g.drawFilledRectangle(80, 30, 20, 20, 0, new Color(0.5f, 0.5f, 0.5f, 0.5f));

		// Draws a blue polygon
		Vector2 points[] = {
				new Vector2(200, 200),
				new Vector2(250, 250),
				new Vector2(300, 200)
		};

		Polygon p = new Polygon(points);
		g.drawFilledPolygon(p, Color.BLUE);
	}
}
