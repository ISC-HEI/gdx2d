package hevs.gdx2d.demos.physics.mouse_interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.components.physics.PhysicsStaticBox;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.util.Random;

/**
 * A demo on how to use the mouse to move objects with box2d
 *
 * @author Pierre-André Mudry, mui 2013
 * @version 1.1
 */
public class DemoPhysicsMouse extends PortableApplication {
	protected Body hitBody = null;
	/**
	 * ground body to connect the mouse joint to *
	 */
	protected Body groundBody;
	/**
	 * our mouse joint *
	 */
	protected MouseJoint mouseJoint = null;
	World world = PhysicsWorld.getInstance();
	// Contains all the objects that will be simulated
	DebugRenderer debugRenderer;
	/**
	 * we instantiate this vector and the callback here so we don't irritate the
	 * GC
	 */
	Vector2 testPoint = new Vector2();
	Vector2 target = new Vector2();
	// Called for AABB lookup
	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture(Fixture fixture) {
			// if the hit point is inside the fixture of the body we report it
			if (fixture.testPoint(testPoint.x, testPoint.y)) {
				hitBody = fixture.getBody();
				return false;
			} else
				return true;
		}
	};

	public DemoPhysicsMouse() {
		super();
	}

	public DemoPhysicsMouse(int w, int h) {
		super(w, h);
	}

	public static void main(String[] args) {
		new DemoPhysicsMouse(800, 500);
	}

	@Override
	public void onInit() {
		setTitle("Mouse interactions in box2d, mui 2013");

		// we also need an invisible zero size ground body
		// to which we can connect the mouse joint
		BodyDef bodyDef = new BodyDef();
		groundBody = world.createBody(bodyDef);

		new PhysicsScreenBoundaries(this.getWindowWidth(), this.getWindowHeight());
		new PhysicsStaticBox("wall in the middle", new Vector2(getWindowWidth() / 2, 50), 20, 100);

		// Build some boxes
		for (int i = 0; i < 10; i++) {
			Random r = new Random();
			new PhysicsBox("box", new Vector2(100 + r.nextInt(100), 200 + r.nextInt(100)),
					16, r.nextInt(80) + 40, 1000f, 0.2f, 0.2f);
		}

		// Build the ball
		new PhysicsCircle("ball", new Vector2(100, 500), 20);

		debugRenderer = new DebugRenderer();
		debugRenderer.setDrawJoints(true);
		debugRenderer.setDrawContacts(true);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		debugRenderer.render(world, g.getCamera().combined);
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		g.drawFPS();
		g.drawSchoolLogoUpperRight();

	}

	@Override
	public void onDrag(int x, int y) {
		// if a mouse joint exists we simply update
		// the target of the joint based on the new
		// mouse coordinates
		if (mouseJoint != null) {
			mouseJoint.setTarget(target.set(x, y).scl(PhysicsConstants.PIXEL_TO_METERS));
		}
		return;
	}

	@Override
	public void onRelease(int x, int y, int button) {
		// if a mouse joint exists we simply destroy it
		if (mouseJoint != null) {
			world.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
	}

	public void onClick(int x, int y, int button) {
		// translate the mouse coordinates to world coordinates
		// cam.unproject(testPoint.set(x, y, 0));
		testPoint.x = x;
		testPoint.y = y;

		testPoint.scl(PhysicsConstants.PIXEL_TO_METERS);

		// ask the world which bodies are within the given
		// bounding box around the mouse pointer
		hitBody = null;

		world.QueryAABB(callback, testPoint.x - 5, testPoint.y - 5, testPoint.x + 5, testPoint.y + 5);

		// ignore kinematic bodies, they don't work with the mouse joint
		if (hitBody == null || hitBody.getType() == BodyType.KinematicBody)
			return;

		// if we hit something we create a new mouse joint
		// and attach it to the hit body.
		if (hitBody != null) {
			MouseJointDef def = new MouseJointDef();
			def.bodyA = groundBody;
			def.bodyB = hitBody;
			def.collideConnected = true;
			def.dampingRatio = 0.8f;
			def.target.set(testPoint.x, testPoint.y);
			def.maxForce = 100.0f * hitBody.getMass();

			mouseJoint = (MouseJoint) world.createJoint(def);
			hitBody.setAwake(true);
		}
		return;
	}

	@Override
	public void onDispose() {
		super.onDispose();
		debugRenderer.dispose();
	}
}
