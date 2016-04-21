package ch.hevs.gdx2d.demos.menus.screens.example_screens;

import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.screen_management.RenderingScreen;
import hevs.gdx2d.lib.GdxGraphics;

/**
 * A simple @SplashScreen to demonstrate transition between screens
 */
public class SplashScreen extends RenderingScreen {
	BitmapImage imgBitmap;

	@Override
	public void onInit() {
		// Loads the image that will be displayed in the middle of the screen
		imgBitmap = new BitmapImage("data/images/Android_PI_48x48.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.NAVY);
		g.drawStringCentered(g.getScreenHeight() / 2, "1 - Splash screen");
		g.drawPicture(g.getScreenWidth() / 2.0f, g.getScreenHeight() / 3.0f, imgBitmap);
		g.drawSchoolLogo();
	}

	@Override
	public void dispose() {
		imgBitmap.dispose();
	}
}
