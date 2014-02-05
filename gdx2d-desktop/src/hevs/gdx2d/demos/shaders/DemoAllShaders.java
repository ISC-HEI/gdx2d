package hevs.gdx2d.demos.shaders;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Shows how to interwind shaders and normal GDX operations
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoAllShaders extends PortableApplication {

	public DemoAllShaders(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("All the shaders - Right click to change, mui 2013");
		Logger.log("Right click to change the shader");
		mouse.x = this.getWindowWidth() / 2;
		mouse.y = this.getWindowHeight() / 2;
	}

	public final String[] shaders = {"underwater.fp", "galaxy.fp", "joyDivision.fp",
			"colorRect.fp", "plasma.fp", "gradient.fp", "particles.fp", "pulse.fp",
			"circles/circle3.fp", "advanced/vignette.fp" };

	private float time = 0;
	private int current = 0;
	private int old = current;

	private Vector2 mouse = new Vector2();

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/" + shaders[current]);
			g.shaderRenderer.addTexture("data/images/lena.png", "texture0");
		}

		if (current != old) {
			g.setShader("data/shader/" + shaders[current]);
			g.shaderRenderer.addTexture("data/images/lena.png", "texture0");
			Logger.log("Current shader set to " + shaders[current]);
			old = current;
		}

		g.clear();

		// Draws the shader
		g.shaderRenderer.setUniform("mouse", mouse);
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);

		g.drawFPS();
		g.drawStringCentered((int) (0.98 * g.getScreenHeight()), "Shader demo \""
				+ shaders[current] + "\" " + (current+1)+ "/" + (shaders.length));
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);

		if (button == Buttons.RIGHT)
			current = (current + 1) % shaders.length;

		mouse.x = x;
		mouse.y = y;
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		mouse.x = x;
		mouse.y = y;
	}

	public static void main(String args[]) {
		new DemoAllShaders(false);
	}
}
