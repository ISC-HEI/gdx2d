package hevs.gdx2d.demos.shaders.advanced;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class DemoBackbuffer extends PortableApplication{

	public DemoBackbuffer(boolean onAndroid) {
		super(onAndroid);
	}
	
	Vector2 mouse;
	
	@Override
	public void onInit() {	
		this.setTitle("Backbuffer - mui 2013");
		Logger.log("Click to change shader");
		//mouse = new Vector2(this.getWindowWidth()/2, this.getWindowHeight()/2);
		mouse = new Vector2(0, 0);
	}
	
	int currentMatrix = 0;

	float time = 0;
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		if(g.shaderRenderer == null){
			g.setShader("data/shader/advanced/game_of_life.fp");			
		}
		
		g.shaderRenderer.setUniform("mouse", mouse);
		time += Gdx.graphics.getDeltaTime();
		
		g.clear();
		g.drawShader(time);		
		g.drawFPS();		
		g.drawSchoolLogo();
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouse.x = x;
		mouse.y = y;
	}
	
	
	public static void main(String args[]){
		new DemoBackbuffer(false);
	}
}
