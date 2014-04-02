package hevs.gdx2d.demos.physics.joints;

import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.PhysicsStaticBox;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A demo on how to use PhysicsMotor (anchor points)
 * 
 * @author Pierre-André Mudry, mui
 * @version 1.2
 */
public class DemoMixer extends PortableApplication {
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	Body box1;
	Body box2;
	PhysicsMotor physicMotor;

	// Linked List to store all particles
	LinkedList<CircleParticle> particles = new LinkedList<CircleParticle>();
	Random random;
	float width, height;
	boolean motorOn = false;

	// The rate at which the balls are generated
	int N_PARTICLES = 150;

	public DemoMixer(boolean onAndroid) {
		super(onAndroid);
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
				new Vector2(width / 2, height / 2), 20, 20);

		box1 = stator.getBody();

		// Create the windmill wing. It is also located in the center of the
		// frame
		// This is is not static, as it can rotate
		final PhysicsBox rotor = new PhysicsBox("rotor", new Vector2(width / 2,
				height / 2), 240, 6);

		box2 = rotor.getBody();

		/**
		 * Create a motor that will make the moving box move and rotate around
		 * the anchor point (which is the center of the first box)
		 */
		physicMotor = new PhysicsMotor(box1, box2, box1.getWorldCenter());

		// Initialize the motor with a speed and torque
		physicMotor.initializeMotor(1.0f, 800000.0f, false);

		createParticles();
	}

	Random rnd = new Random();

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
			final CircleParticle newParticle = new CircleParticle(position, 10,
					c, 0.02f, 60f);
			particles.add(newParticle);
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Draws the particles
		for (CircleParticle particle : particles) {
			particle.draw(g);
		}

		// Render the scene using the debug renderer
		debugRenderer.render(world, g.getCamera().combined);

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawString(width - 5, 60, "Left Mouse button: Motor ON/OFF",
				HAlignment.RIGHT);
		g.drawString(width - 5, 20, "Motor is " + (motorOn ? "ON" : "OFF"),
				HAlignment.RIGHT);

		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		if (button == Input.Buttons.LEFT) {
			physicMotor.enableMotor(!physicMotor.isMotorEnabled());
		}
	}

	public static void main(String args[]) {
		new DemoMixer(false);
	}
}
