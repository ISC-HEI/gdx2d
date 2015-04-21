package hevs.gdx2d.demos.lights;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * A simple demo for the gdx2light integration (lights and shadows)
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
public class DemoRotateLight extends PortableApplication {

	protected final float radius = 0.3f;
	// Various attributes used for light animation
	protected int width, height;
	protected float angle;
	protected boolean clicked = false;
	protected Vector2 rotationCenter = new Vector2();
	// The physics world (because light uses physics for positions)
	World world;
	// This object manages the different lights of the scene
	RayHandler rayHandler;
	// A single light
	PointLight p;
	float hue = 0.3f;
	float direction = 0.001f;
	// The objects located in the middle of the screen that will cast shadows
	PhysicsCircle circle1, circle2, circle3;

	public DemoRotateLight(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoRotateLight(false);
	}

	@Override
	public void onInit() {
		this.setTitle("Rotate light demo, mui 2013");
		Gdx.app.log("[DemoLights]", "Left click to move the light source");

		world = PhysicsWorld.getInstance();

		// We don't need gravity
		world.setGravity(new Vector2(0, 0));

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		rayHandler = new RayHandler(world);

		// The light has a position, a manager, a color and an intensity
		p = new PointLight(rayHandler, 800, Color.LIGHT_GRAY, 8,
				(width / 2 - 30) * PhysicsConstants.PIXEL_TO_METERS,
				(height / 2 + 120) * PhysicsConstants.PIXEL_TO_METERS);

		p.setDistance(8f);
		p.setSoft(true);
		p.setColor(ColorUtils.hsvToColor(hue, 0.8f, 1.0f));

		// Creates the objects that will cast shadows
		circle1 = new PhysicsCircle("circle", new Vector2(width / 2, height / 2), 10, 1.2f, 1, 0.01f);
		circle2 = new PhysicsCircle("circle", new Vector2(width / 2 - width / 10, height / 2), 10, 1.2f, 1, 0.01f);
		circle3 = new PhysicsCircle("circle", new Vector2(width / 2 + width / 10, height / 2), 10, 1.2f, 1, 0.01f);

		// This has to be done once because the physics has other
		// coordinates than pixels
		OrthographicCamera other = new OrthographicCamera();
		other.setToOrtho(false);
		other.combined.scl(PhysicsConstants.METERS_TO_PIXELS);
		rayHandler.setCombinedMatrix(other.combined);

		rotationCenter.set(width / 2, height * 0.65f);
		rotationCenter.scl(PhysicsConstants.PIXEL_TO_METERS);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Render the blue objects
		Vector2 pos = circle1.getBodyPosition();
		g.drawFilledCircle(pos.x, pos.y, 12, Color.BLUE);
		pos = circle2.getBodyPosition();
		g.drawFilledCircle(pos.x, pos.y, 12, Color.BLUE);
		pos = circle3.getBodyPosition();
		g.drawFilledCircle(pos.x, pos.y, 12, Color.BLUE);

		// Make the light turn
		angle += 0.05;

		// Change the light's color
		if (hue > 0.99f || hue <= 0.01) {
			direction *= -1;
		}
		hue += direction;
		p.setColor(ColorUtils.hsvToColor(hue, 0.8f, 1.0f));

		// If the user plays with its mouse, move the light accordingly. When done, make it turn
		if (clicked)
			p.setPosition((float) (rotationCenter.x), (float) (rotationCenter.y));
		else
			p.setPosition((float) (rotationCenter.x - radius + radius * Math.cos(angle)), (float) (rotationCenter.y + radius * Math.sin(angle)));

		g.resetRenderingMode();

		// Update the light rays
		rayHandler.updateAndRender();

		// Update the physics
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawSchoolLogo();
		g.drawFPS();
	}

	@Override
	public void onClick(int x, int y, int button) {
		if (button == Input.Buttons.LEFT) {
			rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS);
			clicked = true;
		}
	}

	@Override
	public void onDrag(int x, int y) {
		rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS);
	}

	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		if (clicked) {
			angle = 0;
			clicked = false;
		}
	}
}
