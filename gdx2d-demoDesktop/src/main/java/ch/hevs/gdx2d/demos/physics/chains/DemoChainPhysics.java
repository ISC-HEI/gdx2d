package ch.hevs.gdx2d.demos.physics.chains;

import ch.hevs.gdx2d.components.colors.Palette;
import ch.hevs.gdx2d.components.physics.PhysicsChain;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * <p>Demonstration of chained objects. Here the chain is the thing on which the
 * balls fall.</p>
 * <p/>
 * <p>Based on ex 5.3 from the Nature of code book</p>
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The nature of code example</a>
 */
public class DemoChainPhysics extends PortableApplication {
	final LinkedList<PhysicsBall> balls = new LinkedList<PhysicsBall>();
	final Random r = new Random();

	World w;
	PhysicsChain chain;

	// The rate at which the balls are generated
	int GENERATION_RATE = 7;

	float width, height;
	boolean generate = false;

	public DemoChainPhysics() {
		super();

		if (onAndroid())
			GENERATION_RATE = 3;
	}

	public DemoChainPhysics(int w, int h) {
		super(w, h);
	}

	public static void main(String[] args) {
		new DemoChainPhysics(1000, 600);
	}

	@Override
	public void onInit() {
		this.setTitle("Physics objects in well demo, mui 2013");

		Gdx.app.log("[DemoChainPhysics]", "Left click to generate balls");
		Gdx.app.log("[DemoChainPhysics]", "Right click to generate random terrain");
		Gdx.app.log("[DemoChainPhysics]", "Middle click to generate Catmull-Rom terrain");
		Gdx.app.log("[DemoChainPhysics]", "'r' to modify rendering type");

		w = PhysicsWorld.getInstance();

		width = getWindowWidth();
		height = getWindowHeight();

		chain = new PhysicsChain(new Vector2(width / 10, height * 0.33f),
				new Vector2(width - width / 10, height * 0.33f), 8,
				PhysicsChain.chain_type.CATMUL);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.LIGHT_GRAY);
		chain.draw(g);

		// Draws the balls
		for (Iterator<PhysicsBall> iter = balls.iterator(); iter.hasNext(); ) {
			PhysicsBall ball = iter.next();
			ball.draw(g);

			Vector2 p = ball.getBodyPosition();

			// If a ball is not visible anymore, it should be destroyed
			if (p.y > height || p.y < 0 || p.x > width || p.x < 0) {
				// Mark the ball for deletion when possible
				ball.destroy();

				// Remove the ball from the collection as well
				iter.remove();
			}
		}

		g.drawFPS();
		g.drawString(5, 30, "#Obj: " + w.getBodyCount());
		g.drawSchoolLogo();

		// Generate new balls if required
		if (generate) {
			for (int i = 0; i < GENERATION_RATE; i++) {
				float x = width / 10 + r.nextFloat() * (width - width / 10);
				float y = height * 0.8f + r.nextInt(10);
				final Vector2 position = new Vector2(x, y);
				final PhysicsBall b = new PhysicsBall(position,
						r.nextInt(10) + 6,
						Palette.pastel2[r.nextInt(Palette.pastel2.length)]);
				balls.add(b);
			}
		}

		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);

		if (keycode == Input.Keys.R) {
			PhysicsBall.change_rendering();
		}
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);

		if (button == Input.Buttons.LEFT)
			generate = true;

		if (button == Input.Buttons.MIDDLE)
			chain.catmull_chain(5);

		if (button == Input.Buttons.RIGHT)
			chain.random_chain(15);
	}

	@Override
	public void onTap(float x, float y, int count, int button) {
		super.onTap(x, y, count, button);
		if (count == 2) {
			chain.catmull_chain(5);
		}
	}

	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		generate = false;
	}

}
