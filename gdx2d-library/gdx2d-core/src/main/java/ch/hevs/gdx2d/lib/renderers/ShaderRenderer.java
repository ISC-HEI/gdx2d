package ch.hevs.gdx2d.lib.renderers;

import ch.hevs.gdx2d.lib.Version;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Render things using a GLSL shader program included in a file.
 *
 * @author Pierre-André Mudry
 * @version 0.5
 */
public class ShaderRenderer implements Disposable {

	// Shader include that is going to be added at the beginning of *every* fragment shader
	protected final String fragmentShaderInclude = Gdx.files.internal("res/lib/fragment_include.glsl").readString();
	protected ShaderProgram shader;
	protected Texture[] tex = new Texture[10];
	protected int textureCount = 0;
	protected SpriteBatch batch;
	protected int w;
	protected int h;

	protected FileHandle vertexShader;

	/**
	 * Create a shader render from an existing shader file.
	 * @param shaderFileName the shader file name to renderer
	 * @param width the shader width
	 * @param height the shader height
	 */
	public ShaderRenderer(String shaderFileName, int width, int height) {
		this(Gdx.files.internal(shaderFileName), width, height);
	}

	ShaderRenderer(FileHandle handle, int width, int height) {
		w = width;
		h = height;
		vertexShader = Gdx.files.internal("res/lib/default.vs");

		create(fragmentShaderInclude + handle.readString(), vertexShader.readString());

		if(Version.isSnapshot) {
			Logger.dbg(String.format("Fragment shader '%s' loaded.", handle));
			Logger.dbg(String.format("Vertex shader '%s' loaded.", vertexShader));
			StringBuilder log = new StringBuilder();
			log.append("Shader defined uniforms are :\n");
			for (String uniform : shader.getUniforms()) {
				log.append(String.format("\t%s = %s%n", uniform, shader.getAttributeType(uniform)));
			}
			Logger.dbg(log.toString());

			log = new StringBuilder();
			log.append("Shader defined attributes are :\n");
			for (String a : shader.getAttributes()) {
				log.append(String.format("\t%s%n", a));
			}
			Logger.dbg(log.toString());
		}

		shader.begin();
			// Pass the size of the shader, once
			shader.setUniformf("resolution", w, h);
		shader.end();
	}

	private void create(String fragmentShader, String vertexShader) {
		//the texture does not matter since we will ignore it anyways
		tex[0] = new Texture(w, h, Format.RGBA8888);

		//important since we aren't using some uniforms and attributes that SpriteBatch expects
		ShaderProgram.pedantic = false;

		shader = new ShaderProgram(vertexShader, fragmentShader);

		if (!shader.isCompiled()) {
			Logger.log("Shader compile msg - " + shader.getLog());
			Gdx.app.exit();
		}

		if (shader.getLog().length() != 0 && !shader.getLog().contains("No errors"))
			Logger.log("Shader message - " + shader.getLog());

		// Creates a batch with the size of the texture
		batch = new SpriteBatch(1, shader);
		batch.setShader(shader);
	}

	/**
	 * Render the shader.
	 *
	 * @param posX X center position of the texture shader to draw
	 * @param posY Y center position of the texture shader to draw
	 * @param time the time information to pass to the shader
	 */
	public void render(int posX, int posY, float time) {
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
			// Pass time to the shader
			shader.setUniformf("time", time);
			// Note that LibGDX coordinate system origin is lower-left
			batch.draw(tex[0], posX - w / 2f, posY - h / 2f);
		batch.end();
	}

	/**
	 * Set an uniform pair (key, value) that is passed to the shader.
	 *
	 * @param uniform the uniform variable to change
	 * @param value the value of the variable, int
	 */
	public void setUniform(String uniform, int value) {
		batch.begin();
		shader.setUniformi(uniform, value);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform the uniform variable to change
	 * @param value the value of the variable, boolean
	 */
	public void setUniform(String uniform, boolean value) {
		batch.begin();
		shader.setUniformi(uniform, value ? 1 : 0);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform the uniform variable to change
	 * @param value the value of the variable, float
	 */
	public void setUniform(String uniform, float value) {
		batch.begin();
		shader.setUniformf(uniform, value);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform the uniform variable to change
	 * @param values the value of the variable, vec2
	 */
	public void setUniform(String uniform, float[] values) {
		batch.begin();
		shader.setUniform1fv(uniform, values, 0, values.length);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform the uniform variable to change
	 * @param values the value of the variable, vec2
	 */
	public void setUniform(String uniform, Vector2 values) {
		batch.begin();
		shader.setUniformf(uniform, values);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform the uniform variable to change
	 * @param values the value of the variable, vec3
	 */
	public void setUniform(String uniform, Vector3 values) {
		batch.begin();
		shader.setUniformf(uniform, values);
		batch.end();
	}

	/**
	 * Add a texture to the shader.
	 *
	 * @param t the texture
	 * @param n the name of the uniform
	 * @return the texture number that was created
	 * @throws GdxRuntimeException if too much texture have been loaded
	 */
	public int addTexture(Texture t, String n) {
		if (textureCount >= GL20.GL_MAX_TEXTURE_UNITS)
				throw new GdxRuntimeException("Out of texture space!");

		textureCount++;
		tex[textureCount] = t;

		// Transmit the texture as an uniform to the shader
		shader.begin();
		shader.setUniformi(n, textureCount);
		shader.end();

		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0 + textureCount);
		//bind mask to glActiveTexture(GL_TEXTUREXXX)
		tex[textureCount].bind(textureCount);

		// now we need to reset glActiveTexture to zero!!!!
		// since sprite batch does not do this for us
		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
		return textureCount;
	}

	/**
	 * Add an empty texture.
	 * See {@link #addTexture(String, String)}
	 *
	 * @param width the width of the texture to create
	 * @param height the height of the texture to create
	 * @param textureIdentifier the uniform name passed to the
	 * @return the texture number that was created
	 * @throws GdxRuntimeException if too much texture have been loaded
	 */
	public int addEmptyTexture(int width, int height, String textureIdentifier) {
		Texture t = new Texture(width, height, Format.RGBA8888);
		return addTexture(t, textureIdentifier);
	}

	/**
	 * Adds a new texture from an image to the OpenGL pipeline. It is bound with a name and a number.
	 *
	 * @param texturePath       The image to load as a texture
	 * @param textureIdentifier The identifier uniform that will be passed to the shader
	 * @return The texture number that was created
	 */
	public int addTexture(String texturePath, String textureIdentifier) {
		Texture t = new Texture(Gdx.files.internal(texturePath));
		return addTexture(t, textureIdentifier);
	}

	/**
	 * Release the used resources properly.
	 */
	@Override
	public void dispose() {
		batch.dispose();
		shader.dispose();

		for (int i = 0; i < textureCount; i++)
			tex[i].dispose();
	}

	/**
	 * Set a texture.
	 *
	 * @param colorBufferTexture the texture
	 * @param id the texture number to replace
	 */
	public void setTexture(Texture colorBufferTexture, int id) {
		tex[id] = colorBufferTexture;
	}
}
