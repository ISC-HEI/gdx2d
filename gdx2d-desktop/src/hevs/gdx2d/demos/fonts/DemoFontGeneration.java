package hevs.gdx2d.demos.fonts;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * A demo that shows how to generate different fonts for 
 * rendering
 * 
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
public class DemoFontGeneration extends PortableApplication{
	BitmapFont optimus60, optimus40, timeless40, starjedi40, icepixel40;
	
	public DemoFontGeneration(boolean onAndroid) {
		super(onAndroid);
	}
	
	@Override
	public void onInit() {
		this.setTitle("Font generation demo, mui 2013");
		
		FileHandle optimusF = Gdx.files.internal("font/OptimusPrinceps.ttf");
		FileHandle timelessF = Gdx.files.internal("font/Timeless.ttf");
		FileHandle starjediF = Gdx.files.internal("font/Starjedi.ttf");
		FileHandle icePixelF = Gdx.files.internal("font/ice_pixel-7.ttf");
		
		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(optimusF);
		optimus40 = generator.generateFont(40);
		optimus40.setColor(Color.BLUE);
		optimus60 = generator.generateFont(60);
		generator.dispose();
		
		generator = new FreeTypeFontGenerator(timelessF);
		timeless40 = generator.generateFont(40);
		timeless40.setColor(Color.RED);
		generator.dispose();
		
		generator = new FreeTypeFontGenerator(starjediF);
		starjedi40 = generator.generateFont(40);
		starjedi40.setColor(Color.GREEN);
		generator.dispose();

		generator = new FreeTypeFontGenerator(icePixelF);
		int s = generator.scaleForPixelHeight(50);
		icepixel40 = generator.generateFont(s);
		generator.dispose();
	}
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		
		float h = getWindowHeight() / 6.0f;
		
		/**
		 * Space the fonts evenly on screen
		 */
		g.drawStringCentered(h/2 + h * 1, "Optimus size 60", optimus60);
		g.drawStringCentered(h/2 + h * 2, "Optimus size 40", optimus40);
		g.drawStringCentered(h/2 + h * 3, "Timeless 40", timeless40);		
		g.drawStringCentered(h/2 + h * 4, "Star jedi 40", starjedi40);		
		g.drawStringCentered(h/2 + h * 5, "Ice pixel 40", icepixel40);		
	}
	
	@Override
	public void onDispose() {
		// Release what we've used
		super.onDispose();
		optimus40.dispose();
		optimus60.dispose();
		timeless40.dispose();
		starjedi40.dispose();
		icepixel40.dispose();
	}
	
	public static void main(String[] args) {
		new DemoFontGeneration(false);
	}

}
