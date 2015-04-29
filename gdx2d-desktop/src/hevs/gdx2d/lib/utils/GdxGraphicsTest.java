package hevs.gdx2d.lib.utils;

import com.badlogic.gdx.graphics.Color;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * Torture test for graphics primitives 
 * @author Marc Pignat (pim)
 * 
 * Test all grapics primitives in different order, colors, zoom...
 */
public class GdxGraphicsTest extends PortableApplication {

	public GdxGraphicsTest(boolean onAndroid) {
		super(onAndroid, 512, 512);
	}

	public static void main(String args[]) {
		new GdxGraphicsTest(false);
	}

	float zoom = 1.0f;
	boolean zoom_up = true;
	
	@Override
	public void onInit() {
		
	}
	
	void manage_zoom()
	{
		if (zoom_up)
		{
			zoom *= 1.01;
			if (zoom > 1.2)
			{
				zoom_up = false;
			}
		}
		else
		{
			zoom *= 0.99;
			if (zoom < 0.8)
			{
				zoom_up = true;
			}
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		
		manage_zoom();
		
		g.clear();
		g.zoom(zoom);
		
		for (int i = 0 ; i < 20 ; i++)
		{
			g.setPixel(40+i, 0+i, Color.BLUE);
		}
		g.drawCircle(10, 10, 10);
		g.drawFilledCircle(30, 10, 10, Color.RED);
		
		for (int i = 0 ; i < 20 ; i++)
		{
			g.setPixel(60-i, 0+i, Color.BLUE);
		}
		
		g.drawRectangle(70, 10, 10, 10, 30);
		g.drawFilledRectangle(90, 10, 10, 10, 60, Color.CYAN);
	}

}
