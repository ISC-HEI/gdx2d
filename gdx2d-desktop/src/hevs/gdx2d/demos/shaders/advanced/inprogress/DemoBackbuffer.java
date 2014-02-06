package hevs.gdx2d.demos.shaders.advanced.inprogress;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoBackbuffer extends PortableApplication {

	public DemoBackbuffer(boolean onAndroid) {
		super(onAndroid);
	}

	Vector2 mouse;

	@Override
	public void onInit() {
		this.setTitle("Backbuffer - mui 2013");
		Logger.log("Click to change shader");
		fbo = new FrameBuffer(Format.RGBA8888, this.getWindowWidth(), this.getWindowHeight(), false);
		mouse = new Vector2(0, 0);
	}

	int currentMatrix = 0;

	float time = 0;

	// Used for off screen rendering
	FrameBuffer fbo;

	Texture t;
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.shaderRenderer == null) {
			g.setShader("data/shader/advanced/tbd/watermix.fp");
		}
		
		t = fbo.getColorBufferTexture();

		fbo.begin();
			g.clear();
			g.drawSchoolLogo();
			g.drawFilledCircle(256, 256, 20, Color.BLUE);
			g.drawFilledCircle(0, 0, 20, Color.BLUE);
			g.drawFilledCircle(400, 400, 20, Color.BLUE);
			g.spriteBatch.draw(t, 256, 256);
			g.drawShader(time);
		fbo.end();

		// Copy the offscreen buffer to the displayed bufer
		g.shaderRenderer.setTexture(fbo.getColorBufferTexture(),0);
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouse.x = x;
		mouse.y = y;
	}

	public static void main(String args[]) {
		new DemoBackbuffer(false);
	}
}
