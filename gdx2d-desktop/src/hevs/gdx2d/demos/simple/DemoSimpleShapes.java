package hevs.gdx2d.demos.simple;

import hevs.gdx2d.components.geometry.Vector2D;
import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to 
 * display something with the library
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleShapes extends PortableApplication{

	public DemoSimpleShapes(boolean onAndroid) {
		super(onAndroid);
	}
	
	@Override
	public void onInit() {	
		this.setTitle("Simple shapes, mui 2013");
	}
	
	@Override
	public void onGraphicRender(GdxGraphics g) {	
		g.clear();
		
		// Draws a yellow circle
		g.setColor(Color.YELLOW);
		g.drawCircle(250, 400, 20);
		
		// Draws a green rectangle
		g.setColor(Color.GREEN);
		g.drawRectangle(20,250,40,40,0);
		
		// Some filled shapes
		g.drawFilledCircle(400, 100, 30, Color.PINK);
		g.drawFilledCircle(400, 100, 20, Color.ORANGE);
		g.drawFilledCircle(400, 100, 10, Color.MAGENTA);
		
		// TODO Fix this, not working properly
		g.drawFilledBorderedCircle(300, 100, 40, Color.BLUE, Color.WHITE);
		
		g.drawFilledRectangle(100, 250, 10, 10, 0, Color.RED);
		g.drawFilledRectangle(80, 30, 20, 20, 0, new Color(0.5f, 0.5f, 0.5f, 0.5f));
		
		// Draws a blue polygon
		Vector2D points[] = {
				new Vector2D(200, 200),
				new Vector2D(250, 250),
				new Vector2D(300, 200)				
		};
		
		Polygon p = new Polygon(points);
		g.drawFilledPolygon(p, Color.BLUE);				
	}
		
	public static void main(String args[]){
		new DemoSimpleShapes(false);
	}
}
