package hevs.gdx2d.demos.shaders.advanced;

import com.badlogic.gdx.Gdx;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Demonstrates the use of multiple textures in a shader
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.5
 */
public class DemoMultipleTextures extends PortableApplication {

	float time = 0;
	int i = 0;

	public static void main(String args[]) {
		new DemoMultipleTextures();
	}

	@Override
	public void onInit() {
		this.setTitle("Multiple textures passing to shader, mui 2013");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/advanced/multiple_textures.fp");
			g.getShaderRenderer().addTexture("data/images/lena.jpg", "texture0");
			g.getShaderRenderer().addTexture("data/images/mandrill.jpg", "texture1");
		}

		g.getShaderRenderer().setUniform("textureChosen", i);

		g.clear();
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		i = (i + 1) % 2;
	}
}
