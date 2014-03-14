package hevs.gdx2d.demos.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

public class DemoRopeJoint extends PortableApplication
{
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	
	float width = 20;
	float height = 20;
	
	public DemoRopeJoint( boolean onAndroid )
	{
		super( onAndroid );
	}

	@Override
	public void onInit()
	{
		// TODO Auto-generated method stub
		setTitle( "Simple Rope Joint simulation, hit 2014" );
		
		int w = getWindowWidth(), h = getWindowHeight();
		
		// Build the walls around the screen
		new PhysicsScreenBoundaries( w, h );
		
		debugRenderer = new DebugRenderer();
		
		// The amount of rope segments
		int N = 10;
		
		PolygonShape shape1 = new PolygonShape();
		shape1.setAsBox( 8 * 0.01f, 2 * 0.01f );
		
		// The box in the middle of the screen for the origin
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
				
		
		Body prevBody = body;
		
		for( int i = 0; i < N; ++i )
		{
			BodyDef bd3 = new BodyDef();
			bd3.type = BodyType.DynamicBody;
			bd3.position.set( ( 350 + i * 20 ) * 0.01f, 350 * 0.01f );
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
			
			revoluteJointDefRope.localAnchorA.set( 0 * 0.01f, 0 * 0.01f );
			revoluteJointDefRope.localAnchorB.set( 20 * 0.01f, 0 * 0.01f );

			world.createJoint( revoluteJointDefRope );
			
			prevBody = body3;
		}
	}

	@Override
	public void onGraphicRender( GdxGraphics g )
	{
		// TODO Auto-generated method stub
		g.clear();
		
		debugRenderer.render( world, g.getCamera().combined );
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}
	
	public static void main( String args[] )
	{
		new DemoRopeJoint( false );
	}
}
