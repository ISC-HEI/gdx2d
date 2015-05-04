package hevs.gdx2d.demos.shaders.circles;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Shows how to pass parameters to a shader
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderMouse2 extends PortableApplication {

	Circle c;

	public static void main(String args[]) {
		new DemoShaderMouse2();
	}

	@Override
	public void onInit() {
		this.setTitle("Mouse shader interactions, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
		Logger.log("Press mouse anywhere to move the circle to that location");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/circles/circle2.fp");
			g.getShaderRenderer().setUniform("radius", 30);
		}

		g.clear();

		// Pass the mouse position to the shader, always
		g.getShaderRenderer().setUniform("mouse", c.pos);
		g.drawShader();

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
