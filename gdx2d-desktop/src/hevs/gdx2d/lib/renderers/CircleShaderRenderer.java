package hevs.gdx2d.lib.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Implements a shader for rendering circles
 * TODO Pass the coordinates as an array, for drawing multiple circles at the same time ?
 * TODO Use instancing to make this faster?
 *
 * @author Pierre-André Mudry
 */
public class CircleShaderRenderer extends ShaderRenderer{
	public CircleShaderRenderer() {
		super(Gdx.files.internal("data/shader/circles/circle_aa.fp"), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Renders the shaders
	 */
	public void render() {
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
			batch.draw(tex[0], 0, 0);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param value The value of the variable, float
	 */
	public void setRadius(float value) {
		batch.begin();
			shader.setUniformf("radius", value);
		batch.end();
	}

	public void setColor(Vector3 col) {
		batch.begin();
			shader.setUniformf("color", col);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 */
	public void setPosition(Vector2 center) {
		batch.begin();
			shader.setUniformf("position", center);
		batch.end();
	}
}
