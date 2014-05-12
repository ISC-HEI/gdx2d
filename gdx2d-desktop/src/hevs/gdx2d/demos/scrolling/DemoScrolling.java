package hevs.gdx2d.demos.scrolling;

import hevs.gdx2d.demos.scrolling.objects.Brick;
import hevs.gdx2d.demos.scrolling.objects.Cloud;
import hevs.gdx2d.demos.scrolling.objects.Coin;
import hevs.gdx2d.demos.scrolling.objects.Pipe;
import hevs.gdx2d.demos.scrolling.objects.Sky;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.utils.Logger;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Demonstrates how to scroll and zoom on a scene. Also demonstrates how to
 * delegate render to other objects through the {@link DrawableObject}
 * interface.
 * 
 * For some reason, running in windowed mode displays stuttering problems. Image
 * stuttering can be removed by playing the demo full screen.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.04
 */
public class DemoScrolling extends PortableApplication {

	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();

	// Default zoom factor
	double zoom = 1.0;

	// Displays the demo in full-screen using the display's native resolution
	public DemoScrolling() {
		super(0, 0, true);
	}

	// Create a default-sized application
	public DemoScrolling(boolean onAndroid) {
		super(onAndroid);
	}

	public void onInit() {
		setTitle("Scrolling demo, mui 2013");
		Logger.log("Press s or w for zooming in or out");

		toDraw.add(new Sky());

		// Some pipe for a nice 'Mario' like atmosphere
		toDraw.add(new Pipe(100, 60));
		toDraw.add(new Pipe(600, 80));
		toDraw.add(new Pipe(1500, 90));

		// First layer (bottom)
		for (int i = 0; i < 60; i++) {
			toDraw.add(new Brick(-500 + 64 * i, 20));
		}

		// Coins
		for (int i = 0; i < 5; i++) {
			toDraw.add(new Coin(250 + 64 * i, 120));
		}

		// Some clouds
		toDraw.add(new Cloud(100, 450));
		toDraw.add(new Cloud(250, 600));
		toDraw.add(new Cloud(450, 250));
		toDraw.add(new Cloud(700, 350));
		toDraw.add(new Cloud(1000, 370));		
	};

	float travelSpeed = 2f;
	boolean scrolling = true;

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		scrolling = !scrolling;
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		g.zoom((float) zoom);

		/**
		 * Handle input (not done using the inherited method onKeyDown) because
		 * we don't want to release the key for the zoom to occur
		 */
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			zoom += 0.02;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			zoom -= 0.02;

		// We scroll only if the camera is not too big
		if (scrolling && g.getCamera().viewportWidth < 600) {
			// If we've reached one of the borders, change the speed's direction
			if (g.getCamera().position.x > 600 || g.getCamera().position.x < 200){				
					travelSpeed *= -1;
			}

			// Travel the camera left-right
			g.scroll(travelSpeed, 0);
		}

		//
		// Draw all objects
		for (DrawableObject obj : toDraw) {
			obj.draw(g);
		}

		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}

	public static void main(String[] args) {
		new DemoScrolling(false);
	}
}
