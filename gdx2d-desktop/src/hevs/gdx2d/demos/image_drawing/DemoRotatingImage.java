package hevs.gdx2d.demos.image_drawing;

import hevs.gdx2d.lib.GdxGraphics;

/**
 * Demo application to demonstrate how to rotate an image
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0 
 */
public class DemoRotatingImage extends DemoSimpleImage{		
	public DemoRotatingImage(boolean onAndroid) {
		super(onAndroid);
	}

	@Override
	public void onInit() {	
		super.onInit();
		this.setTitle("Rotating image, mui 2013");
	}
	
	@Override
	public void onGraphicRender(GdxGraphics g) {		
		/**
		 * Rendering
		 */
		// Clear the screen
		g.clear();					
		
		// Render an image which turns with scale
		g.drawTransformedPicture(getWindowWidth()/2, getWindowHeight()/2, 								
								counter,
								1,
								imgBitmap);	
		
		g.drawFPS(); 		// Draws the number of frame per second
		g.drawSchoolLogo(); // Draws the school logo
		
		/**
		 * Logic update
		 */
		if(counter >= 360)
			counter = 0;
		
		// Make the angle bigger
		counter += 1;
	}
	
	public static void main(String[] args) {
		new DemoRotatingImage(false);
	}
}
