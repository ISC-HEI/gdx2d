package hevs.gdx2d.demos.image_drawing;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Demonstrates the use of the {@link BitmapImage#getColor(int, int)} function on 
 * a real case.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0 
 */
public class DemoGetImageColor extends PortableApplication{	
	BitmapImage imgBitmap;
	
	float alpha1 = 0.06f, alpha2 = 0.3f, alpha3 = 0.6f, alpha4 = 0.94f;
	int dir1 = 1, dir2= 1, dir3 = 1, dir4 = 1;
	
	/**
	 * Constructor
	 * @param onAndroid tells if we are currently running on Android or not
	 */
	public DemoGetImageColor(boolean onAndroid){
		super(onAndroid);	
	}
	
	@Override
	public void onInit() {
		// Sets the window title
		setTitle("Get image color, mui 2014");
		
		Logger.log("Move the mouse on the image to get their color");
		
		// Loads the image that will be displayed in the middle of the screen
		imgBitmap = new BitmapImage("data/lib/color_pattern.png");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {					
		// Clear the screen
		g.clear();					
		
		/**
		 * Position of the image on the screen
		 */
		Vector2 imagePosition = new Vector2(100, 100);
		
		/**
		 * Position we want to read the color of
		 */
		Vector2 sampledPixel = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		
		g.drawPicture((float)imagePosition.x, (float)imagePosition.y, imgBitmap);

		/**
		 * To get the color of a pixel in an image, we
		 * must get translate screen coordinates to image coordinates
		 * This is the role of the following function
		 */
		Vector2 imgPixel = imgBitmap.pixelInScreenSpace(sampledPixel, imagePosition);		
		
		if(imgBitmap.isContained(imgPixel)){
			int c = imgBitmap.getColor((int)imgPixel.x, (int) imgPixel.y);
			
			// Draw a circle corresponding to the read color
			g.drawStringCentered(300, "Color read from the image");
			g.drawFilledCircle(250, 250, 20, ColorUtils.intToColor(c));
		}
		
		g.drawFPS(); 		// Draws the number of frame per second
		g.drawSchoolLogo(); // Draws the school logo
	}

	@Override
	public void onDispose() {
		super.onDispose();
		imgBitmap.dispose();
	}
	
	public static void main(String[] args) {
		new DemoGetImageColor(false);
	}
}
