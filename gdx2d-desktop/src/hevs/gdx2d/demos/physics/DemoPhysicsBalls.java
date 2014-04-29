package hevs.gdx2d.demos.physics;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A demo for physics, based on box2d
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoPhysicsBalls extends PortableApplication {

	LinkedList<PhysicsCircle> list = new LinkedList<PhysicsCircle>();	
	
	// A world with gravity, pointing down
	World world = PhysicsWorld.getInstance();
	DebugRenderer debugRenderer;
	BitmapImage img;

	boolean hasAccelerometers;
	
	public DemoPhysicsBalls(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {			
		
		setTitle("Physics demo with box2d, mui 2013");
		Logger.log("Click to create a ball");
		
		hasAccelerometers = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		
		world.setGravity(new Vector2(0, -10));
		
		// The walls
		new PhysicsScreenBoundaries(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
		// Used to display debug information about the physics
		debugRenderer = new DebugRenderer();
		img = new BitmapImage("data/images/soccer.png");
	}

	
	/**
	 * Adds a ball at a given location
	 * @param x
	 * @param y
	 */
	public void addBall(int x, int y) {				
		// If there exists enough ball already, remove the oldest one
		if(list.size() > 50){
			PhysicsCircle b = list.poll();
			b.destroy();			
		}	
		
		float size = (float) ((Math.random() * 15.0f)) + 15f;
		PhysicsCircle b = new PhysicsCircle(null, new Vector2(x, y), size);
		
		// Add the ball to the list of existing balls
		list.add(b);
	}
	
	
	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);

		if (button == Input.Buttons.LEFT)
			addBall(x, y);			
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();		
		
//		debugRenderer.render(world, g.getCamera().combined);		
		
		// For every body in the world
		for(Iterator<PhysicsCircle> it = list.iterator(); it.hasNext();){
			PhysicsCircle c = it.next();
			Float radius = c.getBodyRadius();		
			Vector2 pos = c.getBodyPosition();
			g.drawTransformedPicture(pos.x, pos.y, c.getBodyAngleDeg(), radius, radius, img);
			c.setBodyAwake(true);			
		}

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}

	// For low-pass filtering accelerometer
	private double smoothedValue = 0;
	private final double SMOOTHING = 30; // This value changes the dampening effect of the low-pass	
	
	/**
	 * Called periodically
	 */
	@Override
	public void onGameLogicUpdate() {		
	
		if(hasAccelerometers){
			// On tablet, orientation is different than on phone
			Orientation nativeOrientation = Gdx.input.getNativeOrientation();
			
			float accel;
			
			if(nativeOrientation == Orientation.Landscape)
				accel = -Gdx.input.getAccelerometerY();
			else
				accel = Gdx.input.getAccelerometerX();																			
			
			// Low pass filtering of the value
			smoothedValue += (accel - smoothedValue) / SMOOTHING;
			world.setGravity(new Vector2(-(float)(smoothedValue), -10));				
		}
	}

	public static void main(String[] args) {
		new DemoPhysicsBalls(false);
	}
}
