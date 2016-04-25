package ch.hevs.gdx2d.demos.physics.pinball;

import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

public class ScoreAnimation implements TemporaryDrawable {

	private float duration;
	private String msg;
	private float x;
	private float y;
	private float start_duration;

	public ScoreAnimation(String msg, float x, float y) {
		this.start_duration = 1.0f;
		this.duration = 1.0f;
		this.msg = msg;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(GdxGraphics g) {
		duration -= Gdx.graphics.getDeltaTime();
		g.setColor(new Color(Color.BLACK).mul(duration/start_duration));
		g.drawString(x, y, msg, Align.center);
	}

	@Override
	public boolean isDone() {
		return duration < 0;
	}
}
