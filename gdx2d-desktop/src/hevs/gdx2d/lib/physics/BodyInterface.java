package hevs.gdx2d.lib.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;

/**
 * An interface for the Box2D bodies so that we can
 * work in pixels units and not in meters. Most of the physics
 * operations have been converted to return pixels.
 * <p/>
 * <p>Used in {@link AbstractPhysicsObject}.</p>
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
public interface BodyInterface {
	float getBodyAngle();

	float getBodyAngularDamping();

	void setBodyAngularDamping(float damping);

	float getBodyAngularVelocity();

	Array<Fixture> getBodyFixtureList();

	float getBodyGravityScale();

	float getBodyInertia();

	float getBodyAngleDeg();

	float getBodyRadius();

	float getBodyMass();

	MassData getBodyMassData();

	Vector2 getBodyPosition();

	Transform getBodyTransform();

	float getBodyLinearDamping();

	void setBodyLinearDamping(float damping);

	Vector2 getBodyLinearVelocity();

	void setBodyLinearVelocity(Vector2 v);

	Vector2 getBodyLinearVelocityFromLocalPoint(Vector2 v);

	Vector2 getBodyLinearVelocityFromWorldPoint(Vector2 v);

	Vector2 getBodyLocalCenter();

	Vector2 getBodyLocalPoint(Vector2 v);

	Vector2 getBodyLocalVector(Vector2 v);

	World getBodyWorld();

	Vector2 getBodyWorldCenter();

	Vector2 getBodyWorldPoint(Vector2 v);

	Vector2 getBodyWorldVector(Vector2 v);

	boolean isBodyActive();

	void setBodyActive(boolean active);

	boolean isBodyAwake();

	void setBodyAwake(boolean awake);

	boolean isBodyBullet();

	boolean isBodyFixedRotation();

	boolean isBodySleepingAllowed();

	void setBodyLinearVelocity(float vx, float vy);

	void applyBodyAngularImpulse(float impulse, boolean wake);

	void applyBodyForce(Vector2 force, Vector2 point, boolean wake);

	void applyBodyForce(float forceX, float forceY, float pointX, float pointY, boolean wake);

	void applyBodyForceToCenter(Vector2 force, boolean wake);

	void applyBodyForceToCenter(float forceX, float forceY, boolean wake);

	void applyBodyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake);

	void applyBodyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake);

	void applyBodyTorque(float torque, boolean wake);
}
