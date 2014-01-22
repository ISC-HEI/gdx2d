package hevs.gdx2d.demos.physics.rocket;

import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Spaceship implements DrawableObject {
	PhysicsBox box = null;
	
	// Motor related
	boolean thrustLeft = false, thrustRight = false;
	float thrustUp = 0;
	
	// Drawing related
	final static BitmapImage shipImage = new BitmapImage("data/images/rocket_128.png");
	final static BitmapImage flameImage = new BitmapImage("data/images/flame.png");
	
	final float MAX_THRUST = 2f;
	final float MAX_TORQUE = 0.08f;

	public Spaceship(Vector2 position) {
		box = new PhysicsBox("ship", position, 30, 30, 0.61f, 0.2f, 0.2f);
		box.setBodyAngularDamping(0.4f);
		box.setBodyLinearDamping(0.2f);
	}

	@Override
	public void draw(GdxGraphics g) {
		// Make the ship turn if required
		if (thrustLeft)
			box.applyBodyTorque(MAX_TORQUE, true);
		
		if (thrustRight)
			box.applyBodyTorque(-MAX_TORQUE, true);

		// Let's move the ship with a force
		box.applyBodyForceToCenter((float) Math.cos(box.getBodyAngle()) * thrustUp,
				(float) Math.sin(box.getBodyAngle()) * thrustUp, true);

		// Draws the ship
		Vector2 pos = box.getBodyPosition();
		g.drawTransformedPicture(pos.x,	pos.y, box.getBodyAngleDeg(), .5f, shipImage);
		
		// Draw the flame
		// FIXME this is not working
		//if(thrustUp > 1000)			
//		g.drawTransformedPicture(
//				box.body.getPosition().x+50,
//				box.body.getPosition().y,
////				box.body.getPosition().x,
////				box.body.getPosition().y,
//				box.body.getAngle() * RAD2DEG, .2f*thrustUp/100000.0f, flameImage);
	}

}
