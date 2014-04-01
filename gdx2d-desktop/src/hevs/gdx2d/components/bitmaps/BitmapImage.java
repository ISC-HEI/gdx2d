package hevs.gdx2d.components.bitmaps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * An image encapsulation class. Allows to store an image
 * and to use it for drawing.
 * 
 * @author Nils Chatton (chn)
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class BitmapImage implements Disposable{
	
	// TODO: loading each image in a texture is a bad idea but it works. Refactor this
	// using http://steigert.blogspot.ch/search?updated-max=2012-03-15T18:29:00-03:00&max-results=3&start=3&by-date=false
	private Texture image;
	private TextureRegion tRegion;
	
	
	
	public BitmapImage(String file) {
		image = new Texture(Gdx.files.internal(file));
		tRegion = new TextureRegion(image);
		image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//image.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);		
		//Utils.callCheck("hevs.gdx2d.lib.Game2D", "create");		
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
