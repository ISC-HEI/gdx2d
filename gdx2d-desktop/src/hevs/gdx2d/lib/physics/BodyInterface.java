package hevs.gdx2d.lib.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * An interface for the Box2D bodies so that we can
 * work in pixels units and not in meters. Most of the physics 
 * operations have been converted to return pixels.
 * 
 * <p>Used in {@link AbstractPhysicsObject}.</p>
 * 
 * @author Pierre-André Mudry
 * @version 1.0
 */
public interface BodyInterface {
	public Vector2 getBodyPosition();
	public float getBodyAngle();
	public float getBodyAngleDeg();
	public float getBodyRadius();
	
	public void setBodyAwake(boolean awake);
	public void setBodyLinearVelocity(Vector2 v);
	public void setBodyLinearVelocity(float vx, float vy);
	public void setBodyLinearDamping(float damping);
	public void setBodyAngularDamping(float damping);

	public void applyBodyAngularImpulse(float impulse, boolean wake);
	public void applyBodyForce(Vector2 force, Vector2 point, boolean wake);
	public void applyBodyForce(float forceX, float forceY, float pointX, float pointY, boolean wake);	
	public void applyBodyForceToCenter(Vector2 force, boolean wake);
	public void applyBodyForceToCenter(float forceX, float forceY, boolean wake);
	public void applyBodyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake);
	public void applyBodyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake);
	public void applyBodyTorque(float torque, boolean wake);
}
