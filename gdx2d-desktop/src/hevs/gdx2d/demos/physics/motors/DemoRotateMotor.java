package hevs.gdx2d.demos.physics.motors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * A demo on how to use PhysicsMotor
 * 
 * <p>
 * Based on ex 5.9 from the Nature of code book
 * The bodys are created without the help of this library to show how 
 * two bodys for a joint motor can be created.
 * </p>
 * 
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The
 *      nature of code example</a>
 * @author Thierry Hischier, hit 2014
 * @version 1.1
 */
public class DemoRotateMotor extends PortableApplication
{
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	PhysicsMotor physicMotor;
	
	BitmapImage clockBitmap;
	BitmapImage clockSecondsBitmap;
	Body body;
	Body body2;
	
	public DemoRotateMotor( boolean onAndroid )
	{
		super( onAndroid );
	}
	
	@Override
	public void onInit() 
	{
		setTitle( "Simple rotate motor demo, hit 2014" );
		
		int w = getWindowWidth(), h = getWindowHeight();
		
		// Build the walls around the screen
		new PhysicsScreenBoundaries( w, h );
		
		// A renderer that displays physics objects things simply
		debugRenderer = new DebugRenderer();
		
		clockBitmap = new BitmapImage("data/images/clock.png");
		clockSecondsBitmap = new BitmapImage( "data/images/clock_second.png" );

		// Create the static Body which acts as the airplane motor
		BodyDef bd1 = new BodyDef();
		bd1.type = BodyType.StaticBody;
		bd1.position.set( 350 * 0.01f, 350 * 0.01f );
		bd1.angle = 0;
		
		// Physical information about the static body
		body = world.createBody( bd1 );
		FixtureDef fd1 = new FixtureDef();
		fd1.density = 20.0f;
		fd1.restitution = 0.0f;
		fd1.friction = 1.0f;
		fd1.shape = new CircleShape();
		fd1.shape.setRadius( 30 * 0.01f );
		
		body.createFixture( fd1 );
		fd1.shape.dispose();
		
		// Create the dynamic Body which acts as the airplane propeller
		BodyDef bd2 = new BodyDef();
		bd2.type = BodyType.DynamicBody;
		bd2.position.set( 350 * 0.01f, 350 * 0.01f );
		bd2.angle = 0;
		
		// Physical information about the dynamic body
		body2 = world.createBody( bd2 );
		FixtureDef fd2 = new FixtureDef();
		fd2.density = 20.0f;
		fd2.restitution = 0.0f;
		fd2.friction = 2.0f;
		fd2.shape = new CircleShape();
		fd2.shape.setRadius( 20 * 0.01f );
		
		body2.createFixture( fd2 );
		fd2.shape.dispose();
		
		/**
		 * Create a motor that will make the moving box move and rotate
		 * around the anchor point (which is the center of the first box)
		 */
		physicMotor = new PhysicsMotor(body, body2, body.getWorldCenter());
		world.setGravity( new Vector2( 0, 0 ) );
		
		// Initialize the motor with a speed and torque
		physicMotor.initializeMotor( ( float) ( -2.8 ), 360.0f, true );
	}

	@Override
	public void onGraphicRender(GdxGraphics g) 
	{
		// Change the background to Gray
		g.clear( Color.GRAY );
		// Render the world with the whole phsyics
        debugRenderer.render(world, g.getCamera().combined);
        PhysicsWorld.updatePhysics();
        // Draw the clock surface as background and the clock hand in front with the same angle as the motor has.
        g.drawPicture(getWindowHeight() / 2.0f, getWindowWidth() / 2.0f, clockBitmap );
        g.drawTransformedPicture(getWindowHeight() / 2.0f, getWindowWidth() / 2.0f, body2.getAngle(), 1.0f, clockSecondsBitmap );
        g.drawFilledCircle( getWindowHeight() / 2.0f, getWindowWidth() / 2.0f, 5.0f, Color.BLACK );
        g.drawSchoolLogoUpperRight();
        g.drawFPS();
	}
	
	public static void main( String args[] )
	{
		new DemoRotateMotor( false );
	}
}
