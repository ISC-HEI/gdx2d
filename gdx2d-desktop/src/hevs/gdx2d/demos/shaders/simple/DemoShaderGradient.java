package hevs.gdx2d.demos.shaders.simple;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Shows how to interwind shaders and normal GDX operations
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderGradient extends PortableApplication {

	public static void main(String args[]) {
		new DemoShaderGradient();
	}

	@Override
	public void onInit() {
		this.setTitle("Gradient shader, no animation, mui 2013");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null)
			g.setShader("data/shader/gradient.fp");

		g.clear();
		g.drawFPS();
		g.drawShader();
		g.drawSchoolLogo();
	}
}
