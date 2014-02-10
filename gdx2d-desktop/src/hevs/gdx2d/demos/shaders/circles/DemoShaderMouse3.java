package hevs.gdx2d.demos.shaders.circles;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;

/**
 * A circle that moves with the mouse, giggles and 
 * has a nice color.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.3
 */
public class DemoShaderMouse3 extends PortableApplication {

	Circle c;

	public DemoShaderMouse3(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Mouse shader interactions #2, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
		Logger.log("Press mouse anywhere to move the circle to that location");
	}
	
	float t = 0;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/circles/circle3.fp");
			g.shaderRenderer.setUniform("radius", 30f);
		}

		g.clear();

		// Pass the mouse position to the shader, always
		g.shaderRenderer.setUniform("mouse", c.pos);
		t += Gdx.graphics.getDeltaTime();
		g.drawShader(t);

		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);			
		c = new Circle(x,y);
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		c = new Circle(x, y);
	}

	public static void main(String args[]) {
		new DemoShaderMouse3(false);
	}
}
