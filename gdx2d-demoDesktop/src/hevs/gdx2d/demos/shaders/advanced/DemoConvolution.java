package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Demonstrates how to program a convolution using a shader
 * FIXME This could be improved a lot
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoConvolution extends PortableApplication {

	int currentMatrix = 0;

	public static void main(String args[]) {
		new DemoConvolution();
	}

	@Override
	public void onInit() {
		this.setTitle("Texture convolution - mui 2013");
		Logger.log("Click to change shader");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/advanced/convolution.fp");
			g.getShaderRenderer().addTexture("data/images/lena.png", "texture0");
		}

		// TODO Improve this, pass the matrix directly
		g.getShaderRenderer().setUniform("matrix", currentMatrix);

		g.clear();
		g.drawShader();
		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		currentMatrix = (currentMatrix + 1) % 5;
	}
}
