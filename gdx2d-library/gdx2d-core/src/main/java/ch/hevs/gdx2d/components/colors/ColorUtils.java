package ch.hevs.gdx2d.components.colors;

import com.badlogic.gdx.graphics.Color;

/**
 * {@link Color} conversion utilities methods.
 * <p>
 * The source code was adapted from a question on
 * <a href="http://stackoverflow.com/questions/7896280/converting-from-hsv-hsb-in-java-to-rgb-without-using-java-awt-color-disallowe"> stack overflow</a>.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public final class ColorUtils {

	private ColorUtils() {
		// Never called
	}

	/**
	 * Conversion from the HSV color space to {@link Color}.
	 *
	 * @param hue        hue of the original color (tint)
	 * @param saturation the amount of color saturation
	 * @param value      value of the current color
	 * @return the converted {@link Color}
	 */
	public static Color hsvToColor(float hue, float saturation, float value) {
		return intToColor(hsvToRgb(hue, saturation, value));
	}

	/**
	 * Converts hue-saturation-value color space to red-green-blue integer
	 * value representation.
	 *
	 * @param hue        hue of the original color (tint)
	 * @param saturation the amount of color saturation
	 * @param value      value of the current color
	 * @return the integer representation of the {@link Color}
	 */
	public static int hsvToRgb(float hue, float saturation, float value) {

		int h = (int) (hue * 6);
		float f = hue * 6 - h;
		float p = value * (1 - saturation);
		float q = value * (1 - f * saturation);
		float t = value * (1 - (1 - f) * saturation);

		switch (h) {
			case 0:
				return rgbToInt(value, t, p);
			case 1:
				return rgbToInt(q, value, p);
			case 2:
				return rgbToInt(p, value, t);
			case 3:
				return rgbToInt(p, q, value);
			case 4:
				return rgbToInt(t, p, value);
			case 5:
				return rgbToInt(value, p, q);
			default:
				throw new RuntimeException("Something went wrong when converting from" +
						" HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
		}
	}

	/**
	 * Converts an RGB color to its integer counterpart
	 *
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @return the integer value representing this color
	 */
	public static int rgbToInt(float r, float g, float b) {
		return Color.rgb888(r, g, b);
	}

	/**
	 * Creates a {@link Color} from an integer value
	 *
	 * @param col the integer color value to convert
	 * @return the {@link Color} representation of the given integer value
	 */
	public static Color intToColor(int col) {
		float r = ((col & 0x00ff0000) >>> 16) / 255f;
		float g = ((col & 0x0000ff00) >>> 8) / 255f;
		float b = ((col & 0x000000ff)) / 255f;
		return new Color(r, g, b, 1);
	}
}
