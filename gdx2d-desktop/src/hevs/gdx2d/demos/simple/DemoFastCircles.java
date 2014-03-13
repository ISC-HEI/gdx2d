package hevs.gdx2d.demos.simple;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.graphics.Color;

public class DemoFastCircles extends PortableApplication {

	public DemoFastCircles() {
		super(false);
	}
	
	@Override
	public void onInit() {

	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();
		
		g.drawAntiAliasedCircle(250, 100, 50, Color.BLUE);
		g.drawAntiAliasedCircle(150, 100, 50, Color.BLUE);
		g.drawAntiAliasedCircle(200, 100, 50, Color.BLUE);
		g.drawAntiAliasedCircle(100, 100, 20, Color.BLUE);
		
		g.drawFPS();
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		new DemoFastCircles();

	}

}
