package ch.hevs.gdx2d.demos.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.components.graphics.GeomUtils;
import hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

/**
 * Demonstrates how to use general polygons as physics objects
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class DemoPolygonPhysics extends PortableApplication {

	PhysicsPolygon p1, p2;
	DebugRenderer debugRenderer;

	public static void main(String[] args) {
		new DemoPolygonPhysics();
	}

	@Override
	public void onInit() {
		// A triangle
		Vector2 obj1[] = {
				new Vector2(100, 100),
				new Vector2(200, 100),
				new Vector2(150, 200)
		};

		// A special polygon
		Vector2 obj2[] = {
				new Vector2(0, 0),
				new Vector2(4, 0),
				new Vector2(5, 3),
				new Vector2(2, 8),
				new Vector2(-1, 3)
		};

		GeomUtils.translate(obj1, new Vector2(250, 100));
		GeomUtils.rotate(obj1, 12);

		GeomUtils.scale(obj2, 20);
		GeomUtils.rotate(obj2, 14);
		GeomUtils.translate(obj2, new Vector2(100, 100));

		p1 = new PhysicsPolygon("poly1", obj1, true);
		p2 = new PhysicsPolygon("poly2", obj2, true);

		new PhysicsScreenBoundaries(this.getWindowWidth(), this.getWindowHeight());

		debugRenderer = new DebugRenderer();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		PhysicsWorld.updatePhysics(Gdx.graphics.getRawDeltaTime());
		debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera().combined);

		g.drawFilledPolygon(p1.getPolygon(), Color.YELLOW);
		g.drawFilledPolygon(p2.getPolygon(), Color.RED);

		g.drawSchoolLogo();
		g.drawFPS();
	}

}
