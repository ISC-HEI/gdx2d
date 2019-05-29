package ch.hevs.gdx2d.demos.physics.particle;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Demonstrates how to render particles
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.2
 */
public class Particle extends PhysicsBox {
	protected final int maxAge;
	// Resources MUST not be static
	protected BitmapImage img = new BitmapImage("images/texture.png");
	protected int age = 0;
	private boolean init = false;

	public Particle(Vector2 position, int radius, int maxAge) {
		super(null, position, radius, radius, 0.12f, 1f, 1f);
		this.maxAge = maxAge;

		// Particles should not collide together
		Filter filter = new Filter();
		filter.groupIndex = -1;
		f.setFilterData(filter);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		img.dispose();
	}

	public boolean shouldbeDestroyed() {
		return age > maxAge ? true : false;
	}

	public void step() {
		age++;
	}

	public void render(GdxGraphics g) {
		final Color col = g.sbGetColor();
		final Vector2 pos = getBodyPosition();

		if (!init) {
			g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
			init = true;
		}

		// Make the particle disappear with time
		g.sbSetColor(.5f, 0.7f, 0.9f, 1.0f - age / (float) (maxAge + 5));

		// Draw the particle
		g.draw(img.getRegion(), pos.x - img.getImage().getWidth() / 2, pos.y - img.getImage().getHeight() / 2);
		g.sbSetColor(col);
	}
}
