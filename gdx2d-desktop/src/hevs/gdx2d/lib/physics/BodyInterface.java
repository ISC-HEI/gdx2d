package hevs.gdx2d.lib.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

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
	public float getBodyAngle();

	public float getBodyAngularDamping();

	public void setBodyAngularDamping(float damping);

	public float getBodyAngularVelocity();

	public ArrayList<Fixture> getBodyFixtureList();

	public float getBodyGravityScale();

	public float getBodyInertia();

	public float getBodyAngleDeg();

	public float getBodyRadius();

	public float getBodyMass();

	public MassData getBodyMassData();

	public Vector2 getBodyPosition();

	public Transform getBodyTransform();

	public float getBodyLinearDamping();

	public void setBodyLinearDamping(float damping);

	public Vector2 getBodyLinearVelocity();

	public void setBodyLinearVelocity(Vector2 v);

	public Vector2 getBodyLinearVelocityFromLocalPoint(Vector2 v);

	public Vector2 getBodyLinearVelocityFromWorldPoint(Vector2 v);

	public Vector2 getBodyLocalCenter();

	public Vector2 getBodyLocalPoint(Vector2 v);

	public Vector2 getBodyLocalVector(Vector2 v);

	public World getBodyWorld();

	public Vector2 getBodyWorldCenter();

	public Vector2 getBodyWorldPoint(Vector2 v);

	public Vector2 getBodyWorldVector(Vector2 v);

	public boolean isBodyActive();

	public void setBodyActive(boolean active);

	public boolean isBodyAwake();

	public void setBodyAwake(boolean awake);

	public boolean isBodyBullet();

	public boolean isBodyFixedRotation();

	public boolean isBodySleepingAllowed();

	public void setBodyLinearVelocity(float vx, float vy);

	public void applyBodyAngularImpulse(float impulse, boolean wake);

	public void applyBodyForce(Vector2 force, Vector2 point, boolean wake);

	public void applyBodyForce(float forceX, float forceY, float pointX, float pointY, boolean wake);

	public void applyBodyForceToCenter(Vector2 force, boolean wake);

	public void applyBodyForceToCenter(float forceX, float forceY, boolean wake);

	public void applyBodyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake);

	public void applyBodyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake);

	public void applyBodyTorque(float torque, boolean wake);
}
