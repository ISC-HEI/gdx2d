package hevs.gdx2d.demos.shaders.advanced;

import com.badlogic.gdx.Gdx;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Demonstrates the use of a texture in a shader
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoTexture extends PortableApplication{

	public DemoTexture(boolean onAndroid) {
		super(onAndroid);
	}
	
	@Override
	public void onInit() {	
		this.setTitle("Texture shader / simple animation, mui 2013");
		Logger.log("Click to change picture");
	}
	
	float t = 0;
	boolean clicked = false;
	boolean image1 = true;
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if(g.shaderRenderer == null){
			g.setShader("data/shader/advanced/texture.fp");
			g.shaderRenderer.setTexture("data/images/lena.png");
		}
		
		if(clicked){
			if(image1)
				g.shaderRenderer.setTexture("data/images/mandrill.png");
			else
				g.shaderRenderer.setTexture("data/images/lena.png");
			image1 = !image1;
			clicked = false;
		}
		
		g.clear();
		t+= Gdx.graphics.getDeltaTime();			
		g.drawShader(t);		
		
		g.drawFPS();		
		g.drawSchoolLogo();
	}
	
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		clicked = true;
	}
	
	public static void main(String args[]){
		new DemoTexture(false);
	}
}
