package ch.hevs.gdx2d.demos.complex_shapes;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.colors.PaletteGenerator;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;


import java.util.Random;
import java.util.Vector;

/**
 * Performance demo animation rendering multiple circles at the same scale. This
 * is a complex demo, you should not start with this one.
 *
 * @author Pierre-André Mudry, mui
 * @version 1.0, April 2013
 */
public class DemoComplexShapes extends PortableApplication {

	final Random rrand = new Random(12345);
	final Vector<Color> colors = new Vector<Color>();
	final Vector<DrawableShape> shapes = new Vector<DrawableShape>();
	final Vector<Integer> directions = new Vector<Integer>();
	private final int N_SHAPES = 500;

	private int screenWidth, screenHeight;
	private int maxRadius;
	private float angle = 0;
	private type_shape shape_type = type_shape.CIRCLE;
	// For the movement of objects
	private double counter = 10, dir = 1.34;
	// The image which will be displayed
	private BitmapImage imageBmp;

	public static void main(String[] args) {
		new DemoComplexShapes();
	}

	/**
	 * Create a nice color palette in the blue tones
	 */
	public void fillPalette() {
		Color a = new Color(0.4f, 0, 0.8f, 0);
		Color b = new Color(0, 0.2f, 0.97f, 0);
		Color c = new Color(0, 0.6f, 0.85f, 0);

		for (int i = 0; i < 50; i++) {
			colors.add(PaletteGenerator.RandomMix(a, b, c, 0.01f));
		}
	}

	protected void destroyObjects(int nObjects) {
		for (int i = 0; i < nObjects; i++)
			shapes.remove(0);
	}

	protected void generateObjects(int nObjects) {
		/**
		 * Generate some objects to be drawn randomly
		 */
		for (int i = 0; i < nObjects; i++) {
			int width = 10 + rrand.nextInt(40);

			DrawableShape s = new DrawableShape(width, width,
					rrand.nextInt(screenWidth),
					rrand.nextInt(screenHeight),
					colors.get(rrand.nextInt(colors.size())));

			shapes.add(s);

			int dir = rrand.nextInt(10) + 1;
			dir = rrand.nextBoolean() ? dir : -dir;

			directions.add(dir);
		}
	}

	@Override
	public void onInit() {
		fillPalette();

		this.setTitle("Demo shapes, mui 2013");
		screenWidth = getWindowWidth();
		screenHeight = getWindowHeight();
		maxRadius = Math.min(getWindowHeight() / 2, getWindowWidth() / 2) - 10;

		imageBmp = new BitmapImage("data/images/Android_PI_48x48.png");
		generateObjects(N_SHAPES);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Updates the counter for the position on screen
		dir = counter > maxRadius || counter <= 5 ? dir *= -1 : dir;
		counter += dir;
		angle = angle >= 360 ? 0 : angle + 0.2f;

		// Move the shapes on the screen
		for (int i = 0; i < shapes.size(); i++) {
			final DrawableShape r = shapes.get(i);

			if (r.x > screenWidth + imageBmp.getImage().getWidth() / 2 || r.x < 0) {
				int val = directions.get(i);
				directions.setElementAt(-val, i);
			}

			r.x += directions.get(i);
		}

		// Do the drawing
		switch (shape_type) {
			case CIRCLE:
				g.clear(Color.BLACK);
				for (DrawableShape i : shapes) {
					g.drawFilledCircle(i.x, i.y, i.width, i.c);
				}
				break;
			case IMAGE:
				g.clear(new Color(0.9f, 0.9f, 0.9f, 1));
				for (DrawableShape i : shapes)
					g.drawTransformedPicture(i.x, i.y, angle + i.offset, 1, imageBmp);
				break;
			case RECT:
				g.clear(Color.BLACK);
				for (DrawableShape i : shapes)
					// FIXME Did not work well for old Linux driver
					g.drawFilledRectangle(i.x, i.y, i.width, i.width, 0, i.c);
			default:
				break;
		}

		g.drawSchoolLogo();
		g.drawFPS();
	}

	@Override
	public void onKeyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.PLUS:
				generateObjects(100);
				Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size());
				break;

			case Input.Keys.MINUS:
				if (shapes.size() > 100) {
					Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size());
					destroyObjects(100);
				}
				break;
		}
	}

	@Override
	/**
	 * Change shape on click
	 */
	public void onClick(int x, int y, int button) {
		if (shape_type == type_shape.CIRCLE)
			shape_type = type_shape.RECT;
		else if (shape_type == type_shape.RECT)
			shape_type = type_shape.IMAGE;
		else if (shape_type == type_shape.IMAGE)
			shape_type = type_shape.CIRCLE;
	}

	private enum type_shape {
		CIRCLE, IMAGE, RECT
	}
}
