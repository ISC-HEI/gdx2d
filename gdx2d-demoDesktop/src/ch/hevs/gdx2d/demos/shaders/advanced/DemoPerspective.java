package ch.hevs.gdx2d.demos.shaders.advanced;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;

/**
 * Demonstrates how to use a shader to postprocess
 * an image generated by the library. The idea is to generate
 * everything you want in a backbuffer (~ a texture)
 * then copy it to the active buffer and then apply a
 * shader to which the texture data is the rendered image.
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class DemoPerspective extends PortableApplication {
	float time = 0;
	boolean shaderEnabled = true;

	// Used for off screen rendering
	FrameBuffer fbo;

	// Standard images used for drawing
	BitmapImage imageAndroid, imageBackground;

	float cameraFov   = (float) (Math.PI/6.0f);  //Horizontal field of vision
	float cameraAngle = (float) (Math.PI/10.0f);  //Horizontal field of vision
	Vector3 cameraPosition = new Vector3(0.0f, -1.0f, 0.02f);
	Vector3 cameraAxis = new Vector3(1.0f, 0.0f, 0.0f);
	
	@Override
	public void onInit() {
		this.setTitle("Postprocessing with a shader, mui 2013");
		imageAndroid = new BitmapImage("images/Android_PI_48x48.png");
		imageBackground = new BitmapImage("images/back1_512.png");
		fbo = new FrameBuffer(Format.RGBA8888, this.getWindowWidth(), this.getWindowHeight(), false);
		Logger.log("Click to enable/disable shader");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if (g.getShaderRenderer() == null) {
			g.setShader("shader/advanced/perspective.fp");
		}

		// Draws some stuff to an offscreen buffer, using normal
		// gdx2d primitives
		fbo.begin();
			g.clear();
			g.drawPicture(256, 256, imageBackground);
			g.drawTransformedPicture(256, 256, time * 100, 1, imageAndroid);
			g.drawFPS();
			g.drawSchoolLogo();
			g.sbFlush();
		fbo.end();


		// Copy the offscreen buffer to the displayed bufer
		g.getShaderRenderer().setTexture(fbo.getColorBufferTexture(), 0);
		g.getShaderRenderer().setUniform("enabled", shaderEnabled);
		g.getShaderRenderer().setUniform("cameraPosition", cameraPosition);
		g.getShaderRenderer().setUniform("cameraAxis", cameraAxis);
		g.getShaderRenderer().setUniform("screenPlanDistance", (float) (0.5 / Math.atan(cameraFov/2.0)));
		g.getShaderRenderer().setUniform("cameraAngle", cameraAngle);

		//Move the camera
		cameraPosition.y *= 1 + 0.1f * Gdx.graphics.getDeltaTime();
		cameraPosition.y -= 0.1f * Gdx.graphics.getDeltaTime();
		cameraPosition.z *= 1 + 0.05f * Gdx.graphics.getDeltaTime();
		cameraPosition.z += 0.05 * Gdx.graphics.getDeltaTime();
		cameraAngle += 0.01* Gdx.graphics.getDeltaTime();
		
		time += Gdx.graphics.getDeltaTime();
		g.drawShader(time);
		System.out.println(1.0/Gdx.graphics.getDeltaTime());
	}

	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		shaderEnabled = !shaderEnabled;
	}

	public static void main(String[] args) {
		new DemoPerspective();
	}
}
