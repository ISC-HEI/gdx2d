package hevs.gdx2d.components.bitmaps;

import hevs.gdx2d.lib.utils.Utils;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * An image encapsulation class. Allows to store an image
 * and to use it for drawing.
 * 
 * @author Nils Chatton (chn)
 * @author Pierre-André Mudry (mui)
 * @version 1.11
 */
final public class BitmapImage implements Disposable{
	
	// TODO: loading each image in a texture is a bad idea but it works. Refactor this
	// using http://steigert.blogspot.ch/search?updated-max=2012-03-15T18:29:00-03:00&max-results=3&start=3&by-date=false
	protected Texture image;
	protected TextureRegion tRegion;
	
	protected String filePath;
	protected BufferedImage cleanImage;

	/**
	 * Use the {@link BitmapImageFactory} to create instances of this class
	 * @see BitmapImageFactory
	 */
	public BitmapImage(String file) {
		Utils.assertGdxLoaded("BitmapImages can only be created in the onInit "
				+ "method of a class extending PortableApplication "
				+ "(or must be called from within this method)");
		
		image = new Texture(Gdx.files.internal(file));
		tRegion = new TextureRegion(image);
		image.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		filePath = file;
	}

	/**
	 * Should normally not be used directly, use at your own risk
	 * @return 
	 */
	public Texture getImage() {
		return image;
	}

	/**
	 * Should normally not be used directly, use at your own risk
	 * @return 
	 */
	public TextureRegion getRegion() {
		return tRegion;
	}

	/**
	 * Mirrors the image left <-> right
	 */
	public void mirrorLeftRight(){
		tRegion.flip(true, false);		
	}
	
	/**
	 * Mirrors the image up <-> down
	 */
	public void mirrorUpDown(){
		tRegion.flip(false, true);
	}

	
	/**
	 * @param p The pixel position, in image space
	 * @return If the pixel can be contained in the image space
	 * @see BitmapImage#isContained(int, int)
	 */
	public boolean isContained(Vector2 p){
		return isContained((int)p.x, (int)p.y);
	}
	
	/**
	 * Is a given pixel position contained in the image or not
	 * The pixel is given in image space (width x height)
	 * @param x x-position of the pixel
	 * @param y y-position of the pixel
	 * @return if the pixel is contained inside the image
	 */
	public boolean isContained(int x, int y){
		if(x < 0 || y < 0)
			return false;
		
		if(x >= image.getWidth())
			return false;
		
		if(y >= image.getHeight())
			return false;
		
		return true;
	}
	
	/**
	 * To get the color of a pixel in an image, we
	 * must get translate screen coordinates to image coordinates
	 * This is the role of the following function. 
	 * @param pixelPosition The pixel we want to read, in screen coordinates
	 * @param imagePosition The current position of the image, in screen coordinates
	 * @return The pixel position in the image space coordinates 
	 */	
	public Vector2 pixelInScreenSpace(Vector2 pixelPosition, Vector2 imagePosition){
		int width = image.getWidth();
		int height = image.getWidth();
		
		Vector2 center = pixelPosition.sub(imagePosition);
		center.add(width/2, height/2);
		
		return center;
	}
	
	/**
	 * Returns the current color of an image
	 * @param x The x position we want to read
	 * @param y The y position we want to read
	 * @return The color read
	 */
	public int getColor(int x, int y){
		// Read the image only if needed
		if(cleanImage == null){
			try{
				cleanImage = ImageIO.read(Gdx.files.internal(filePath).file()); 
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}		
		return cleanImage.getRGB(x, cleanImage.getHeight() - y);
	}
	
	/**
	 * Release the resources allocated by this {@link BitmapImage}, normally
	 * should be called automatically
	 */
	@Override
	public void dispose() {
		image.dispose();
	}
	
	@Override
	protected void finalize() throws Throwable {	
		super.finalize();
		dispose();
	}
}
