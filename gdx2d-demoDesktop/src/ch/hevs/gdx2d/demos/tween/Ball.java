package ch.hevs.gdx2d.demos.tween;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple ball object
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
public class Ball implements DrawableObject {
	final BitmapImage img = new BitmapImage("images/soccer.png");
	public float posx, posy;

	public Ball(float x, float y) {
		posx = x;
		posy = y;
	}

	@Override
	public void draw(GdxGraphics g) {
		draw(g, 1.0f);
	}

	public void draw(GdxGraphics g, float scale) {
		g.drawTransformedPicture(posx, posy, 0, scale, img);
	}
}
