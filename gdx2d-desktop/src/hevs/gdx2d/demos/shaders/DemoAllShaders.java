package hevs.gdx2d.demos.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * A demo that shows many shaders, some of them from
 * Heroku, some of them original. The source of the shader
 * is always clearly indicated in the .fp file.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoAllShaders extends PortableApplication {

	public final String[] shaders = {"underwater.fp", "galaxy.fp",
			"joyDivision.fp", "stars.fp", "colorRect.fp", "plasma.fp", "gradient.fp",
			"particles.fp", "pulse.fp", "circles/circle3.fp",
			"advanced/vignette.fp"};
	private float time = 0;
	private int currentShaderID = 0;
	private int previousShaderID = currentShaderID;
	private Vector2 mouse = new Vector2();

	public static void main(String args[]) {
		new DemoAllShaders();
	}

	@Override
	public void onInit() {
		this.setTitle("Shaders demos (some from Heroku), right click to change, mui 2014");
		Logger.log("Right click to change the shader");
		mouse.x = this.getWindowWidth() / 2;
		mouse.y = this.getWindowHeight() / 2;
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/" + shaders[currentShaderID]);
			g.getShaderRenderer().addTexture("data/images/lena.png", "texture0");
		}

		if (currentShaderID != previousShaderID) {
			g.setShader("data/shader/" + shaders[currentShaderID]);
			g.getShaderRenderer().addTexture("data/images/lena.png", "texture0");
			Logger.log("Current shader set to " + shaders[currentShaderID]);
			previousShaderID = currentShaderID;
		}

		// Clears the screen
		g.clear();

		// Draws the shader
		g.getShaderRenderer().setUniform("mouse", mouse);
		g.drawShader(time);

		// Update time
		time += Gdx.graphics.getDeltaTime();

		// Draws the rest of the stuff
		g.drawFPS();
		g.drawStringCentered((int) (0.98 * g.getScreenHeight()),
				"Shader demo \"" + shaders[currentShaderID] + "\" " + (currentShaderID + 1)
						+ "/" + (shaders.length));
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);

		if (button == Buttons.RIGHT || onAndroid())
			currentShaderID = (currentShaderID + 1) % shaders.length;

		mouse.x = x;
		mouse.y = y;
	}

	@Override
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		mouse.x = x;
		mouse.y = y;
	}
}
