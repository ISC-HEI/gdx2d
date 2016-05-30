package ch.hevs.gdx2d.components.bitmaps;

import ch.hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A class to load a sprite sheet which is more or less a 2-D array of {@link TextureRegion}.
 * </p>
 * Look at the {@code DemoSpriteSheet} to see how to use it.
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class Spritesheet implements Disposable {
	Texture img;

	/** Each sprites texture regions. */
	public TextureRegion[][] sprites;

	/**
	 * Create a sprite sheet from an image file. Gdx must be loaded.
	 *
	 * @param file the image file
	 * @param tileWidth a tile's width in pixels
	 * @param tileHeight a tile's height in pixels
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public Spritesheet(String file, int tileWidth, int tileHeight) {
		Utils.assertGdxLoaded(Spritesheet.class);

		img = new Texture(Gdx.files.internal(file));
		sprites = TextureRegion.split(img, tileWidth, tileHeight);
	}

	/** Releases the allocated resources. */
	@Override
	public void dispose() {
		img.dispose();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
