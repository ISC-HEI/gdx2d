package ch.hevs.gdx2d.demos.physics.joints;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * A demo on how to build chains with rope joints with box2d
 *
 * @author Pierre-André Mudry, mui
 * @author Thierry Hischier, hit
 * @version 1.2
 * @date 2014
 */
public class DemoRopeJoint extends PortableApplication {
	World world = PhysicsWorld.getInstance();

	// Contains all the objects that will be simulated
	DebugRenderer debugRenderer;

	public static void main(String args[]) {
		new DemoRopeJoint();
	}

	@Override
	public void onInit() {
		setTitle("Rope joints simulation, hit/mui 2014");
		final int w = getWindowWidth(), h = getWindowHeight();

		debugRenderer = new DebugRenderer();

		// The amount of rope segments
		int nSegments = 10;

		/**
		 * The circle in the middle of the screen for the origin
		 */
		PhysicsStaticBox p = new PhysicsStaticBox("", new Vector2(w / 2.0f, h / 1.4f), 20, 20);

		// The first object in the chain is the static circle
		Body prevBody = p.getBody();

		final int segmentLength = 20;
		final int spaceBetweenSegments = 10;

		// Build the body and the RevoluteJointDef for the chain elements.
		for (int i = 0; i < nSegments; i++) {
			// Create a rope segment element
			PhysicsBox box = new PhysicsBox("", new Vector2(w / 2 + i * (segmentLength + spaceBetweenSegments), h / 1.4f),
					segmentLength,
					4);

			// Connect each element with the element before
			Vector2 anchorA = prevBody.getLocalCenter().add(0, 0);

			// The anchor point should be outside the object here to make it nice
			Vector2 anchorB = box.getBodyLocalCenter().add(-(segmentLength + spaceBetweenSegments), 0);

			// Create a joint between the previous and current object
			RevoluteJointDef revoluteJointDefRope = new RevoluteJointDef();
			revoluteJointDefRope.bodyA = prevBody;
			revoluteJointDefRope.bodyB = box.getBody();
			revoluteJointDefRope.collideConnected = false;
			revoluteJointDefRope.localAnchorA.set(PhysicsConstants.coordPixelsToMeters(anchorA));
			revoluteJointDefRope.localAnchorB.set(PhysicsConstants.coordPixelsToMeters(anchorB));
			world.createJoint(revoluteJointDefRope);

			// Create the joint and set the previous body to the created one
			prevBody = box.getBody();
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		debugRenderer.render(world, g.getCamera().combined);

		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}
}
