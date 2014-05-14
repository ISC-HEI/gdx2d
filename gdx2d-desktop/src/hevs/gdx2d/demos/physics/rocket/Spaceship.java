package hevs.gdx2d.demos.physics.rocket;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;

/**
 * A simple spaceship simulated using a {@link PhysicsBox}.
 * 
 * The spaceship can move and turn. A nice image is used to draw the spaceship.
 * 
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
public class Spaceship implements DrawableObject {
	protected AbstractPhysicsObject box;

	// Motor related
	protected boolean thrustLeft = false, thrustRight = false;
	protected float thrustUp = 0;

	static final float MAX_THRUST = 2f;
	static final float MAX_TORQUE = 0.08f;

	// Drawing related
	protected static BitmapImage shipImage, flameImage;

	public Spaceship(Vector2 position) {
		box = new PhysicsBox("ship", position, 30, 30, 0.61f, 0.2f, 0.2f);
		box.setBodyAngularDamping(0.4f);
		box.setBodyLinearDamping(0.2f);
		// The front of the rocket is up by default
		box.getBody().setTransform(
				PhysicsConstants.coordPixelsToMeters(position),
				(float) Math.toRadians(90));

		// The spaceship image
		shipImage = new BitmapImage("data/images/rocket_128.png");
		flameImage = new BitmapImage("data/images/flame.png");
	}

	@Override
	public void draw(GdxGraphics g) {
		// Make the ship turn if required
		if (thrustLeft)
			box.applyBodyTorque(MAX_TORQUE, true);

		if (thrustRight)
			box.applyBodyTorque(-MAX_TORQUE, true);

		// Let's move the ship with a force
		box.applyBodyForceToCenter((float) Math.cos(box.getBodyAngle())
				* thrustUp, (float) Math.sin(box.getBodyAngle()) * thrustUp,
				true);

		Vector2 pos = box.getBodyPosition();
		if (thrustUp > 0) {
			// Draw the flame
			Vector2 x = box.getBody().getWorldPoint(new Vector2(-55.0f, 0.0f));
			Vector2 flameCenter = x.add(pos); // rotation matrix

			// TODO: change the flame height depending on the spaceship thrust
			g.drawTransformedPicture(flameCenter.x, flameCenter.y,
					box.getBodyAngleDeg(), .3f, flameImage);
		}

		// Draws the ship
		g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg(), .5f,
				shipImage);
	}

}
