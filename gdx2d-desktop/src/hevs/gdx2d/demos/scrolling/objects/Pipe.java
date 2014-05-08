package hevs.gdx2d.demos.scrolling.objects;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A simple pipe that does nothing special
 * @author Pierre-Andre Mudry (mui)
 */

public class Pipe implements DrawableObject {

	int posx, posy;
	final BitmapImage img = new BitmapImage("data/images/pipe.png");
	
	public Pipe(int x, int y){
		posx = x;
		posy = y;		
	}
	
	@Override
	public void draw(GdxGraphics g) {
		g.drawPicture(posx, posy, img);
	}

}
