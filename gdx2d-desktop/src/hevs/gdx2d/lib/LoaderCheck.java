package hevs.gdx2d.lib;

import hevs.gdx2d.components.bitmaps.BitmapImage;

public class LoaderCheck extends PortableApplication {

	BitmapImage b = new BitmapImage("data/images/clock.png");
	
	public LoaderCheck() {
		super(false);
	}
	
	@Override
	public void onInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new LoaderCheck();
	}

}
