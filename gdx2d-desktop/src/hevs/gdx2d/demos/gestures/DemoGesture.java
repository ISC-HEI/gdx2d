package hevs.gdx2d.demos.gestures;

import com.badlogic.gdx.graphics.OrthographicCamera;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Simple demo for gestures on Android.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.01
 */
public class DemoGesture extends PortableApplication {

	BitmapImage image;

	OrthographicCamera cam = null;
	float initialScale = 1.0f;

	public DemoGesture(boolean onAndroid) {
		super(onAndroid);

		if (!onAndroid) {
			Logger.error("This demo only works on Android! Exiting");
			exit();
		}
	}

	public static void main(String args[]) {
		new DemoGesture(false);
	}

	@Override
	public void onZoom(float initialDistance, float distance) {
		float ratio = initialDistance / distance;
		cam.zoom = initialScale * ratio;
		cam.update();
	}

	@Override
	public void onClick(int x, int y, int button) {
		initialScale = cam.zoom;
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		cam.position.add(-deltaX * cam.zoom, deltaY * cam.zoom, 0);
		cam.update();
	}

	@Override
	public void onInit() {
		image = new BitmapImage("data/images/Android_PI_48x48.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		if (cam == null)
			cam = g.getCamera();

		g.drawPicture(getWindowWidth() / 2, getWindowHeight() / 2, image);
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}

}
