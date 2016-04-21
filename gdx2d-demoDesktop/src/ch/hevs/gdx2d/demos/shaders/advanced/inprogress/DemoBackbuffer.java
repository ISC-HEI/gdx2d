package ch.hevs.gdx2d.demos.shaders.advanced.inprogress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoBackbuffer extends PortableApplication {

	Vector2 mouse;
	int currentMatrix = 0;
	float time = 0;
	// Used for off screen rendering
	FrameBuffer fbo;
	Texture t;

	public static void main(String args[]) {
		new DemoBackbuffer();
	}

	@Override
	public void onInit() {
		this.setTitle("Backbuffer - mui 2013");
		fbo = new FrameBuffer(Format.RGBA8888, this.getWindowWidth(), this.getWindowHeight(), false);
		mouse = new Vector2(0, 0);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("data/shader/advanced/tbd/test.fp");
			t = new Texture(512, 512, Format.RGB888);
			g.getShaderRenderer().addTexture(t, "backbuffer");
		}

		fbo.begin();
		g.clear();
//			g.drawFilledCircle(256, 256, 20, Color.RED);
//			g.drawFilledCircle(0, 0, 20, Color.GREEN);
		g.drawFilledCircle(400, 400, 20, Color.YELLOW);
//			g.drawSchoolLogo();
		g.sbFlush();
		fbo.end();

		// Copy the offscreen buffer to the displayed bufer
		t = fbo.getColorBufferTexture();
		t.bind(1);
		//g.shaderRenderer.setTexture(t, 1);

//		g.clear();
//		g.spriteBatch.draw(t, 0, 0);

		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouse.x = x;
		mouse.y = y;
	}
}
