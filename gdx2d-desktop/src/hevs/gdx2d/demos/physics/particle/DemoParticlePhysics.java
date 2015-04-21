package hevs.gdx2d.demos.physics.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.util.Iterator;
import java.util.Random;

/**
 * Demo for particle physics. There are no collisions in the physics and
 * no boundaries.
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.2
 */
public class DemoParticlePhysics extends PortableApplication {
	static final Random rand = new Random();
	public final int MAX_AGE = 35;
	public int CREATION_RATE = 3;
	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	// Particle creation related
	boolean mouseActive = false;
	Vector2 position;

	public DemoParticlePhysics(boolean onAndroid) {
		super(onAndroid);
	}

	public DemoParticlePhysics(boolean onAndroid, int x, int y) {
		super(onAndroid, x, y);
	}

	public static void main(String args[]) {
		new DemoParticlePhysics(false, 1000, 600);
	}

	@Override
	public void onInit() {
		setTitle("Particle physics, mui 2013");
		dbgRenderer = new DebugRenderer();
		world.setGravity(new Vector2(0, -0.6f));
		Gdx.app.log("[DemoParticlePhysics]", "Click on screen to create particles");
		Gdx.app.log("[DemoParticlePhysics]", "a/s change the creation rate of particles");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		Iterator<Body> it = world.getBodies();

		while (it.hasNext()) {
			Body p = it.next();

			if (p.getUserData() instanceof Particle) {
				Particle particle = (Particle) p.getUserData();
				particle.step();
				particle.render(g);

				if (particle.shouldbeDestroyed()) {
					particle.destroy();
				}
			}
		}

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		if (mouseActive)
			createParticles();

		g.drawSchoolLogo();
		g.drawFPS();
	}

	void createParticles() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Particle c = new Particle(position, 10, MAX_AGE + rand.nextInt(MAX_AGE / 2));

			// Apply a vertical force with some random horizontal component
			Vector2 force = new Vector2();
			force.x = rand.nextFloat() * 0.00095f * (rand.nextBoolean() == true ? -1 : 1);
			force.y = rand.nextFloat() * 0.00095f * (rand.nextBoolean() == true ? -1 : 1);
			c.applyBodyLinearImpulse(force, position, true);
		}
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		position.x = x;
		position.y = y;
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouseActive = true;
		position = new Vector2(x, y);
	}

	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		position.x = x;
		position.y = y;
		mouseActive = false;
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		if (keycode == Input.Keys.A) {
			CREATION_RATE++;
		}
		if (keycode == Input.Keys.S) {
			CREATION_RATE = CREATION_RATE > 1 ? CREATION_RATE - 1 : CREATION_RATE;
		}
		Gdx.app.log("[DemoParticlePhysics]", "Creation rate is now " + CREATION_RATE);
	}
}
