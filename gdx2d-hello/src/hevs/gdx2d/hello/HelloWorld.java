package hevs.gdx2d.hello;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Hello World demo.
 * 
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
public class HelloWorld extends PortableApplication {

	private BitmapImage imgBitmap;

	public HelloWorld(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		setTitle("Hello World, mei 2014");
		imgBitmap = new BitmapImage("data/images/hei-pi.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		g.drawPicture(getWindowWidth() / 2.0f, getWindowHeight() / 2.0f, imgBitmap);

		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new HelloWorld(false);
	}
}
