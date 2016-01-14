package ch.hevs.gdx2d.lib.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * An anti-aliased circle shader, based on the circle equation.
 *
 * @author Pierre-André Mudry
 */
public class CircleShaderRenderer extends ShaderRenderer {

	// TODO: Pass the coordinates as an array, for drawing multiple circles at the same time ?
	// TODO: Use instancing to make this faster?

	public CircleShaderRenderer() {
		super(Gdx.files.internal("res/lib/circle_aa.fp"), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Render the shader.
	 */
	public void render() {
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
			batch.draw(tex[0], 0, 0);
		batch.end();
	}

	/**
	 * Set the circle radius.
	 * <p/>
	 * Set an uniform pair (key, value) that is passed to the shader.
	 *
	 * @param value the radius of the shader
	 */
	public void setRadius(float value) {
		batch.begin();
			shader.setUniformf("radius", value);
		batch.end();
	}

	/**
	 * Set the circle color.
	 * <p/>
	 * Set an uniform pair (key, value) that is passed to the shader.
	 *
	 * @param col the circle color (RGB value)
	 */
	public void setColor(Vector3 col) {
		batch.begin();
			shader.setUniformf("color", col);
		batch.end();
	}

	/**
	 * Set the circle position.
	 * <p/>
	 * Set an uniform pair (key, value) that is passed to the shader.
	 */
	public void setPosition(Vector2 center) {
		batch.begin();
			shader.setUniformf("position", center);
		batch.end();
	}
}
