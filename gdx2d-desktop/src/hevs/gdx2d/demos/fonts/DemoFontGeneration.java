package hevs.gdx2d.demos.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A demo that shows how to generate different fonts and hot to display texts
 * with different alignments.
 * Custom font (ttf files) can be loaded from the resource folder and customized
 * using parameters of the {@code FreeTypeFontParameter} class.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.2
 */
public class DemoFontGeneration extends PortableApplication {
	private final static String LOREM = "Lorem ipsum dolor sit amet,\n" +
			"consectetur adipiscing elit.\n" +
			"In laoreet libero sit amet\n" +
			"sollicitudin vestibulum.\n" +
			"The default font is Roboto size 15, white";
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

		// See all parameters available in the FreeTypeFontParameter
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(optimusF);
		parameter.size = generator.scaleForPixelHeight(40);
		parameter.color = Color.WHITE;
		optimus40 = generator.generateFont(parameter);

		parameter.size = generator.scaleForPixelHeight(60);
		parameter.color = Color.BLUE;
		optimus60 = generator.generateFont(parameter);
		generator.dispose();

		generator = new FreeTypeFontGenerator(timelessF);
		parameter.size = generator.scaleForPixelHeight(40);
		parameter.color = Color.RED;
		timeless40 = generator.generateFont(parameter);
		generator.dispose();

		generator = new FreeTypeFontGenerator(starjediF);
		parameter.size = generator.scaleForPixelHeight(40);
		parameter.color = Color.GREEN;
		starjedi40 = generator.generateFont(parameter);
		generator.dispose();

		// Use shadows, border, etc.
		generator = new FreeTypeFontGenerator(icePixelF);
		parameter.size = generator.scaleForPixelHeight(40);
		parameter.color = Color.WHITE;
		parameter.borderColor = Color.BLUE;
		parameter.borderWidth = 3;
		parameter.shadowOffsetY = -8;
		parameter.shadowColor = Color.LIGHT_GRAY;
		icepixel40 = generator.generateFont(parameter);
		generator.dispose();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		final float h = g.getScreenHeight();
		final float y = h / 7.0f;
		final float w = g.getScreenWidth();

		g.clear();

		/**
		 * Default font is Robot 15.
		 */
		g.setColor(Color.WHITE);
		g.drawStringCentered(y / 2 + y * 1, LOREM);

		/**
		 * Display different fonts centered on the screen.
		 */
		g.drawStringCentered(y / 2 + y * 6, "Ice pixel 40", icepixel40);
		g.drawStringCentered(y / 2 + y * 5, "Star jedi 40", starjedi40);
		g.drawStringCentered(y / 2 + y * 4, "Timeless size 40", timeless40);
		g.drawStringCentered(y / 2 + y * 3, "Optimus size 40", optimus40);
		g.drawStringCentered(y / 2 + y * 2, "Optimus size 60", optimus60);

		/**
		 * Display fonts left, right and center aligned.
		 */
		g.setColor(Color.MAGENTA);
		g.drawString(10, h - 10, "left\naligned\ntext", Align.left);
		g.drawString(w - 10, h - 10, "right\naligned\ntext", Align.right);
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
