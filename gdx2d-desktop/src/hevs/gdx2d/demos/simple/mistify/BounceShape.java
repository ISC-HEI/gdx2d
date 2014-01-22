package hevs.gdx2d.demos.simple.mistify;

import hevs.gdx2d.lib.GdxGraphics;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

/**
 * A classical Mistify screen saver clone. 
 * 
 * Code adapted from http://r3dux.org/2010/11/mystify-2-0/
 * @author Pierre-André Mudry
 * @version 1.0
 */
public class BounceShape {
	double[] x = new double[4];
	double[] y = new double[4];

	double[] xSpeed = new double[4];
	double[] ySpeed = new double[4];

	Color c;
	Color targetC;

	double colourChangeSpeed = 0.001;
	double xSpeedMultiplier = 6, ySpeedMultiplier = 6;
	double xSpeedAdder = 1, ySpeedAdder = 1;

	void drawShape(GdxGraphics g) {
		
		g.setColor(c);
		
		for (int pointLoop = 0; pointLoop < 4; pointLoop++) {
			g.drawLine((float) x[pointLoop], (float) y[pointLoop],
					(float) x[(pointLoop + 1) % 4],
					(float) y[(pointLoop + 1) % 4]);
		}
	}

	void moveShape(int theScreenWidth, int theScreenHeight) {
		for (int loop = 0; loop < 4; loop++) {
			// Move the particles
			x[loop] += xSpeed[loop];
			y[loop] += ySpeed[loop];

			// Bounce the particles when they hit the edge of the window
			if (x[loop] > theScreenWidth) {
				x[loop] = theScreenWidth;
				xSpeed[loop] = -xSpeedAdder
						+ r.nextFloat()
						* (xSpeedMultiplier * -1);
			}
			if (x[loop] < 0.0f) {
				x[loop] = 0.0f;
				xSpeed[loop] = xSpeedAdder
						+ r.nextFloat()
						* xSpeedMultiplier;
			}

			if (y[loop] > theScreenHeight) {
				y[loop] = theScreenHeight;
				ySpeed[loop] = -ySpeedAdder
						+ r.nextFloat()
						* (ySpeedMultiplier * -1);
			}
			if (y[loop] < 0.0f) {
				y[loop] = 0.0f;
				ySpeed[loop] = ySpeedAdder
						+ r.nextFloat()
						* ySpeedMultiplier;
			}

		} // End of for loop
	}

	Color pickColour() {
		return new Color(r.nextFloat(),r.nextFloat(),r.nextFloat(), 1.0f);
	}

	void shiftShapeColour(int frameCount) {
	    // Change the target colour every 500 frames
	    if (frameCount % 500 == 0)
	        targetC = pickColour();
	 
	    // Shift red component close to red target
	    if (c.r < targetC.r)
	        c.r += colourChangeSpeed;
	    if (c.r > targetC.r)
	        c.r -= colourChangeSpeed;
	 
	    // Shift green component closer to green target
	    if (c.g < targetC.g)
	        c.g += colourChangeSpeed;
	    if (c.g > targetC.g)
	        c.g -= colourChangeSpeed;
	 
	    // Shift blue component closer to blue target
	    if (c.b < targetC.b)
	        c.b += colourChangeSpeed;
	    if (c.b > targetC.b)
	        c.b -= colourChangeSpeed;
	    
		
	}

	Random r = new Random();

	BounceShape(int width, int height) {
		// Initialise the x/y and xSpeeds/ySpeeds
		for (int loop = 0; loop < 4; loop++) {
			x[loop] = r.nextInt(width);
			y[loop] = r.nextInt(height);

			xSpeed[loop] = r.nextDouble() * xSpeedMultiplier;
			ySpeed[loop] = r.nextDouble() * ySpeedMultiplier;
		}

		// Pick a random colour for the current colour and target colour
		c = pickColour();
		targetC = pickColour();
	}

}
