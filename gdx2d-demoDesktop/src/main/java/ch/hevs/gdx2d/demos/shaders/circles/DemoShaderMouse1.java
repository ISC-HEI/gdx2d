package ch.hevs.gdx2d.demos.shaders.circles;


import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * Draws a simple, yet ugly, circle. This uses a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoShaderMouse1 extends PortableApplication {

	Circle c;

	@Override
	public void onInit() {
		this.setTitle("Simple circle shader, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.getShaderRenderer() == null) {
			g.setShader("shader/circles/circle1.fp");
			// Pass the some information to the shader.
			g.getShaderRenderer().setUniform("center", c.pos);
		}

		g.clear();
		g.drawShader();
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoShaderMouse1();
	}
}
