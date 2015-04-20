package hevs.gdx2d.demos.physics.car.components;

import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * A top-view physics car Original example adapted from
 * http://www.level1gamer.com/2012/10/24/top-down-car-using-libgdx-and-box2d/
 * 
 * @author Pierre-André Mudry
 * @version 1.1
 */
public class Car implements DrawableObject {

	/**
	 * Use this to control the car
	 */
	public boolean steer_left, steer_right;
	public boolean accelerate, brake;
	
	/**
	 * How the car energy is dissipated when no longer
	 * accelerating. 0 would not brake the car at all
	 * and 1 will make it stop very quickly.
	 */
	float slowingFactor = 0.5f;

	public final int wheelWidth = 16, wheelHeight = 60; 

	protected float maxSteerAngle, maxSpeed, power, wheelAngle;
	protected PhysicsBox carbox; 
	protected List<Wheel> wheels;

	/**
	 * Builds a physically "accurate" car
	 * 
	 * @param width
	 *            The width of the body of the car
	 * @param length
	 *            The length of the body of the car
	 * @param position
	 *            The start position of the car
	 * @param angle
	 *            The starting angle of the car, in radians
	 * @param power
	 *            The maximum horse power of the car
	 * @param maxSteerAngle
	 *            The maximum steering angle for the wheels, in degrees
	 * @param maxSpeed
	 *            The maximum speed for the car
	 */
	public Car(float width, float length, Vector2 position, float angle,
			float power, float maxSteerAngle, float maxSpeed) {

		this.maxSteerAngle = maxSteerAngle;
		this.maxSpeed = maxSpeed;
		this.power = power;

		carbox = new PhysicsBox("carCenter", position, width, length, angle);
		carbox.setCollisionGroup(-1);
		
		// Initialize wheels
		this.wheels = new ArrayList<Wheel>();

		Vector2 wheelOffset = new Vector2(25, 35);
		
		// Topleft wheel
		this.wheels.add(new Wheel(this, wheelOffset.cpy().scl(-1, -1), wheelWidth, wheelHeight, true, true));
		// Topright wheel
		this.wheels.add(new Wheel(this, wheelOffset.cpy().scl(1, -1), wheelWidth, wheelHeight, true, true));
		// Backleft wheel
		this.wheels.add(new Wheel(this, wheelOffset.cpy().scl(-1, 1), wheelWidth, wheelHeight, false, false));
		// Backright wheel
		this.wheels.add(new Wheel(this, wheelOffset.cpy().scl(1, 1), wheelWidth, wheelHeight, false, false));
	}

	/**
	 * @return car's velocity vector relative to the car
	 */
	protected Vector2 getLocalVelocity() {
		//return carbox.getBodyLocalVector(carbox.getBodyLinearVelocityFromLocalPoint(new Vector2(0,0)));//).getLocalVector(carBox.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
		return carbox.getBody().getLocalVector(carbox.getBody().getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
	}

	protected List<Wheel> getRevolvingWheels() {
		List<Wheel> revolvingWheels = new ArrayList<Wheel>();
		for (Wheel wheel : this.wheels) {
			if (wheel.revolving)
				revolvingWheels.add(wheel);
		}
		return revolvingWheels;
	}

	protected List<Wheel> getPoweredWheels() {
		List<Wheel> poweredWheels = new ArrayList<Wheel>();
		for (Wheel wheel : this.wheels) {
			if (wheel.powered)
				poweredWheels.add(wheel);
		}
		return poweredWheels;
	}

	/**
	 * @return The current speed of the car, in km/h
	 */
	public float getSpeedKMH() {
		Vector2 velocity = carbox.getBodyLinearVelocity();
		float len = velocity.len();
		return (len / 1000) * 3600;
	}

	/**
	 * Sets the speed of the car in kilometers per hour
	 * 
	 * @param speed
	 *            The speed of the car, in km/h
	 */
	public void setSpeed(float speed) {
		/*
		 * speed - speed in kilometers per hour
		 */
		Vector2 velocity = carbox.getBodyLinearVelocity();
		velocity = velocity.nor();
		velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f),
				velocity.y * ((speed * 1000.0f) / 3600.0f));
		carbox.setBodyLinearVelocity(velocity);
	}

	/**
	 * Updates some physical parameters which are specific to a car
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {

		// 1. KILL SIDEWAYS VELOCITY
		for (Wheel wheel : wheels) {
			wheel.killSidewaysVelocity();
		}

		// 2. SET WHEEL ANGLE
		// calculate the change in wheel's angle for this update
		float incr = (this.maxSteerAngle) * deltaTime * 5;

		if (steer_left) {
			/**
			 * Augment angle and check max steering
			 */
			this.wheelAngle = Math.min(Math.max(this.wheelAngle, 0) + incr,
					this.maxSteerAngle); 
		} else if (steer_right) {
			/**
			 * Diminish angle and check min steering
			 */
			this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) - incr,
					-this.maxSteerAngle); 
		} else {
			this.wheelAngle = 0;
		}

		// update revolving wheels
		for (Wheel wheel : this.getRevolvingWheels()) {
			wheel.setAngle(this.wheelAngle);
		}

		// 3. APPLY FORCE TO WHEELS
		/**
		 * Vector pointing in the force direction. Will be applied to 
		 * the wheel and is relative to the wheel
		 */
		Vector2 baseVector = Vector2.Zero; 

		// if accelerator is pressed down and speed limit has not been reached,
		// go forwards
		if ((accelerate) && (this.getSpeedKMH() < this.maxSpeed)) {
			baseVector = new Vector2(0, -1);
		} else if (brake) {
			// braking, but still moving forwards - increased force
			if (this.getLocalVelocity().y < 0)
				baseVector = new Vector2(0f, 1.3f);
			// going in reverse - less force
			else
				// Limit maximal reverse speed
				if(getSpeedKMH() < maxSpeed)
					baseVector = new Vector2(0f, 0.2f);
		} else {
			// slow down if not accelerating
			baseVector = new Vector2(0, 0);
			
			// Stop the car when it is going slow
			if (this.getSpeedKMH() < 4)
				this.setSpeed(0);
			else {
				if (this.getLocalVelocity().y < 0)
					baseVector = new Vector2(0, slowingFactor);
				else if (this.getLocalVelocity().y > 0)
					baseVector = new Vector2(0, -slowingFactor);
			}
		}
		
		// multiply by engine power, which gives us a force vector relative to
		// the wheel
		Vector2 forceVector = baseVector.scl(power);

		// Apply force to each wheel
		for (Wheel wheel : this.getPoweredWheels()) {
			Vector2 position = wheel.body.getWorldCenter();
			wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(
					forceVector.x, forceVector.y)), position, true);
		}
	}

	/**
	 * Draws the car
	 */
	@Override
	public void draw(GdxGraphics g) {
		Vector2 pos = carbox.getBodyPosition();
		g.drawFilledCircle(pos.x, pos.y, 10, Color.BLUE);
		
//		Vector2 v = getLocalVelocity();
//		v.scl(100);
//		g.drawLine(100, 100, 100 + v.x, 100+ v.y);
	}

}
