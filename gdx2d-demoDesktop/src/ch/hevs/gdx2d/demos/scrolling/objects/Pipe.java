package ch.hevs.gdx2d.demos.scrolling.objects;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple pipe that does nothing special
 *
 * @author Pierre-Andre Mudry (mui)
 */

public class Pipe implements DrawableObject {

	final BitmapImage img = new BitmapImage("data/images/pipe.png");
	int posx, posy;

	public Pipe(int x, int y) {
		posx = x;
		posy = y;
	}

	@Override
	public void draw(GdxGraphics g) {
		g.drawPicture(posx, posy, img);
	}

}
