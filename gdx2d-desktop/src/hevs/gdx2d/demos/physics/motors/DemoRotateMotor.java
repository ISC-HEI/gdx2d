package hevs.gdx2d.demos.physics.motors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * TODO This demo should be cleaned-up and beautified before being released
 */
public class DemoRotateMotor extends PortableApplication
{
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	
	public DemoRotateMotor( boolean onAndroid )
	{
		super( onAndroid );
	}
	
	@Override
	public void onInit() 
	{
		// TODO Auto-generated method stub
		setTitle( "Simple rotate motor demo, hit 2014" );
		
		int w = getWindowWidth(), h = getWindowHeight();
		
		// Build the walls around the screen
		new PhysicsScreenBoundaries( w, h );
		
		debugRenderer = new DebugRenderer();

		BodyDef bd1 = new BodyDef();
		bd1.type = BodyType.StaticBody;
		bd1.position.set( 350 * 0.01f, 350 * 0.01f );
		bd1.angle = 0;
		
		Body body = world.createBody( bd1 );
		FixtureDef fd1 = new FixtureDef();
		fd1.density = 20.0f;
		fd1.restitution = 0.0f;
		fd1.friction = 1.0f;
		fd1.shape = new CircleShape();
		fd1.shape.setRadius( 30 * 0.01f );
		
		body.createFixture( fd1 );
		fd1.shape.dispose();
		
		BodyDef bd2 = new BodyDef();
		bd2.type = BodyType.DynamicBody;
		bd2.position.set( 350 * 0.01f, 350 * 0.01f );
		bd2.angle = 0;
		
		Body body2 = world.createBody( bd2 );
		FixtureDef fd2 = new FixtureDef();
		fd2.density = 20.0f;
		fd2.restitution = 0.0f;
		fd2.friction = 2.0f;
		fd2.shape = new CircleShape();
		fd2.shape.setRadius( 20 * 0.01f );
		
		
		body2.createFixture( fd2 );
		fd2.shape.dispose();
		
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.bodyA = body;
		revoluteJointDef.bodyB = body2;
		revoluteJointDef.collideConnected = false;
		
		revoluteJointDef.localAnchorA.set( 0 * 0.01f, 15 * 0.01f );
		//revoluteJointDef.localAnchorA.set( 30 * 0.01f, 0 * 0.01f );
		revoluteJointDef.localAnchorB.set( 100 * 0.01f, 0 * 0.01f );
		
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.maxMotorTorque = 10;
		revoluteJointDef.motorSpeed = 5;
		
		world.createJoint( revoluteJointDef );
	}

	@Override
	public void onGraphicRender(GdxGraphics g) 
	{
		g.clear();
        debugRenderer.render(world, g.getCamera().combined);
        PhysicsWorld.updatePhysics();
        g.drawSchoolLogoUpperRight();
        g.drawFPS();
	}
	
	public static void main( String args[] )
	{
		new DemoRotateMotor( false );
	}
}
