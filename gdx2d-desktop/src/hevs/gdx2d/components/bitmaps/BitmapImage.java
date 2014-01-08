package hevs.gdx2d.components.bitmaps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * An image encapsulation class for GDX2DLib.
 * 
 * TODO: loading each image in a texture is a bad idea but it works. Refactor this
 * using http://steigert.blogspot.ch/search?updated-max=2012-03-15T18:29:00-03:00&max-results=3&start=3&by-date=false
 * @author Nils Chatton (chn)
 * @author Pierre-André Mudry (mui)
 * 
 * @version 1.1
 */
public class BitmapImage implements Disposable{
	
	private Texture image;
	private TextureRegion tRegion;
	
	public BitmapImage(String file) {
		image = new Texture(Gdx.files.internal(file));
		tRegion = new TextureRegion(image);
		//image.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);		
		//Utils.callCheck("hevs.gdx2d.lib.Game2D", "create");		
	}

	public Texture getImage() {
		return image;
	}

	public TextureRegion getRegion() {
		return tRegion;
	}

	/**
	 * Mirrors the image left right
	 */
	public void mirrorLeftRight(){
		tRegion.flip(true, false);		
	}
	
	/**
	 * Mirrors the image up down
	 */
	public void mirrorUpDown(){
		tRegion.flip(false, true);
	}
	
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
