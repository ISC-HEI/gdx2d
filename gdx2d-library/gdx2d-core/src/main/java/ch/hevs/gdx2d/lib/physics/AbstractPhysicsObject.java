package ch.hevs.gdx2d.lib.physics;

import ch.hevs.gdx2d.components.physics.primitives.*;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.Array;


/**
 * An abstract physical object that contains everything required for simulation.<br>
 * For concrete instances, see {@link PhysicsBox}, {@link PhysicsCircle},
 * {@link PhysicsPolygon}, {@link PhysicsStaticBox} and
 * {@link PhysicsStaticCircle}.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.3
 */
public abstract class AbstractPhysicsObject implements ContactListener, BodyInterface {
	// Reusable object for creating other objects
	static final private BodyDef bodyDef = new BodyDef();
	private final float p2m = PhysicsConstants.P2M;
	private final float m2p = PhysicsConstants.M2P;

	/**
	 * A name for the object
	 */
	public String name;

	/**
	 * The physical characteristics of the the object, such as friction etc...
	 */
	protected Fixture f;
	float lastCollideEnergy = -1;

	// FIXME: the objects do not react correctly on screen orientation changes...
	// Contains the body for physics simulation
	private Body body;

	/**
	 * Abstract constructor.
	 */
	protected AbstractPhysicsObject(Type t, String name, Vector2 position,
									float width, float height, float density, float restitution,
									float friction, boolean isDynamic) {
		createObject(t, name, position, width, height, density, restitution, friction, 0, isDynamic);
	}

	/**
	 * Abstract constructor.
	 */
	protected AbstractPhysicsObject(String name, Vector2 position,
									Vector2[] vertices, float density, float restitution,
									float friction, boolean isDynamic) {
		createPolygonObject(name, position, vertices, 0, isDynamic, density, restitution, friction);
	}

	/**
	 * Abstract constructor.
	 */
	protected AbstractPhysicsObject(Type t, String name, Vector2 position,
									float width, float height, float density, float restitution,
									float friction, float angle, boolean isDynamic) {
		createObject(t, name, position, width, height, density, restitution,
				friction, angle, isDynamic);
	}

	private void createPolygonObject(String name, Vector2 position, Vector2[] vertices,
									 float angle, boolean isDynamic, float density, float restitution,
									 float friction) {
		this.name = name;

		// Conversions from pixel world to meters
		Vector2 pos = position.cpy().scl(p2m);

		Vector2[] verticesM = new Vector2[vertices.length];

		// Convert all vertices to meter space
		int i = 0;
		for (Vector2 v : vertices) {
			verticesM[i++] = v.cpy().scl(p2m);
		}

		// As of now, we now work in meter space

		bodyDef.position.set(pos);

		if (isDynamic)
			bodyDef.type = BodyType.DynamicBody;
		else
			bodyDef.type = BodyType.StaticBody;

		bodyDef.angle = angle;

		// The shape used for collisions in physics
		PolygonShape s = new PolygonShape();
		s.set(verticesM);

		World world = PhysicsWorld.getInstance();
		body = world.createBody(bodyDef);

		createFixture(s, density, restitution, friction);

		// Destroy the shape because we don't need it anymore (JNI side)
		s.dispose();
		body.setUserData(this);
	}

	private void createObject(Type t, String name, Vector2 position,
							  float width, float height, float density, float restitution,
							  float friction, float angle, boolean isDynamic) {
		this.name = name;

		// Conversions from pixel world to meters
		Vector2 pos = position.cpy().scl(p2m);
		width *= p2m;
		height *= p2m;

		// As of now, we now work in meter space

		bodyDef.position.set(pos);

		if (isDynamic)
			bodyDef.type = BodyType.DynamicBody;
		else
			bodyDef.type = BodyType.StaticBody;

		bodyDef.angle = angle;

		// The shape used for collisions in physics
		Shape s;

		if (t == Type.Circle) {
			s = new CircleShape();
			s.setRadius(width);
		} else {
			PolygonShape p = new PolygonShape();
			p.setAsBox(width / 2, height / 2);
			s = p;
		}

		World world = PhysicsWorld.getInstance();
		body = world.createBody(bodyDef);

		createFixture(s, density, restitution, friction);

		// Destroy the shape because we don't need it anymore (JNI side)
		s.dispose();
		body.setUserData(this);
	}

	/**
	 * Creates the fixture (collision information) attached to the object
	 *
	 * @param s           The shape we want for collision
	 * @param density     The density of the object, in kg/ms²
	 * @param restitution For elastic collisions
	 * @param friction    Coulomb friction, does not work for circles
	 */
	protected void createFixture(Shape s, float density, float restitution,
								 float friction) {
		FixtureDef def = new FixtureDef();
		def.density = density;
		def.restitution = restitution;
		def.friction = friction;
		def.shape = s;
		def.filter.groupIndex = 1;

		f = body.createFixture(def);

		// Makes things stop slowly, always
		body.setLinearDamping(0.001f);
	}

	/**
	 * Should be called to destroy a simulated object Warning, deletion will
	 * happen at the next simulation step, not immediately
	 */
	public void destroy() {
		PhysicsWorld.scheduleForDeletion(this.body);
	}

	/**
	 * Makes the object react on collisions
	 */
	public void enableCollisionListener() {
		World world = PhysicsWorld.getInstance();
		world.setContactListener(this);
	}

	/**
	 * A collision occurred between this object and another.
	 *
	 * @param theOtherObject the collides object
	 * @param energy         the collision energy
	 */
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		// TODO: should this method be abstract ?
		// Breaks the API but better for next year
		// Function which is called when collision with this object occurs
	}

	// *********************************************
	// Internal functions for collisions, do not use
	// *********************************************

	/**
	 * Sets a collision group to all fixtures of an {@link AbstractPhysicsObject}.
	 * <p/>
	 * Collision groups let you specify an integral group index.
	 * You can have all fixtures with the same group index
	 * always collide (positive index) or never collide (negative index).
	 * <p/>
	 * See <a href="http://www.aurelienribon.com/blog/2011/07/box2d-tutorial-collision-filtering">http://www.aurelienribon.com/blog/2011/07/box2d-tutorial-collision-filtering/</a>
	 *
	 * @param id the identifier of the group
	 */
	public void setCollisionGroup(int id) {
		Filter filter = new Filter();
		filter.groupIndex = (short) id;

		for (Fixture f : body.getFixtureList()) {
			f.setFilterData(filter);
		}
	}

	@Override
	public void beginContact(Contact contact) {
		AbstractPhysicsObject ob1, ob2;
		ob1 = (AbstractPhysicsObject) contact.getFixtureA().getBody()
				.getUserData();
		ob2 = (AbstractPhysicsObject) contact.getFixtureB().getBody()
				.getUserData();

		ob1.collision(ob2, lastCollideEnergy);
		ob2.collision(ob1, lastCollideEnergy);

		lastCollideEnergy = -1;
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		lastCollideEnergy = impulse.getNormalImpulses()[0];
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	/**
	 * Body method redefinition for meters / pixels.
	 * Implementation of the {@link BodyInterface} interface.
	 *
	 * @return The physics body position, in the pixel space
	 */
	@Override
	public Vector2 getBodyPosition() {
		return body.getPosition().scl(m2p);
	}

	/**
	 * @return The physics body central position, in the pixel space
	 */
	@Override
	public Vector2 getBodyLocalCenter() {
		return body.getLocalCenter().scl(m2p);
	}

	/**
	 * @return The current angle of the physics object, in radians
	 */
	@Override
	public float getBodyAngle() {
		return body.getAngle();
	}

	/**
	 * @return The current angle of the physics object, in degrees
	 */
	@Override
	public float getBodyAngleDeg() {
		return body.getAngle() * PhysicsConstants.RAD_TO_DEG;
	}

	/**
	 * @return the radius of the current circle shape. If not a circle shape,
	 * throws an {@link UnsupportedOperationException}
	 * @throws UnsupportedOperationException
	 */
	@Override
	public float getBodyRadius() {
		if (f.getShape().getType() == Type.Circle) {
			return f.getShape().getRadius() * m2p;
		} else {
			throw new UnsupportedOperationException(
					"Only circle shapes have radius");
		}
	}

	@Override
	public void applyBodyTorque(float torque, boolean wake) {
		body.applyTorque(torque, wake);
	}

	@Override
	public float getBodyAngularDamping() {
		return body.getAngularDamping();
	}

	@Override
	public void setBodyAngularDamping(float damping) {
		body.setAngularDamping(damping);
	}

	@Override
	public float getBodyLinearDamping() {
		return body.getLinearDamping();
	}

	@Override
	public void setBodyLinearDamping(float damping) {
		body.setLinearDamping(damping);
	}

	@Override
	public Vector2 getBodyLinearVelocity() {
		return body.getLinearVelocity();
	}

	@Override
	public void setBodyLinearVelocity(Vector2 v) {
		body.setLinearVelocity(v);
	}

	public Vector2 getBodyLinearVelocityPixels() {
		return body.getLinearVelocity().scl(m2p);
	}

	@Override
	public Vector2 getBodyLinearVelocityFromLocalPoint(Vector2 v) {
		return body.getLinearVelocityFromLocalPoint(v.scl(p2m)).scl(m2p);
	}

	@Override
	public Vector2 getBodyLinearVelocityFromWorldPoint(Vector2 v) {
		return body.getLinearVelocityFromWorldPoint(v.scl(p2m)).scl(m2p);
	}

	@Override
	public float getBodyAngularVelocity() {
		return body.getAngularVelocity();
	}

	@Override
	public Array<Fixture> getBodyFixtureList() {
		return body.getFixtureList();
	}

	@Override
	public float getBodyGravityScale() {
		return body.getGravityScale();
	}

	@Override
	public float getBodyInertia() {
		return body.getInertia();
	}

	@Override
	public Vector2 getBodyLocalPoint(Vector2 v) {
		return body.getLocalPoint(v.scl(p2m)).scl(m2p);
	}

	@Override
	public Vector2 getBodyLocalVector(Vector2 v) {
		return body.getLocalVector(v.scl(p2m)).scl(m2p);
	}

	@Override
	public float getBodyMass() {
		return body.getMass();
	}

	@Override
	public MassData getBodyMassData() {
		return body.getMassData();
	}

	@Override
	public Transform getBodyTransform() {
		return body.getTransform();
	}

	@Override
	public World getBodyWorld() {
		return body.getWorld();
	}

	@Override
	public Vector2 getBodyWorldCenter() {
		return body.getWorldCenter().scl(m2p);
	}

	@Override
	public Vector2 getBodyWorldPoint(Vector2 v) {
		return body.getWorldPoint(v.scl(p2m)).cpy().scl(m2p);
	}

	@Override
	public Vector2 getBodyWorldVector(Vector2 v) {
		return body.getWorldVector(v.scl(p2m)).cpy().scl(m2p);
	}

	@Override
	public boolean isBodyActive() {
		return body.isActive();
	}

	@Override
	public void setBodyActive(boolean active) {
		body.setActive(active);
	}

	@Override
	public boolean isBodyAwake() {
		return body.isAwake();
	}

	@Override
	public void setBodyAwake(boolean awake) {
		body.setAwake(awake);
	}

	@Override
	public boolean isBodyBullet() {
		return body.isBullet();
	}

	@Override
	public boolean isBodyFixedRotation() {
		return body.isFixedRotation();
	}

	@Override
	public boolean isBodySleepingAllowed() {
		return body.isSleepingAllowed();
	}

	@Override
	public void setBodyLinearVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
	}

	@Override
	public void applyBodyAngularImpulse(float impulse, boolean wake) {
		body.applyAngularImpulse(impulse, wake);
	}

	@Override
	public void applyBodyForce(float forceX, float forceY, float pointX,
							   float pointY, boolean wake) {
		body.applyForce(forceX, forceY, pointX, pointY, wake);
	}

	@Override
	public void applyBodyForce(Vector2 force, Vector2 point, boolean wake) {
		body.applyForce(force, point, wake);
	}

	@Override
	public void applyBodyForceToCenter(float forceX, float forceY, boolean wake) {
		body.applyForceToCenter(forceX, forceY, wake);
	}

	@Override
	public void applyBodyForceToCenter(Vector2 force, boolean wake) {
		body.applyForceToCenter(force, wake);
	}

	@Override
	public void applyBodyLinearImpulse(float impulseX, float impulseY,
									   float pointX, float pointY, boolean wake) {
		body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
	}

	@Override
	public void applyBodyLinearImpulse(Vector2 impulse, Vector2 point,
									   boolean wake) {
		body.applyLinearImpulse(impulse, point, wake);
	}

	/**
	 * Set (or unset) sensor mode
	 * @param enable
	 *
	 * Set the body traversable, but still sensible to collisions
	 */
	public void setSensor(boolean enable) {
		for (Fixture x : getBody().getFixtureList()) {
			x.setSensor(true);
		}
	}

	/**
	 * Convenience method for some special operations. You should not use that
	 * normally because the dimensions are not scaled appropriately in the
	 * object itself.
	 *
	 * @return The body for the simulation
	 */
	public Body getBody() {
		return this.body;
	}
}
