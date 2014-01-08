package hevs.gdx2d.lib.physics;


import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.GdxGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A renderer that already includes scaling (because the
 * physics world is in meters and we work in pixels)
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DebugRenderer extends Box2DDebugRenderer {	
	@Override
	public void render(World world, Matrix4 projMatrix) {
		Game2D.g.resetRenderingMode();
		Matrix4 debugM = projMatrix.cpy().scale(PhysicsConstants.METERS_TO_PIXELS, PhysicsConstants.METERS_TO_PIXELS, 1);
		super.render(world, debugM);
	}
}
