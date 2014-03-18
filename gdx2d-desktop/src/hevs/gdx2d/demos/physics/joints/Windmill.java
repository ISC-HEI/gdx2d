package hevs.gdx2d.demos.physics.joints;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsStaticBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/** 
 * A simple demo on how to use anchor points in gdx2d
 * 
 * <p>Based on ex 5.9 from the Nature of code book</p>
 * 
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The nature of code example</a>
 * @author Thierry Hischier, hit 2014
 * @version 1.0
 */
public class Windmill extends PortableApplication
{
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	
	RevoluteJoint joint;
	Body box1;
	Body box2;
	
	// Linked List to store all particles
	LinkedList<CircleParticle> particles = new LinkedList<CircleParticle>();
	
	Random random;
	
	float width, height;
	
	int time = 0;
	boolean motorOn = false;
	
	public Windmill( boolean onAndroid )
	{
		super( onAndroid );
	}
	
	@Override
	public void onInit() 
	{
		setTitle( "Simple windmill simulation, hit 2014" );
		System.out.println( "Press left mouse button to enable/disable the motor" );
		// Create the debugRenderer
		debugRenderer = new DebugRenderer();
		random = new Random();
		
		width = getWindowWidth();
		height = getWindowHeight();
		
		// Create PhysicStaticBox where the windmill will be fixed. It is located in the center of the frame
		PhysicsStaticBox box11 = new PhysicsStaticBox( "box1", new Vector2( getWindowWidth() / 2, getWindowHeight() / 2 ), 10, 40 );
		box1 = box11.getBody();
		
		// Create the windmill wing. It is also located in the center of the frame
		PhysicsBox box22 = new PhysicsBox( "box2", new Vector2( getWindowWidth() / 2, getWindowHeight() / 2 ), 120, 10 );
		box2 = box22.getBody();
		
		// Define the joint as between two bodies
		RevoluteJointDef rjd = new RevoluteJointDef();
		// Set the anchor point for the two PhysicBoxes
		rjd.initialize( box1, box2, box1.getWorldCenter() );
		
		// Set the parameter for the motor functionality
		rjd.motorSpeed = ( float )Math.PI;
		rjd.maxMotorTorque = 1000.0f;
		rjd.enableMotor = motorOn;
		
		// Create the joint
		joint = ( RevoluteJoint )world.createJoint( rjd );
	}

	@Override
	public void onGraphicRender( GdxGraphics g ) 
	{
		g.clear();		
		
		// Draw all the added particles and destroy the particles which are no longer in the visible
		for( Iterator<CircleParticle> iter = particles.iterator(); iter.hasNext(); )
		{
			CircleParticle particle = iter.next();
			particle.draw(g);
			
			Vector2 p = particle.getBodyPosition();

			// If a particle is not visible anymore, it should be destroyed
			if( p.y > height || p.y < 0 || p.x > width || p.x < 0 )
			{
				// Mark the particle for deletion when possible
				particle.destroy();
				
				// Remove the particle from the collection as well
				iter.remove();
			}
		}
		
		// Add from time to time a new CircleParticle to the frame
		if( time % 25 == 0 )
		{
			int x = random.nextInt( 100 );
			CircleParticle newParticle = new CircleParticle( "a particle", 
															 new Vector2( ( ( getWindowWidth() / 2 ) - 50 ) + x, 
															 getWindowHeight() - 5 ),
															 5 );
			newParticle.enableCollisionListener();
			particles.add( newParticle );
		}
		
		// Render the physics and draw the logo, fps information and the status of the motor
		debugRenderer.render( world, g.getCamera().combined );
		PhysicsWorld.updatePhysics( Gdx.graphics.getDeltaTime() );
		
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
		
		g.setColor( Color.WHITE );
		g.drawString( width - 250, 40, "Left Mouse button: Motor ON/OFF" );
		if( motorOn )
		{
			g.drawString( width - 100, 20, "Motor is ON" );
		}
		else
		{
			g.drawString( width - 100, 20, "Motor is OFF" );
		}
		
		// Increment the timer variable
		time++;
	}
	
	@Override
	public void onClick( int x, int y, int button )
	{
		super.onClick( x, y, button );
		if( button == Input.Buttons.LEFT )
		{
			if( motorOn )
			{
				motorOn = false;
				joint.enableMotor( motorOn );
			}
			else
			{
				motorOn = true;
				joint.enableMotor( motorOn );
			}
		}
	}
	
	public static void main( String args[] )
	{
		new Windmill( false );
	}
}
