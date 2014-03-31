package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Gdx;

/**
 * Demonstrates the use of multiple textures in a shader
 * @author Pierre-André Mudry (mui)
 * @version 0.5
 */
public class DemoMultipleTextures extends PortableApplication{
	
	public DemoMultipleTextures(boolean onAndroid) {
		super(onAndroid);
	}
	
	@Override
	public void onInit() {	
		this.setTitle("Multiple textures passing to shader, mui 2013");
	}
	
	float time = 0;
	int i = 0;
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if(g.getShaderRenderer() == null){
			g.setShader("data/shader/advanced/multiple_textures.fp");
			g.getShaderRenderer().addTexture("data/images/lena.png", "texture0");			
			g.getShaderRenderer().addTexture("data/images/mandrill.png", "texture1");
		}
		
		g.getShaderRenderer().setUniform("textureChosen", i);
		
		g.clear();
		time+= Gdx.graphics.getDeltaTime();			
		g.drawShader(time);		
		g.drawFPS();		
		g.drawSchoolLogo();
	}
	
	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		i = (i + 1) % 2;
	}
	
	public static void main(String args[]){
		new DemoMultipleTextures(false);
	}
}
