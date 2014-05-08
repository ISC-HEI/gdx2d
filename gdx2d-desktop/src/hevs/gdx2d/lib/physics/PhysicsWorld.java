package hevs.gdx2d.lib.physics;

import hevs.gdx2d.components.physics.utils.PhysicsConstants;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;

/**
 * Gets the single and only physics world, implements a sort of singleton design
 * pattern (this is not a completely standard singleton because the class
 * which is generated (World) is not of the type of the containing class (PhysicsWorld).
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.02
 */
public class PhysicsWorld {

	static {		
		GdxNativesLoader.load(); 
	}
	
	private static World instance = null;
	
	// Contains the object to be removed at each simulation step
	private static final Vector<Body> toRemove = new Vector<Body>();

	// Exists only to defeat normal instantiation
	private PhysicsWorld() {
	}

	/**
	 * @return The single and only World instance
	 */
	public static World getInstance() {
		if (instance == null) {
			instance = new World(new Vector2(0, PhysicsConstants.GRAVITY_VALUE), true);
		}
		return instance;
	}

	private static float accumulator;
	private static float step = PhysicsConstants.STEP_SIZE;

	/**
	 * Schedules an object for deletion when it is doable
	 * @param obj The object to remove from simulation
	 */
	static public void scheduleForDeletion(Body obj) {
		toRemove.add(obj);
	}

	/**
	 * Call this to update the physics simulation
	 */
	static public void updatePhysics() {
		updatePhysics(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Call this to update the physics simulation
	 * 
	 * @param dt
	 *            The amount of time that should be simulated
	 **/
	static public void updatePhysics(float dt) {
		accumulator += dt;

		while (accumulator >= step) {
			instance.step(step, PhysicsConstants.VELOCITY_IT, PhysicsConstants.POSITION_IT);
			accumulator -= step / PhysicsConstants.SPEEDUP;
		}
		
		/**
		 *  Handles object deletions 
		 */		
		for (Body obj : toRemove) {
			obj.setUserData(null);
			instance.destroyBody(obj);
		}
		
		toRemove.clear();		
	}

	/**
	 * To destroy it (required for JNI calls)
	 */
	public static void dispose() {
		if (instance != null) {
			instance.dispose();
			instance = null;
		}
	}
}