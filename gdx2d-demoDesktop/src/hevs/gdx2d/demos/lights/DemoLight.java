package hevs.gdx2d.demos.lights;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import hevs.gdx2d.components.physics.primitives.PhysicsCircle;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * Demonstrates the usage of shadows and lights in GDX2D
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.05
 */
public class DemoLight extends PortableApplication {

	// Physics-related attributes
	RayHandler rayHandler;
	World world;
	DebugRenderer debugRenderer;
	ArrayList<PhysicsCircle> list = new ArrayList<PhysicsCircle>();

	// Light related attributes
	PointLight p;
	ConeLight c1, c2;
	int width, height;
	private boolean firstRun = true;

	public static void main(String[] args) {
		new DemoLight();
	}

	@Override
	public void onInit() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		setTitle("Shadows and lights, mui 2013");

		Gdx.app.log("[DemoLights]", "Left click to create a new light");
		Gdx.app.log("[DemoLights]", "Right click disables normal light");

		world = PhysicsWorld.getInstance();
		world.setGravity(new Vector2(0, 0));

		// The light manager
		rayHandler = new RayHandler(world);

		// This is the light controlled by the mouse click and drag
		p = new PointLight(rayHandler, 200, Color.YELLOW, 10,
				width / 2 - 50,
				height / 2 + 150);

		p.setDistance(10);
		p.setColor(new Color(0.9f, 0f, 0.9f, 0.9f));
		p.setActive(false);

		p.setSoft(true);

		// The two light cones that are always present
		c1 = new ConeLight(rayHandler, 300, new Color(1, 1, 1, 0.92f), 14,
				0.2f * width * PhysicsConstants.PIXEL_TO_METERS,
				0.9f * height * PhysicsConstants.PIXEL_TO_METERS,
				270, 40);
		c2 = new ConeLight(rayHandler, 300, new Color(0.1f, 0.1f, 1, 0.92f), 14,
				0.8f * width * PhysicsConstants.PIXEL_TO_METERS,
				0.9f * height * PhysicsConstants.PIXEL_TO_METERS,
				270, 40);

		rayHandler.setCulling(true);
		rayHandler.setShadows(true);
		rayHandler.setBlur(true);
		rayHandler.setAmbientLight(0.4f);

		new PhysicsScreenBoundaries(width, height);
		createRandomCircles(10);

		debugRenderer = new DebugRenderer();
	}

	/**
	 * Creates n physics objects that will then cast shadows
	 *
	 * @param n The number of physics object to create
	 */
	protected void createRandomCircles(int n) {

		Random r = new Random();

		while (n > 0) {
			Vector2 position = new Vector2((float) (width * Math.random()), (float) (height * Math.random()));
			PhysicsCircle circle = new PhysicsCircle("circle", position, 10, 1.2f, 1, 0.01f);
			circle.setBodyLinearVelocity(r.nextFloat() * 3, r.nextFloat() * 3);
			n--;

			// Only add the body, the rest is useless
			list.add(circle);
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (firstRun) {
			OrthographicCamera other = new OrthographicCamera();
			other.setToOrtho(false);
			Matrix4 cmb = other.combined.scl(PhysicsConstants.METERS_TO_PIXELS);
			rayHandler.setCombinedMatrix(cmb);
			firstRun = false;
		}

		g.clear();

		// Render the blue spheres
		for (PhysicsCircle b : list) {
			final Vector2 pos = b.getBodyPosition();
			g.drawFilledCircle(pos.x, pos.y, 12, Color.MAGENTA);
		}

		// Render the lights
		g.beginCustomRendering();
		rayHandler.updateAndRender();
		g.endCustomRendering();

		// Update the physics
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawSchoolLogo();
		g.drawFPS();
	}

	@Override
	public void onClick(int x, int y, int button) {
		if (button == Input.Buttons.RIGHT) {
			c1.setActive(!c1.isActive());
			c2.setActive(!c2.isActive());
		}

		// Turn on the light when pushing button
		if (button == Input.Buttons.LEFT) {
			p.setActive(true);
			p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS);
		}
	}

	@Override
	public void onDrag(int x, int y) {
		p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS);
	}

	@Override
	public void onRelease(int x, int y, int button) {
		// Turn off the light when releasing button
		p.setActive(false);
	}
}
