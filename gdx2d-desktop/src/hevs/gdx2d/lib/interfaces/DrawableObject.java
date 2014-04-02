package hevs.gdx2d.lib.interfaces;

import hevs.gdx2d.lib.GdxGraphics;

/**
 * An interface that every object you want to draw
 * should implement to tell how to render it.
 * 
 * @author Pierre-André Mudry (mui)
 *
 */
public interface DrawableObject {
	void draw(GdxGraphics g);
}
