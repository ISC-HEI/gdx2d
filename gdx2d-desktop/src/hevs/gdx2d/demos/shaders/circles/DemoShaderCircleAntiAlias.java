package hevs.gdx2d.demos.shaders.circles;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * Demonstrates how to render an anti-aliased circles when using 
 * GLSL shader. This does not use the CPU but the GPU instead for rendering.
 * If a graphics card is supported (either on desktop or Android), this should
 * be way faster than other methods. 
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.4
 */
public class DemoShaderCircleAntiAlias extends PortableApplication {
	Circle c;

	public DemoShaderCircleAntiAlias(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Antialiasing of a circle using shaders, mui 2013");
		c = new Circle(this.getWindowWidth() / 2, this.getWindowHeight() / 2);
		Logger.log("Press mouse anywhere to move the circle to that location");
		Logger.log("Scroll mouse to change the radius");
	}
	

	private float time = 0;
	private float radius = 100;
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Sets some values, once
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/circles/circle_aa.fp");
			g.shaderRenderer.setUniform("color", new Vector3(Color.PINK.r, Color.PINK.g, Color.PINK.b));
		}

		g.clear();
		g.shaderRenderer.setUniform("radius", radius);
		g.shaderRenderer.setUniform("position", new Vector2(c.pos.x, c.pos.y));

		// Update time
		time += 3*Gdx.graphics.getDeltaTime();
		
		g.drawShader(time);
		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);			
		c = new Circle(x,y);
	}
	
	@Override
	public void onScroll(int amount) {
		super.onScroll(amount);
		radius += 8*amount;
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		c = new Circle(x, y);
	}

	public static void main(String args[]) {
		new DemoShaderCircleAntiAlias(false);
	}
}
