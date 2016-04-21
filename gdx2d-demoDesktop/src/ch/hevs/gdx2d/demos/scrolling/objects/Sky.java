package ch.hevs.gdx2d.demos.scrolling.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * A quite complex background image that is linked to the camera.
 * Note that we use a texture to draw the background only *ONCE*
 * for performance reasons.
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.2
 */
public class Sky implements DrawableObject {
	Pixmap p;
	Texture t;
	int width, height;

	public Sky() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		/**
		 *  For the sky we need to draw directy onto an image.
		 *  An image is first of all a pixmap.
		 */

		p = new Pixmap(width, height, Format.RGB888);

		// Fill the sky with a nice color
		for (int i = 0; i < height; i++) {
			int col = ColorUtils.hsvToRgb(0.55f, (float) i / height, 1.0f);
			p.setColor(ColorUtils.intToColor(col));
			p.drawLine(0, i, width, i);
		}

		// For the pixmap, we need to create a texture
		t = new Texture(p);
	}

	@Override
	public void draw(GdxGraphics g) {
		/**
		 *  Using this method is faster than drawing an image and
		 *  also makes sure that the image is not moved with the camera (fixed
		 *  position).
		 */
		g.drawBackground(t, 0, 0);
	}
}
