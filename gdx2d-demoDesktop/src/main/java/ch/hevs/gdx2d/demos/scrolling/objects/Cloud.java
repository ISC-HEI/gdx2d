package ch.hevs.gdx2d.demos.scrolling.objects;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple cloud that does nothing special
 *
 * @author Pierre-Andre Mudry (mui)
 */
public class Cloud implements DrawableObject {

	final BitmapImage cloudImage = new BitmapImage("images/cloud_1.png");
	public int x, y;

	public Cloud(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(GdxGraphics g) {
		g.drawPicture(x, y, cloudImage);
	}

}
