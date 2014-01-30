package hevs.gdx2d.demos.shaders.circles;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Draws a simple, yet ugly, circle. This uses a shader.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoShaderMouse1 extends PortableApplication {

	Circle c;

	public DemoShaderMouse1(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Simple circle shader, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/circles/circle1.fs");
			// Pass the some information to the shader.
			g.shaderRenderer.setUniform("center", c.pos);
		}

		g.clear();
		g.drawShader();
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String args[]) {
		new DemoShaderMouse1(false);
	}
}
