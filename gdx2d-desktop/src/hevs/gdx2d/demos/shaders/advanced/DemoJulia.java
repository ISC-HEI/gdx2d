package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.math.Vector2;

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

	float scaling = 0.35f;
	int direction = 1;
	float scale = 0.55f;
	Vector2 offset = new Vector2(0, 0);

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/julia.fp");
			g.shaderRenderer.addTexture("data/shader/pal.png", "texture0");
		}

		g.shaderRenderer.setUniform("scale", scale);
		g.shaderRenderer.setUniform("offset", offset);
		g.shaderRenderer.setUniform("center", new Vector2(scaling, scaling));
		scaling += direction * (10 / 20000.0f);
		
		if (scaling < 0.35 || scaling > 0.4)
			direction *= -1;
		
		g.clear();
		g.drawShader(t);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onScroll(int amount) {
		super.onScroll(amount);
		scale += amount * 0.03;
	}
	
	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		super.onPan(x, y, deltaX, deltaY);
		offset.x -= deltaX / 200.0;
		offset.y -= deltaY / 200.0;
	}

	public static void main(String args[]) {
		new DemoJulia(false);
	}
}
