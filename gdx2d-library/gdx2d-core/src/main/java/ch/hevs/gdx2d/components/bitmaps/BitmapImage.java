package ch.hevs.gdx2d.components.bitmaps;

import ch.hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * An image encapsulation class.
 * </p>
 * Allow to store an image and to use it for drawing. Use one of the following methods to draw the picture:
 * <ul>
 *     <li>{@link ch.hevs.gdx2d.lib.GdxGraphics#drawPicture(float, float, BitmapImage)}</li>
 *     <li>{@link ch.hevs.gdx2d.lib.GdxGraphics#drawAlphaPicture(float, float, float, BitmapImage)}</li>
 *     <li>{@link ch.hevs.gdx2d.lib.GdxGraphics#drawBackground(BitmapImage, float, float)}</li>
 * </ul>
 * </p>
 * Do not forget to call the {@link #dispose()} method.
 *
 * @author Nils Chatton (chn)
 * @author Pierre-André Mudry (mui)
 * @version 1.2
 */
public final class BitmapImage implements Disposable {

	// TODO: loading each image in a texture is a bad idea but it works. Refactor this
	// using http://steigert.blogspot.ch/search?updated-max=2012-03-15T18:29:00-03:00&max-results=3&start=3&by-date=false
	protected Texture image;
	protected TextureRegion tRegion;

	protected String filePath;
	protected Pixmap pixmap;

	/**
	 * Create an image from a file. Gdx must be loaded.
	 *
	 * @param file the path of the image to load
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public BitmapImage(String file) {
		Utils.assertGdxLoaded(BitmapImage.class);

		image = new Texture(Gdx.files.internal(file));
		tRegion = new TextureRegion(image);
		image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		filePath = file;
	}

	/**
	 * Should normally not be used directly, use at your own risk
	 *
	 * @return A texture
	 */
	public Texture getImage() {
		return image;
	}

	/**
	 * Should normally not be used directly, use at your own risk
	 *
	 * @return A #TextureRegion
	 */
	public TextureRegion getRegion() {
		return tRegion;
	}

	/**
	 * Mirrors the image left <-> right
	 */
	public void mirrorLeftRight() {
		tRegion.flip(true, false);
	}

	/**
	 * Mirrors the image up <-> down
	 */
	public void mirrorUpDown() {
		tRegion.flip(false, true);
	}


	/**
	 * @param p The pixel position, in image space
	 * @return If the pixel can be contained in the image space
	 * @see BitmapImage#isContained(int, int)
	 */
	public boolean isContained(Vector2 p) {
		return isContained((int) p.x, (int) p.y);
	}

	/**
	 * Is a given pixel position contained in the image or not
	 * The pixel is given in image space (width x height)
	 *
	 * @param x x-position of the pixel
	 * @param y y-position of the pixel
	 * @return if the pixel is contained inside the image
	 */
	public boolean isContained(int x, int y) {
		if (x < 0 || y < 0)
			return false;

		return x < image.getWidth() && y < image.getHeight();
	}

	/**
	 * To get the color of a pixel in an image, we
	 * must get translate screen coordinates to image coordinates
	 * This is the role of the following function.
	 *
	 * @param pixelPosition The pixel we want to read, in screen coordinates
	 * @param imagePosition The current position of the image, in screen coordinates
	 * @return The pixel position in the image space coordinates
	 */
	public Vector2 pixelInScreenSpace(Vector2 pixelPosition, Vector2 imagePosition) {
		int width = image.getWidth();
		int height = image.getWidth();

		Vector2 center = pixelPosition.sub(imagePosition);
		center.add(width / 2f, height / 2f);

		return center;
	}

	/**
	 * Returns the current color of an image
	 *
	 * @param x The x position we want to read
	 * @param y The y position we want to read
	 * @return The color read
	 */
	public Color getColor(int x, int y) {
		if (pixmap == null) {
	        if (!image.getTextureData().isPrepared()) {
	        	image.getTextureData().prepare();
	        }
			pixmap = image.getTextureData().consumePixmap();
		}

		return new Color(pixmap.getPixel(x,pixmap.getHeight()-y));
	}

	/**
	 * Release the resources allocated by this {@link BitmapImage}, normally
	 * should be called automatically
	 */
	@Override
	public void dispose() {
		if (pixmap != null && image.getTextureData().disposePixmap()) {
			pixmap.dispose();
		}
		image.dispose();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
