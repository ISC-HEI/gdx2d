package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;

/**
 * Demonstrates the use of a texture in a shader
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
public class DemoMultipleTextures extends PortableApplication{
	
	public DemoMultipleTextures(boolean onAndroid) {
		super(onAndroid);
	}
	
	@Override
	public void onInit() {	
		this.setTitle("Texture shader / heat diffusion animation, mui 2013");
	}
	
	float t = 0;
	
	int i = 0;
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if(g.shaderRenderer == null){
			g.setShader("data/shader/advanced/multiple_textures.fp");
			g.shaderRenderer.setTexture("data/images/lena.png", 0);			
			g.shaderRenderer.addTexture("data/images/mandrill.png");
		}
		
		g.shaderRenderer.setUniform("textureChosen", i);
		
		g.clear();
		t+= Gdx.graphics.getDeltaTime();			
		g.drawShader(t);		
		
		g.drawFPS();		
		g.drawSchoolLogo();
	}
	
	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		i = (i + 1) % 2;
		System.out.println(i);
	}
	
	public static void main(String args[]){
		new DemoMultipleTextures(false);
	}
}
