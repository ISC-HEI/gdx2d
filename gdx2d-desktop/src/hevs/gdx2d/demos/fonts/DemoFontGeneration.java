package hevs.gdx2d.demos.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A demo that shows how to generate different fonts and hot to display texts
 * with different alignments.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
public class DemoFontGeneration extends PortableApplication {
	private final static String LOREM = "Lorem ipsum dolor sit amet,\n" +
			"consectetur adipiscing elit.\n" +
			"In laoreet libero sit amet\n" +
			"sollicitudin vestibulum.\n" +
			"Roboto size 15 (default)";
	private BitmapFont optimus60, optimus40, timeless40, starjedi40, icepixel40;

	public DemoFontGeneration(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoFontGeneration(false);
	}

	@Override
	public void onInit() {
		this.setTitle("Font generation demo, mui 2013");

		// Load some font files
		FileHandle optimusF = Gdx.files.internal("data/font/OptimusPrinceps.ttf");
		FileHandle timelessF = Gdx.files.internal("data/font/Timeless.ttf");
		FileHandle starjediF = Gdx.files.internal("data/font/Starjedi.ttf");
		FileHandle icePixelF = Gdx.files.internal("data/font/ice_pixel-7.ttf");

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
		final float h = g.getScreenHeight();
		final float y = h / 7.0f;
		final float w = g.getScreenWidth();

		g.clear();

		/**
		 * Display different fonts centered on the screen.
		 */
		g.drawStringCentered(y / 2 + y * 6, "Ice pixel 40", icepixel40);
		g.drawStringCentered(y / 2 + y * 5, "Star jedi 40", starjedi40);
		g.drawStringCentered(y / 2 + y * 4, "Timeless size 40", timeless40);
		g.drawStringCentered(y / 2 + y * 3, "Optimus size 40", optimus40);
		g.drawStringCentered(y / 2 + y * 2, "Optimus size 60", optimus60);
		g.setColor(Color.MAGENTA);
		g.drawStringCentered(y / 2 + y * 1, LOREM);

		/**
		 * Display fonts left, right and center aligned.
		 */
		g.setColor(Color.WHITE);
		g.drawString(10, getWindowHeight() - 10, "left\naligned\ntext", HAlignment.LEFT);
		g.drawString(w - 10, getWindowHeight() - 10, "right\naligned\ntext", HAlignment.RIGHT);
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
}
