package hevs.gdx2d.demos.shaders.circles;

import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * 
 * Shows how to interwind shaders and normal GDX operations
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderMouse2 extends PortableApplication {

	class Circle {
		int x, y;

		Circle(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	Circle c;

	public DemoShaderMouse2(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Mouse shader interactions, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
		Logger.log("Press mouse anywhere to move the circle to that location");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/circles/circle2.fs");
			g.shaderRenderer.setUniform("radius", 0.05f);
		}

		g.clear();

		// Pass the mouse position to the shader, always
		g.shaderRenderer.setUniform("mouse", new Vector2(c.x, c.y));
		g.drawShader();

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
		new DemoShaderMouse2(false);
	}
}
