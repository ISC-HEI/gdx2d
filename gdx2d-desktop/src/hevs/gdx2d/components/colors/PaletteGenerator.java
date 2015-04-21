package hevs.gdx2d.components.colors;

import hevs.gdx2d.demos.complex_shapes.DemoComplexShapes;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

/**
 * A color palette generator, original version adapted
 * from <a href="http://devmag.org.za/2012/07/29/how-to-choose-colours-procedurally-algorithms/"> this web-site</a>
 * <p/>
 * <p>The purpose of this class is to mix colors nicely to make a nice looking palette. For an example
 * of use, see {@link DemoComplexShapes}</p>
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PaletteGenerator {

	private static Random random = new Random(123);

	public static Color RandomMix(Color color1, Color color2, Color color3, float greyControl) {
		int randomIndex = random.nextInt(255) % 3;

		float mixRatio1 = (randomIndex == 0) ? random.nextFloat() * greyControl
				: random.nextFloat();

		float mixRatio2 = (randomIndex == 1) ? random.nextFloat() * greyControl
				: random.nextFloat();

		float mixRatio3 = (randomIndex == 2) ? random.nextFloat() * greyControl
				: random.nextFloat();

		float sum = mixRatio1 + mixRatio2 + mixRatio3;

		mixRatio1 /= sum;
		mixRatio2 /= sum;
		mixRatio3 /= sum;

		return new Color((mixRatio1 * color1.r + mixRatio2 * color2.r + mixRatio3 * color3.r), (mixRatio1 * color1.g +
				mixRatio2 * color2.g + mixRatio3 * color3.g),
				(mixRatio1 * color1.b + mixRatio2 * color2.b + mixRatio3 * color3.b), 1.0f);

	}
}
