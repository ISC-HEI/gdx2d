package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Shows how to interwind shaders and normal GDX operations
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderPartialTextureRendering extends PortableApplication {

	public DemoShaderPartialTextureRendering(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Partial screen shader demo, mui 2013");
	}

	double t = 0;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null)
			g.setShader("data/shader/bicolor.fp", 200, 200);

		g.clear();
		g.drawFPS();
		g.drawShader(256, (int) (256 + 128.0 * Math.sin(t)), 0);
		g.drawSchoolLogo();

		t += 0.05;
	}

	public static void main(String args[]) {
		new DemoShaderPartialTextureRendering(false);
	}
}
