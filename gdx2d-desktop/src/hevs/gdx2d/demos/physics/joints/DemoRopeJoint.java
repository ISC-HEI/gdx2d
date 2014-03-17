package hevs.gdx2d.demos.physics.joints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/** 
 * A demo on how to build chains with rope joints with box2d 
 * @author Thierry Hischier, hit 2014
 * @version 1.0
 */
public class DemoRopeJoint extends PortableApplication
{
	World world = PhysicsWorld.getInstance();
	
	// Contains all the objects that will be simulated
	DebugRenderer debugRenderer;
	public DemoRopeJoint( boolean onAndroid )
	{
		super( onAndroid );
	}

	@Override
	public void onInit()
	{
		setTitle( "Simple Rope Joint simulation, hit 2014" );
		
		final int w = getWindowWidth(), h = getWindowHeight();
		final Vector2 middlePos = PhysicsConstants.coordPixelsToMeters( new Vector2( w / 2, h / 1.5f ) );
		
		// Build the walls around the screen
		new PhysicsScreenBoundaries( w, h );
		debugRenderer = new DebugRenderer();
		
		// The amount of rope segments
		int N = 10;
		
		// The polygonShape which will be our chain elements
		PolygonShape shape1 = new PolygonShape();
		shape1.setAsBox( 8 * PhysicsConstants.PIXEL_TO_METERS, 2 * PhysicsConstants.PIXEL_TO_METERS );
		
		// The box in the middle of the screen for the origin
		BodyDef bd1 = new BodyDef();
		bd1.type = BodyType.StaticBody;
		bd1.position.set( middlePos );
		bd1.angle = 0;
		
		Body body = world.createBody( bd1 );
		
		FixtureDef fd1 = new FixtureDef();
		fd1.density = 20.0f;
		fd1.restitution = 0.0f;
		fd1.friction = 1.0f;
		fd1.shape = new CircleShape();
		fd1.shape.setRadius( 30 * PhysicsConstants.PIXEL_TO_METERS );
		body.createFixture( fd1 );
		fd1.shape.dispose();
		
		Body prevBody = body;
		
		for( int i = 0; i < N; ++i )
		{
			// Build the body and the RevoluteJointDef for the chain elements. 
			// Connect each element with the element before
			BodyDef bd3 = new BodyDef();
			bd3.type = BodyType.DynamicBody;
			bd3.position.set( middlePos.x + ( i * 0.02f ), middlePos.y );
			bd3.angle = 0;
			
			Body body3 = world.createBody( bd3 );
			
			FixtureDef fd3 = new FixtureDef();
			fd3.density = 5.0f;
			fd3.restitution = 0.0f;
			fd3.friction = 3.0f;
			fd3.shape = shape1;
			body3.createFixture( fd3 );
			
			RevoluteJointDef revoluteJointDefRope = new RevoluteJointDef();
			revoluteJointDefRope.bodyA = prevBody;
			revoluteJointDefRope.bodyB = body3;
			revoluteJointDefRope.collideConnected = false;
			revoluteJointDefRope.localAnchorA.set( 0 * PhysicsConstants.PIXEL_TO_METERS, 0 );
			revoluteJointDefRope.localAnchorB.set( 20 * PhysicsConstants.PIXEL_TO_METERS, 0 );
			
			// Create the joint and set the previous body to the created one
			world.createJoint( revoluteJointDefRope );
			prevBody = body3;
		}
	}


	@Override
	public void onGraphicRender( GdxGraphics g )
	{
		g.clear();
		debugRenderer.render( world, g.getCamera().combined );
		PhysicsWorld.updatePhysics( Gdx.graphics.getDeltaTime() );
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}
	
	public static void main( String args[] )
	{
		new DemoRopeJoint( false );
	}
}