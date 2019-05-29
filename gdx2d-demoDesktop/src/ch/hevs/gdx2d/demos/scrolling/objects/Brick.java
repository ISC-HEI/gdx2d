package ch.hevs.gdx2d.demos.scrolling.objects;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple brick that does nothing special
 *
 * @author Pierre-Andre Mudry (mui)
 */
public class Brick implements DrawableObject {

	final BitmapImage img = new BitmapImage("images/brick.png");
	int posx, posy;

	public Brick(int x, int y) {
		posx = x;
		posy = y;
	}

	@Override
	public void draw(GdxGraphics g) {
		g.drawPicture(posx, posy, img);
	}

}
