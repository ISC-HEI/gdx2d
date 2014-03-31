package hevs.gdx2d.demos.shaders.simple;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;

/**
 * A nice particle shader. Shader code taken from
 * http://glsl.heroku.com/e#13789.0
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoShaderParticles extends PortableApplication {

	public DemoShaderParticles(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("A nice particle shader demo, mui 2013");
	}

	private float time = 0;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null)
			g.setShader("data/shader/particles.fp");

		g.clear();

		// Draws the shader
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);

		g.drawStringCentered(getWindowHeight() * 0.95f,
				"Original shader code from http://glsl.heroku.com/e#13789.0");
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String args[]) {
		new DemoShaderParticles(false);
	}
}
