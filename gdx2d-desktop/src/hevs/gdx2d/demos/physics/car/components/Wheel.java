package hevs.gdx2d.demos.physics.car.components;

import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * A wheel of the car, adapted from
 * http://www.level1gamer.com/2012/10/24/top-down-car-using-libgdx-and-box2d/
 *  
 * @author Pierre-André Mudry
 * @version 1.2
 */
public class Wheel {	
	public Car car;//car this wheel belongs to	
	public boolean revolving; // does this wheel revolve when steering?
	public boolean powered; // is this wheel powered?
	public Body body;

	public Wheel(Car car, Vector2 wheelPos, float width, float length, boolean revolving, boolean powered) {		
		this.car = car;
		this.revolving = revolving;
		this.powered = powered;
		
		World world = PhysicsWorld.getInstance();
		
		Vector2 x = PhysicsConstants.coordPixelsToMeters(wheelPos);
		
		// Convert car position to pixels 
		Vector2 pos = car.carbox.getBody().getWorldPoint(x);

		// Create the wheel
		PhysicsBox wheel = new PhysicsBox("wheel", PhysicsConstants.coordMetersToPixels(pos), width, length/2, car.carbox.getBodyAngle());
		this.body = wheel.getBody();

	    // Create a revoluting joint to connect wheel to body
	    if(this.revolving){
	    	RevoluteJointDef jointdef=new RevoluteJointDef();
	        jointdef.initialize(car.carbox.getBody(), this.body, this.body.getWorldCenter());
	        jointdef.enableMotor=false; //we'll be controlling the wheel's angle manually
		    world.createJoint(jointdef);
	    }else{
	    	PrismaticJointDef jointdef=new PrismaticJointDef();
	        jointdef.initialize(car.carbox.getBody(), this.body, this.body.getWorldCenter(), new Vector2(1, 0));
	        jointdef.enableLimit=true;
	        jointdef.lowerTranslation=jointdef.upperTranslation=0;
		    world.createJoint(jointdef);
	    }
	}

	/**
	 * @param angle The wheel angle (in degrees), relative to the car
	 */
	public void setAngle (float angle){
		body.setTransform(body.getPosition(), car.carbox.getBodyAngle() + (float) Math.toRadians(angle));
	};

	/**
	 * @return The velocity vector, relative to the car
	 */
	public Vector2 getLocalVelocity () {
	    return car.carbox.getBody().getLocalVector(car.carbox.getBody().getLinearVelocityFromLocalPoint(body.getPosition()));
	};

	/**
	 * @return A world, unit vector pointing in the direction this wheel is currently moving
	 */
	public Vector2 getDirectionVector () {
		Vector2 directionVector;
		
		if (this.getLocalVelocity().y > 0)
			directionVector = new Vector2(0,1);
		else
			directionVector = new Vector2(0,-1);
			
		return directionVector.rotate((float) Math.toDegrees(this.body.getAngle()));	    
	};


	/**
	 * Subtracts sideways velocity from this wheel's velocity vector
	 * @return The remaining front-facing velocity vector
	 */
	public Vector2 getKillVelocityVector (){
	    Vector2 velocity = body.getLinearVelocity();
	    Vector2 sidewaysAxis= getDirectionVector();
	    float dotprod = velocity.dot(sidewaysAxis);
	    return new Vector2(sidewaysAxis.x*dotprod, sidewaysAxis.y*dotprod);
	};

	/**
	 * Removes sideways velocity from this wheel
	 */
	public void killSidewaysVelocity (){
		Vector2 v = getKillVelocityVector();
	    body.setLinearVelocity(v);
	};
}