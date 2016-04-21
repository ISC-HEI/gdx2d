package ch.hevs.gdx2d.demos.shaders.simple;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Shows how to interwind shaders and normal GDX operations
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderSimple extends PortableApplication {

	private float time = 0;

	public static void main(String args[]) {
		new DemoShaderSimple();
	}

	@Override
	public void onInit() {
		this.setTitle("Simple shader demo, mui 2013");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/bicolor.fp");
		}

		g.clear();

		// Draws the shader
		g.drawShader();

		g.drawFPS();
		g.drawSchoolLogo();
	}
}
