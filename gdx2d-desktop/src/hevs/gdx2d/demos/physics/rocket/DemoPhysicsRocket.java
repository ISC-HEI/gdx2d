package hevs.gdx2d.demos.physics.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Control a spaceship with your keyboard.
 * <p/>
 * Demonstrate how to apply forces to physics objects and how to draw images
 * (textures) for them.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
public class DemoPhysicsRocket extends PortableApplication {

	// Physics related
	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();

	// Drawing related
	Spaceship ship;

	public DemoPhysicsRocket(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoPhysicsRocket(false);
	}

	@Override
	public void onInit() {
		setTitle("Rocket with physics");
		Logger.log("Use the arrows keys to move the spaceship.");

		// No gravity in this world
		world.setGravity(new Vector2(0, 0));

		dbgRenderer = new DebugRenderer();

		// Create the obstacles in the scene
		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());

		// Our spaceship
		ship = new Spaceship(new Vector2(getWindowWidth() / 2,
				getWindowHeight() / 2));
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		// Draw the DebugRenderer as helper
		dbgRenderer.render(world, g.getCamera().combined);

		ship.draw(g); // Draw the spaceship image
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
				ship.thrustUp = Spaceship.MAX_THRUST;
				break;
			default:
				break;
		}
	}
}