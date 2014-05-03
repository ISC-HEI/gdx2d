package hevs.gdx2d.demos.simple.polygons;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.Rectangle2D;
import math.geom2d.polygon.SimplePolygon2D;

class DrawablePoly implements DrawableObject {
	Polygon2D shape;

	public DrawablePoly(double[] x, double[] y){
		shape = new SimplePolygon2D(x, y);
	}
	
	
	@Override
	public void draw(GdxGraphics g) {
		
		// TODO
		
	}
}

public class DemoPolygonIntersection extends PortableApplication {
	Polygon2D p1, p2;

	public DemoPolygonIntersection() {
		super(false);
	}

	@Override
	public void onInit() {
		p1 = new Rectangle2D(10, 10, 40, 40);
		p1 = new Rectangle2D(40, 30, 30, 30);

	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

	}

}
