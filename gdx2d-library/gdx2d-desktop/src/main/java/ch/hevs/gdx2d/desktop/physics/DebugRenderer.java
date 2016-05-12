package ch.hevs.gdx2d.desktop.physics;


import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.desktop.Game2D;
import ch.hevs.gdx2d.lib.utils.Utils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A renderer that already includes scaling (because the physics world is in meters and we work in pixels).
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DebugRenderer extends Box2DDebugRenderer {

  /**
   * Create a Box2D debug renderer to see physicals objects. Gdx must be loaded.
   *
   * @throws GdxRuntimeException if Gdx is not loaded
   */
	public DebugRenderer() {
		Utils.assertGdxLoaded(DebugRenderer.class);
	}

	@Override
	public void render(World world, Matrix4 projMatrix) {
		Game2D.g.beginCustomRendering();
		Matrix4 debugM = projMatrix.cpy().scale(PhysicsConstants.METERS_TO_PIXELS, PhysicsConstants.METERS_TO_PIXELS, 1);
		super.render(world, debugM);
		Game2D.g.endCustomRendering();
	}
}
