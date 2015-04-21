package hevs.gdx2d.demos.physics.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * Simple collision handling in box2d
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSlowCollisions extends PortableApplication {
	World world = PhysicsWorld.getInstance();
	DebugRenderer dbgRenderer;

	BumpyBall b1, b2;

	public DemoSlowCollisions(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String args[]) {
		new DemoSlowCollisions(false);
	}

	@Override
	public void onInit() {
		dbgRenderer = new DebugRenderer();
		setTitle("Slow collisions for box2d, mui 2013");

		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());

		// A BumpyBall has redefined its collision method.
		b1 = new BumpyBall("ball 1", new Vector2(150, 250), 30);
		b2 = new BumpyBall("ball 2", new Vector2(350, 250), 30);

		// Indicate that the ball should be informed for collisions
		b1.enableCollisionListener();
		b2.enableCollisionListener();
		b1.setBodyLinearVelocity(1, 0);
		b2.setBodyLinearVelocity(-1, 0);

		world.setGravity(Vector2.Zero);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		b1.draw(g);
		b2.draw(g);

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}
}
