package hevs.gdx2d.demos.shaders;

import hevs.gdx2d.components.geometry.Vector2D;
import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * 
 * Shows how to interwind shaders and normal GDX operations
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoShaderSimple extends PortableApplication{

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
		if(g.shaderRenderer == null)
			g.setShader("data/shader/pulse.fs");
		
		g.clear();
		g.drawFPS();
		
		// Draws the shader
		time+=Gdx.graphics.getDeltaTime();
		g.drawShader(time);

		g.drawFilledCircle(125, 100, 40, Color.BLUE);
		g.drawFilledRectangle(120, 150, 30, 30, 0, Color.RED);
		g.drawFilledRectangle(80, 30, 20, 20, 0, new Color(0.5f, 0.5f, 0.5f, 0.5f));
		g.drawSchoolLogo();
	}
		
	public static void main(String args[]){
		new DemoShaderSimple(false);
	}
}
