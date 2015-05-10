package hevs.gdx2d.components.screen_management.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

/**
 * From LibGDX Game Development Second Edition
 */
public class SmoothTransition implements ScreenTransition {
	private static final SmoothTransition instance = new SmoothTransition();
	private float duration;

	public static SmoothTransition init(float duration) {
		instance.duration = duration;
		return instance;
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public void render(SpriteBatch batch, Texture currScreen,
					   Texture nextScreen, float alpha) {

		float w = currScreen.getWidth();
		float h = currScreen.getHeight();

		alpha = Interpolation.fade.apply(alpha);

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
			batch.setColor(1, 1, 1, 1);
			batch.draw(currScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0,
				currScreen.getWidth(), currScreen.getHeight(),
				false, true);
			batch.setColor(1, 1, 1, alpha);
			batch.draw(nextScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0,
				nextScreen.getWidth(), nextScreen.getHeight(),
				false, true);
		batch.end();
	}
}