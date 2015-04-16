package hevs.gdx2d.components.bitmaps;

import hevs.gdx2d.demos.spritesheet.DemoSpriteSheet;
import hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class to load a spritesheet which is more or less
 * a bidimensional array of {@link TextureRegion}. See the
 * {@link DemoSpriteSheet} for how to use it. 
 * 
 * @author Pierre-André Mudry
 * @version 1.0
 *
 */
public class Spritesheet {	
	Texture img;
	public TextureRegion[][] sprites;
	
	public Spritesheet(String file, int tileWidth, int tileHeight){		
			Utils.assertGdxLoaded("Spritesheet can only be created in the onInit "
					+ "method of a class extending PortableApplication "
					+ "(or must be called from within this method)");
			
			img = new Texture(Gdx.files.internal(file));
			sprites = TextureRegion.split(img, tileWidth, tileHeight);
				
			// Gdx.app.log("[SpriteSheet]", "Loaded " + sprites.length + "x" + sprites[0].length + " sprites");		
	}
}
