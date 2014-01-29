package hevs.gdx2d.demos.shaders;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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

	BitmapFont roboto;
	@Override
	public void onInit() {
		this.setTitle("A nice particle shader demo, mui 2013");

		FileHandle robotoF = Gdx.files.internal("font/RobotoSlab-Light.ttf");

		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(robotoF);
		roboto = generator.generateFont(15);
		generator.dispose();
	}

	private float time = 0;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.shaderRenderer == null)
			g.setShader("data/shader/particles.fs");

		g.clear();

		// Draws the shader
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);

		g.drawStringCentered(getWindowHeight() * 0.95f,
				"Original code from http://glsl.heroku.com/e#13789.0",
				roboto);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String args[]) {
		new DemoShaderParticles(false);
	}
}
