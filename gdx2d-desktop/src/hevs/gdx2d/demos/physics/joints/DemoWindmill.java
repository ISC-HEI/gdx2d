package hevs.gdx2d.demos.physics.joints;

import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.PhysicsStaticBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A demo on how to use PhysicsMotor (anchor points)
 * 
 * <p>
 * Based on ex 5.9 from the Nature of code book
 * </p>
 * 
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The
 *      nature of code example</a>
 * @author Thierry Hischier, hit 2014
 * @version 1.1
 */
public class DemoWindmill extends PortableApplication {
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;

	Body box1;
	Body box2;
	PhysicsMotor physicMotor;

	// Linked List to store all particles
	LinkedList<CircleParticle> particles = new LinkedList<CircleParticle>();

	Random random;

	float width, height;

	int time = 0;
	boolean motorOn = false;

	// The rate at which the balls are generated
	int GENERATION_RATE = 2;
	boolean generate = false;

	public DemoWindmill(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		setTitle("Windmill simulation, hit 2014");
		System.out.println("Press left mouse button to enable/disable the motor");

		// A renderer that displays physics objects things simply
		debugRenderer = new DebugRenderer();
		random = new Random();

		width = getWindowWidth();
		height = getWindowHeight();

		// Create PhysicStaticBox where the windmill will be fixed. It is
		// located in the center of the frame
		PhysicsStaticBox staticBox = new PhysicsStaticBox("box1", 
									new Vector2(getWindowWidth() / 2,
												getWindowHeight() / 2), 10, 40);
		
		box1 = staticBox.getBody();

		// Create the windmill wing. It is also located in the center of the frame
		// This is is not static, as it can rotate
		PhysicsBox movingBox = new PhysicsBox("box2", new Vector2(getWindowWidth() / 2, getWindowHeight() / 2), 120, 10);
		
		box2 = movingBox.getBody();

		/**
		 * Create a motor that will make the moving box move and rotate
		 * around the anchor point (which is the center of the first box)
		 */
		physicMotor = new PhysicsMotor(box1, box2, box1.getWorldCenter());

		// Initialize the motor with a speed and torque
		physicMotor.initializeMotor(2.0f, 200.0f, false);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Draw all the added particles and destroy the particles which are no
		// longer in the visible
		for (Iterator<CircleParticle> iter = particles.iterator(); iter.hasNext();) {
			CircleParticle particle = iter.next();
			particle.draw(g);

			Vector2 p = particle.getBodyPosition();

			// If a particle is not visible anymore, it should be destroyed
			if (p.y > height || p.y < 0 || p.x > width || p.x < 0) { 
				particle.destroy();
				// Remove the particle from the collection as well
				iter.remove();
			}
		}

		/**
		 * Generate new circleParticles if the right button of the mouse 
		 * was pressed earlier
		 */
		if (generate) {
			for (int i = 0; i < GENERATION_RATE; i++) {
				float x = width / 5 + random.nextFloat()
						* (width - 2 * width / 5);
				float y = height * 0.9f + random.nextInt(30);
				final Vector2 position = new Vector2(x, y);
				final CircleParticle newParticle = new CircleParticle("a particle", position, 5);
				particles.add(newParticle);
			}
		} else {
			/**
			 * Add new particles from time to time 
			 */
			if (time % 25 == 0) {
				int x = random.nextInt(100);
				Vector2 position = new Vector2(((getWindowWidth() / 2) - 50) + x, getWindowHeight() - 5);
				CircleParticle newParticle = new CircleParticle("a particle", position, 5);				
				particles.add(newParticle);
			}
		}

		/** 
		 * Render the physics and draw the logo, fps information and the status
		 * of the motor
		 */
		debugRenderer.render(world, g.getCamera().combined);
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawSchoolLogoUpperRight();
		g.drawFPS();

		g.setColor(Color.WHITE);		
		g.drawString(width - 250, 60, "Left Mouse button: Motor ON/OFF");
		g.drawString(width - 290, 40, "Right Mouse button: Generate particles");
		
		if (motorOn) {
			g.drawString(width - 100, 20, "Motor is ON");
		} else {
			g.drawString(width - 100, 20, "Motor is OFF");
		}

		// Increment the timer variable
		time++;
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		if (button == Input.Buttons.LEFT) {
			if (motorOn) {
				motorOn = false;
				physicMotor.enableMotor(motorOn);
			} else {
				motorOn = true;
				physicMotor.enableMotor(motorOn);
			}
		} else if (button == Input.Buttons.RIGHT) {
			generate = true;
		}
	}

	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		generate = false;
	}

	public static void main(String args[]) {
		new DemoWindmill(false);
	}
}
