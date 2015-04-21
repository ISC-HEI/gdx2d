package hevs.gdx2d.demos.tween.interpolatorengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import hevs.gdx2d.demos.tween.Ball;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Demonstrates the usage of interpolation (tweening) for animation using the
 * {@link Interpolation} class of libgdx.
 *
 * @author Christopher Metrailler (mei)
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class DemoPositionInterpolator extends PortableApplication {

	final float ANIMATION_LENGTH = 1.3f; // Animation length (in seconds)
	float currentTime = 0f; // In seconds
	int direction = 1; // Direction of movement
	private Ball[] balls;
	private int height, width, margin;

	public DemoPositionInterpolator(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String args[]) {
		new DemoPositionInterpolator(false);
	}

	@Override
	public void onInit() {
		setTitle("Position interpolators, mei/mui 2013");

		margin = Gdx.graphics.getWidth() / 8;
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		balls = new Ball[8];

		for (int i = 0; i < 8; i++)
			balls[i] = new Ball(margin, height * (i + 1) / 10f);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Create an infinite "Yoyo effect"
		final float animationPercentage = computePercentage();

		// Apply different types of interpolation to the balls between start position and
		// end position of the X attribute of the ball
		final float start = margin;
		final float end = width - margin;

		balls[0].posx = Interpolation.linear.apply(start, end, animationPercentage);
		balls[1].posx = Interpolation.elastic.apply(start, end, animationPercentage);
		balls[2].posx = Interpolation.sine.apply(start, end, animationPercentage);
		balls[3].posx = Interpolation.bounce.apply(start, end, animationPercentage);
		balls[4].posx = Interpolation.circle.apply(start, end, animationPercentage);
		balls[5].posx = Interpolation.swing.apply(start, end, animationPercentage);
		balls[6].posx = Interpolation.pow2.apply(start, end, animationPercentage);
		balls[7].posx = Interpolation.exp10.apply(start, end, animationPercentage);

		// Do the drawing
		g.clear();

		// Draw the two red boundaries
		g.setColor(Color.RED);
		g.drawLine(margin, height * 1 / 10f, margin, height * 8 / 10f);
		g.drawLine(width - margin, height * 1 / 10f, width - margin, height * 8 / 10f);

		// Draw the balls
		for (int i = 0; i < balls.length; i++) {
			balls[i].draw(g, 0.5f);
		}

		g.drawFPS();
		g.drawSchoolLogoUpperRight();
	}

	private float computePercentage() {
		if (direction == 1)
			currentTime += Gdx.graphics.getDeltaTime();
		else
			currentTime -= Gdx.graphics.getDeltaTime();

		if (currentTime >= ANIMATION_LENGTH || currentTime <= 0)
			direction *= -1;

		return currentTime / ANIMATION_LENGTH;
	}
}