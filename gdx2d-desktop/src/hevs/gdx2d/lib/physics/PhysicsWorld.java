package hevs.gdx2d.lib.physics;

import hevs.gdx2d.components.physics.utils.PhysicsConstants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Gets the single and only physics world, implements
 * the singleton design pattern.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.02
 */
public class PhysicsWorld {

	private static World instance = null;

	// Exists only to defeat normal instantiation 
	private PhysicsWorld() {}

	public static World getInstance() {
		if (instance == null) {
			instance = new World(new Vector2(0, PhysicsConstants.GRAVITY_VALUE), true);
		}		
		return instance;		
	}
	
	private static float accumulator;
	private static float step = PhysicsConstants.STEP_SIZE;	
	
	/**
	 * Call this to update the physics simulation
	 */
	static public void updatePhysics(){
		updatePhysics(Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * Call this to update the physics simulation
	 * @param dt The amount of time that should be simulated	 
	 **/
	static public void updatePhysics(float dt){
	   accumulator+=dt;

	   while(accumulator>=step){
		  instance.step(step,PhysicsConstants.VELOCITY_IT,PhysicsConstants.POSITION_IT);		  
	      accumulator -= step / PhysicsConstants.SPEEDUP;  
	   }
	}
	
	/**
	 * To destroy it (required for JNI calls)
	 */
	public static void dispose(){
		if(instance != null){
			instance.dispose();
			instance = null;
		}
	}
}