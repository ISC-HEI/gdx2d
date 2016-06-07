package ch.hevs.gdx2d.demos.shaders.circles;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;

/**
 * A circle that moves with the mouse, giggles and
 * has a nice color.
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.3
 */
public class DemoShaderMouse3 extends PortableApplication {

	Circle c;
	float time = 0;

	public static void main(String[] args) {
		new DemoShaderMouse3();
	}

	@Override
	public void onInit() {
		this.setTitle("Mouse shader interactions #2, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
		Logger.log("Press mouse anywhere to move the circle to that location");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/circles/circle3.fp");
			g.getShaderRenderer().setUniform("radius", 30f);
		}

		g.clear();

		// Pass the mouse position to the shader, always
		g.getShaderRenderer().setUniform("mouse", c.pos);
		time += Gdx.graphics.getDeltaTime();

		g.drawShader(time);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		c = new Circle(x, y);
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		c = new Circle(x, y);
	}
}
