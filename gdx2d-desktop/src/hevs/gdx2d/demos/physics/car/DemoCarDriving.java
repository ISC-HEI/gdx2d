package hevs.gdx2d.demos.physics.car;

import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;
import hevs.gdx2d.stuff.Car;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class DemoCarDriving extends PortableApplication {

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Car c1;

	DemoCarDriving(boolean onAndroid) {
		super(onAndroid, 800, 800);
	}

	@Override
	public void onInit() {
		Logger.log("Use the arrows to move the car");

		// No gravity in this world
		world.setGravity(new Vector2(0, 0));

		dbgRenderer = new DebugRenderer();

		// Create the obstacles in the scene
		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());

		// Our car
		c1 = new Car(90, 120, new Vector2(200, 200), (float) Math.PI, 10, 30, 20);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			c1.accelerate = true;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			c1.brake = true;
		else{
			c1.accelerate = false;
			c1.brake = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			c1.steer_left = true;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			c1.steer_right = true;
		else
		{
			c1.steer_left = false;
			c1.steer_right = false;
		}

		c1.update(Gdx.graphics.getDeltaTime());

		c1.draw(g);
		
		// Physik update
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		dbgRenderer.render(world, g.getCamera().combined);
		
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoCarDriving(false);
	}
}
