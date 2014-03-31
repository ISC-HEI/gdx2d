package hevs.gdx2d.demos.shaders.simple;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;

/**
 * Shows how to interwind shaders and normal GDX operations
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderSimple extends PortableApplication {

	public DemoShaderSimple(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		this.setTitle("Simple shader demo, mui 2013");
	}

	private float time = 0;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		if(g.getShaderRenderer() == null)
			g.setShader("data/shader/colorRect.fp");
		
		g.clear();
		
		// Draws the shader
		time+=Gdx.graphics.getDeltaTime();
		g.drawShader(time);

		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String args[]) {
		new DemoShaderSimple(false);
	}
}
