package hevs.gdx2d.components.colors;

import com.badlogic.gdx.graphics.Color;

/**
 * Color conversion utilities. This was
 * originally taken on 
 * <a href=http://stackoverflow.com/questions/7896280/converting-from-hsv-hsb-in-java-to-rgb-without-using-java-awt-color-disallowe"> stack overflow</a>
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0 
 */
public class ColorUtils {	
	public static Color hsvToColor(float hue, float saturation, float value) {
		return intToColor(hsvToRgb(hue, saturation, value));
	}
	
	/**
	 * Converts hsv to rgb int
	 * @param hue
	 * @param saturation
	 * @param value
	 * @return
	 */
	public static int hsvToRgb(float hue, float saturation, float value) {

	    int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return rgbToInt(value, t, p);
	      case 1: return rgbToInt(q, value, p);
	      case 2: return rgbToInt(p, value, t);
	      case 3: return rgbToInt(p, q, value);
	      case 4: return rgbToInt(t, p, value);
	      case 5: return rgbToInt(value, p, q);
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
	    }
	}

	/**
	 * Creates an int representation of rgb color
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int rgbToInt(float r, float g, float b){
		return Color.rgb888(r, g, b);
	}	
	
	/**
	 * Creates a Color from an int
	 * @param col
	 * @return
	 */
	public static Color intToColor(int col){
		float r = ((col & 0x00ff0000) >>> 16) / 255f;
		float g = ((col & 0x0000ff00) >>> 8) / 255f;
		float b = ((col & 0x000000ff)) / 255f;		
		return new Color(r,g,b,1);		
	}
}
