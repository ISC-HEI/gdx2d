package hevs.gdx2d.lib;

import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Implements a shader for rendering circles
 * TODO Pass the coordinates as an array, for drawing multiple circles at the same time ?
 * TODO Use instancing to make this faster?
 * @author Pierre-André Mudry
 */
public class CircleShaderRenderer implements Disposable{
	private ShaderProgram shader;
	private Texture tex;
	public SpriteBatch batch;
	private int w, h;

	private FileHandle vertexShader;

	CircleShaderRenderer() {
		this(Gdx.files.internal("data/shader/circles/circle_aa.fp"), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	private CircleShaderRenderer(FileHandle handle, int width, int height) {
		w = width;
		h = height;
		vertexShader = Gdx.files.internal("data/shader/default.vs");
		create(handle.readString(), vertexShader.readString());
	}

	private void create(String fragmentShader, String vertexShader) {
		// the texture does not matter since we will ignore it anyways
		tex = new Texture(w, h, Format.RGBA8888);

		// important since we aren't using some uniforms and attributes that
		// SpriteBatch expects
		ShaderProgram.pedantic = false;

		shader = new ShaderProgram(vertexShader, fragmentShader);

		if (!shader.isCompiled()) {
			Logger.log("ShaderRenderer - " + shader.getLog());
			System.exit(0);
		}

		if (shader.getLog().length() != 0
				&& !shader.getLog().contains("No errors"))
			Logger.log("ShaderRenderer - " + shader.getLog());

		// Creates a batch with the size of the texture
		batch = new SpriteBatch(1, shader);
		batch.setShader(shader);

		shader.begin();
		// Pass the size of the shader, once		
		shader.setUniformf("resolution", w,h);
		shader.end();
	}

	/**
	 * Renders the shaders
	 * 
	 * @param posX
	 *            Center, x-position of the texture shader to draw
	 * @param posY
	 *            Center, y-position of the texture shader to draw
	 * @param time
	 *            The time information to pass to the shader
	 */
	public void render() {
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
		batch.draw(tex, 0, 0);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * 
	 * @param uniform
	 *            The uniform variable to change
	 * @param value
	 *            The value of the variable, float
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
	 * 
	 * @param uniform
	 *            The uniform variable to change
	 * @param value
	 *            The value of the variable, float
	 */
	public void setPosition(Vector2 center) {
		batch.begin();
		shader.setUniformf("position", center);
		batch.end();
	}	
	
	/**
	 * Release the used resources properly
	 */
	@Override
	public void dispose() {
		batch.dispose();
		shader.dispose();
		tex.dispose();
	}
}
