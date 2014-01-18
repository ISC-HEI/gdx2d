package hevs.gdx2d.demos.simple.mistfy;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A classical Mistify screen saver clone. 
 * 
 * Code adapted from http://r3dux.org/2010/11/mystify-2-0/
 * @author Pierre-André Mudry
 * @version 1.0
 * 
 */
public class DemoLines extends PortableApplication {
	BounceShape[] s;
	
	final int N_SHAPES = 3;
	int frame = 0;
	
	public DemoLines(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Mistfy screensaver clone, mui 2013");

		// Allocate size for the shapes
		s = new BounceShape[N_SHAPES];
		
		// Initialize them
		for(int i = 0; i < N_SHAPES; i++){
			s[i] = new BounceShape(this.getWindowWidth(), this.getWindowHeight());
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {		
		
		// Clears the screen
		g.clear();

		// Draws and moves the shapes
		for(BounceShape shape : s){
			shape.drawShape(g);
			shape.moveShape(g.getScreenWidth(), g.getScreenHeight());
			shape.shiftShapeColour(frame++);
		}
		
		g.drawFPS();
		g.drawSchoolLogo();
	}
	
	public static void main(String[] args) {
		/**
		 * Note that the constructor parameter is used to determine if running
		 * on Android or not. As we are in main there, it means we are on
		 * desktop computer.
		 */
		new DemoLines(false);
	}

}
