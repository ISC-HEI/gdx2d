package ch.hevs.gdx2d.demos.physics.joints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.colors.Palette;
import hevs.gdx2d.components.physics.primitives.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

import java.util.LinkedList;
import java.util.Random;

/**
 * A box that will be used as a rotor for the demo
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class Rotor extends PhysicsBox implements DrawableObject {
	static final BitmapImage screw = new BitmapImage("data/images/screw.png");
	private final float w, h;

	public Rotor(String name, Vector2 position, float width, float height) {
		super(name, position, width, height);
		w = width;
		h = height;
	}

	/**
	 * Used for drawing the rotor
	 */
	@Override
	public void draw(GdxGraphics g) {
		float x = getBodyPosition().x;
		float y = getBodyPosition().y;
		float angle = getBodyAngleDeg();
		g.drawFilledRectangle(x, y, w, h, angle, Palette.pastel1[1]);
		g.drawTransformedPicture(x, y, angle, 0.2f, screw);
	}
}

/**
 * A demo on how to use PhysicsMotor with anchor points
 *
 * @author Pierre-André Mudry, mui
 * @version 1.3
 */
public class DemoMixer extends PortableApplication {
	final static Random rnd = new Random();
	// The number of balls generated
	final int N_PARTICLES = 100;
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	Body box1;
	Body box2;
	PhysicsMotor physicMotor;
	// Linked List to store all particles
	LinkedList<CircleParticle> particles = new LinkedList<CircleParticle>();
	Random random;
	float width, height;
	Rotor rotor;

	public static void main(String args[]) {
		new DemoMixer();
	}

	@Override
	public void onInit() {
		setTitle("Particle mixer, mui 2014");
		Logger.log("Press left mouse button to enable/disable the motor.");

		// A renderer that displays physics objects things simply
		debugRenderer = new DebugRenderer();
		random = new Random();

		width = getWindowWidth();
		height = getWindowHeight();

		// Create fixed boundaries at each side of the screen
		new PhysicsScreenBoundaries(width, height);

		final PhysicsStaticBox stator = new PhysicsStaticBox("stator",
				new Vector2(width / 2, height / 2), 5, 5);
		box1 = stator.getBody();

		/**
		 * Create the stator (moving) part. It is also located in the center of
		 * the frame. It is not static, as it can rotate
		 */
		rotor = new Rotor("rotor", new Vector2(width / 2, height / 2), width * 0.85f, height * 0.02f);
		box2 = rotor.getBody();

		/**
		 * Create a motor that will make the moving box move and rotate around
		 * the anchor point (which is the center of the first box)
		 */
		physicMotor = new PhysicsMotor(box1, box2, box1.getWorldCenter());

		// Initialize the motor with a speed and torque
		physicMotor.initializeMotor(1.0f, 8000.0f, false);

		createParticles();
	}

	/**
	 * Generate random particles to fill the screen
	 */
	private void createParticles() {
		for (int i = 0; i < N_PARTICLES; i++) {
			float x = width / 5 + random.nextFloat() * (width - 2 * width / 5);
			float y = random.nextFloat() * height;
			final Vector2 position = new Vector2(x, y);
			Color c = rnd.nextBoolean() == true ? Color.DARK_GRAY
					: Color.LIGHT_GRAY;
			final CircleParticle p = new CircleParticle(position,
					10 + rnd.nextInt(5), c, 0.002f, 1f);
			particles.add(p);
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Draws the particles
		for (CircleParticle particle : particles) {
			particle.draw(g);
		}

		rotor.draw(g);

		// Render the scene using the debug renderer
		// debugRenderer.render(world, g.getCamera().combined);
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawString(5, height - 20, "Left Mouse button: Motor ON/OFF",
				Align.left);
		g.drawString(5, height - 40,
				"Motor is " + (physicMotor.isMotorEnabled() ? "ON" : "OFF"),
				Align.left);

		g.drawSchoolLogoUpperRight();
		g.drawFPS(Color.CYAN);
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		if (button == Input.Buttons.LEFT) {
			physicMotor.enableMotor(!physicMotor.isMotorEnabled());
		}
	}
}
