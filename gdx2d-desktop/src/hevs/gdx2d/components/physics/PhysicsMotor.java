package hevs.gdx2d.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * A motor to make two objects turn around each other.
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class PhysicsMotor {

	// Contains the first body for physics simulation
	Body body1;
	// Contains the second body for physics simulation
	Body body2;
	// Contains the position of the anchor point between the two bodies
	Vector2 anchorPoint;

	// Contains the definition for the RevoluteJoint
	RevoluteJointDef rjd;
	// Contains the RevoluteJoint for the physics simulation
	private RevoluteJoint joint;

	/**
	 * Constructor which defines and creates the {@link RevoluteJoint}.
	 * It also sets the desired information for the PhysicsMotor.
	 *
	 * @param body1          The first body for the joint
	 * @param body2          The second body for the joint
	 * @param anchorPoint    The point that links the two objects
	 * @param motorSpeed     The speed of the motor
	 * @param maxMotorTorque The maximum torque that the motor can exert
	 * @param motorEnable    If the motor is enabled or not
	 */
	public PhysicsMotor(Body body1, Body body2, Vector2 anchorPoint, float motorSpeed, float maxMotorTorque, boolean motorEnable) {
		this.body1 = body1;
		this.body2 = body2;
		this.anchorPoint = anchorPoint;

		rjd = new RevoluteJointDef();
		rjd.initialize(this.body1, this.body2, anchorPoint);

		rjd.motorSpeed = motorSpeed;
		rjd.maxMotorTorque = maxMotorTorque;
		rjd.enableMotor = motorEnable;

		joint = (RevoluteJoint) PhysicsWorld.getInstance().createJoint(rjd);
	}

	/**
	 * Another constructor which defines and creates the RevoluteJoint. It also sets all information for the PhysicsMotor to 0 and false.
	 * See {@link #PhysicsMotor(Body, Body, Vector2, float, float, boolean)}
	 *
	 * @param body1       The first body for the joint
	 * @param body2       The second body for the joint
	 * @param anchorPoint The point that links the two objects
	 */
	public PhysicsMotor(Body body1, Body body2, Vector2 anchorPoint) {
		this.body1 = body1;
		this.body2 = body2;
		this.anchorPoint = anchorPoint;

		rjd = new RevoluteJointDef();
		rjd.initialize(this.body1, this.body2, anchorPoint);

		rjd.motorSpeed = 0;
		rjd.maxMotorTorque = 0;
		rjd.enableMotor = false;

		joint = (RevoluteJoint) PhysicsWorld.getInstance().createJoint(rjd);
	}

	public void enableLimit(boolean flag) {
		joint.enableLimit(flag);
	}

	public void enableMotor(boolean flag) {
		joint.enableMotor(flag);
	}

	public float getAngle() {
		return joint.getJointAngle();
	}

	public float getSpeed() {
		return joint.getJointSpeed();
	}

	public Vector2 getLocalAnchorA() {
		return joint.getAnchorA();
	}

	public Vector2 getLocalAnchorB() {
		return joint.getAnchorB();
	}

	public float getLowerLimit() {
		return joint.getLowerLimit();
	}

	public float getMotorSpeed() {
		return joint.getMotorSpeed();
	}

	public float getMaxMotortorque(float invDt) {
		return joint.getMotorTorque(invDt);
	}

	public float getUpperLimit() {
		return joint.getUpperLimit();
	}

	public boolean isLimitEnabled() {
		return joint.isLimitEnabled();
	}

	public boolean isMotorEnabled() {
		return joint.isMotorEnabled();
	}

	public void setLowerLimit(float lower) {
		joint.setLimits(lower, joint.getUpperLimit());
	}

	public void setUpperLimit(float upper) {
		joint.setLimits(joint.getLowerLimit(), upper);
	}

	public void setLimits(float lower, float upper) {
		joint.setLimits(lower, upper);
	}

	public void setMaxMotorTorque(float torque) {
		joint.setMaxMotorTorque(torque);
	}

	public void setMotorSpeed(float speed) {
		joint.setMotorSpeed(speed);
	}

	/**
	 * Initialize the motor with the desired parameters.
	 * This is applicable for every instance of {@link PhysicsMotor}.
	 *
	 * @param speed  The new speed for the joint motor
	 * @param torque The new torque value for the joint motor
	 * @param enable The new state for the joint motor
	 */
	public void initializeMotor(float speed, float torque, boolean enable) {
		joint.setMotorSpeed(speed);
		joint.setMaxMotorTorque(torque);
		joint.enableMotor(enable);
	}

	public RevoluteJoint getJoint() {
		return joint;
	}
}
