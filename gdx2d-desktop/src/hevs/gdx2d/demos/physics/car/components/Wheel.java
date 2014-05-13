package hevs.gdx2d.demos.physics.car.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * A wheel of the car
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class Wheel {	
	public Car car;//car this wheel belongs to	

	private float width; // width in meters
	private float length; // length in meters
	public boolean revolving; // does this wheel revolve when steering?
	public boolean powered; // is this wheel powered?
	public Body body;

	public Wheel(World world, Car car, float posX, float posY, float width, float length, boolean revolving, boolean powered) {		
		this.car = car;
		this.width = width;
		this.length = length;
		this.revolving = revolving;
		this.powered = powered;
		
		//init body 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(car.body.getWorldPoint(new Vector2(posX, posY)));
		bodyDef.angle = car.body.getAngle();
		this.body = world.createBody(bodyDef);

		//init shape
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor=true; //wheel does not participate in collision calculations: resulting complications are unnecessary
		PolygonShape wheelShape = new PolygonShape();
		wheelShape.setAsBox(this.width/2, this.length/2);
		fixtureDef.shape = wheelShape;
		this.body.createFixture(fixtureDef);
		wheelShape.dispose();
		
	    //create joint to connect wheel to body
	    if(this.revolving){
	    	RevoluteJointDef jointdef=new RevoluteJointDef();
	        jointdef.initialize(this.car.body, this.body, this.body.getWorldCenter());
	        jointdef.enableMotor=false; //we'll be controlling the wheel's angle manually
		    world.createJoint(jointdef);
	    }else{
	    	PrismaticJointDef jointdef=new PrismaticJointDef();
	        jointdef.initialize(this.car.body, this.body, this.body.getWorldCenter(), new Vector2(1, 0));
	        jointdef.enableLimit=true;
	        jointdef.lowerTranslation=jointdef.upperTranslation=0;
		    world.createJoint(jointdef);
	    }
	}
	
	public void setAngle (float angle){
	    /*
	    angle - wheel angle relative to car, in degrees
	    */
		this.body.setTransform(body.getPosition(), this.car.body.getAngle() + (float) Math.toRadians(angle));
	};

	public Vector2 getLocalVelocity () {
	    /*returns get velocity vector relative to car
	    */
	    return this.car.body.getLocalVector(this.car.body.getLinearVelocityFromLocalPoint(this.body.getPosition()));
	};

	public Vector2 getDirectionVector () {
	    /*
	    returns a world unit vector pointing in the direction this wheel is moving
	    */
		Vector2 directionVector;
		if (this.getLocalVelocity().y > 0)
			directionVector = new Vector2(0,1);
		else
			directionVector = new Vector2(0,-1);
			
		return directionVector.rotate((float) Math.toDegrees(this.body.getAngle()));	    
	};


	public Vector2 getKillVelocityVector (){
	    /*
	    substracts sideways velocity from this wheel's velocity vector and returns the remaining front-facing velocity vector
	    */
	    Vector2 velocity = this.body.getLinearVelocity();
	    Vector2 sidewaysAxis=this.getDirectionVector();
	    float dotprod = velocity.dot(sidewaysAxis);
	    return new Vector2(sidewaysAxis.x*dotprod, sidewaysAxis.y*dotprod);
	};

	public void killSidewaysVelocity (){
	    /*
	    removes all sideways velocity from this wheels velocity
	    */
	    this.body.setLinearVelocity(this.getKillVelocityVector());
	};
}
