package hevs.gdx2d.demos.shaders.advanced;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Julian set as a shader, based on mei code 
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoJulia extends PortableApplication {

	public DemoJulia(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Julia set shader, mui 2013");
		Logger.log("Click to change picture");
	}

	float t = 0;
	boolean clicked = false;
	boolean image1 = true;

	Vector2 v = new Vector2(0.005f, 0.005f);

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/julia.fp");
			g.shaderRenderer.addTexture("data/shader/pal.png", "texture0");

		}

		if (clicked)
			v.x += 0.001f;

		g.shaderRenderer.setUniform("center", v);
		t += Gdx.graphics.getDeltaTime();

		g.clear();
		g.drawShader(t);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		clicked = true;
	}

	public void onRelease(int x, int y, int button) {
		clicked = false;
	}

	public static void main(String args[]) {
		new DemoJulia(false);
	}
}
