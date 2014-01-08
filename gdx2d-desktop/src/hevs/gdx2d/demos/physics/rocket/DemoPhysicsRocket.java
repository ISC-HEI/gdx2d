package hevs.gdx2d.demos.physics.rocket;

import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * FIXME finish this Demonstrates the use of applyForce to physics objects
 * 
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class DemoPhysicsRocket extends PortableApplication {
	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Spaceship ship;

	DemoPhysicsRocket(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		Logger.log("Use the arrows to move the spaceship");

		// No gravity in this world
		world.setGravity(new Vector2(0, 0));

		dbgRenderer = new DebugRenderer();

		// Create the obstacles in the scene
		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());
		//new PhysicsStaticBox("wall", new Vector2(250, 250), 100, 10);

		// Our spaceship
		ship = new Spaceship(new Vector2(getWindowWidth() / 2, getWindowHeight() / 2));
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		
		dbgRenderer.render(world, g.getCamera().combined);
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		ship.draw(g);
	}

	@Override
	public void onKeyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.LEFT:
			ship.thrustLeft = false;
			break;
		case Input.Keys.RIGHT:
			ship.thrustRight = false;
			break;
		case Input.Keys.UP:
			ship.thrustUp = 0;
			break;
		default:
			break;
		}

	}

	@Override
	public void onKeyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.LEFT:
			ship.thrustLeft = true;
			break;
		case Input.Keys.RIGHT:
			ship.thrustRight = true;
			break;
		case Input.Keys.UP:
			ship.thrustUp = ship.MAX_THRUST;
			break;

		default:
			break;
		}
	}

	public static void main(String[] args) {
		new DemoPhysicsRocket(false);
	}
}