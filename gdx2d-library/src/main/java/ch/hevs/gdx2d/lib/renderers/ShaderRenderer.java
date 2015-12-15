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

/**
 * Renders things using a GLSL shader program included in a file.
 *
 * @author Pierre-André Mudry
 * @version 0.5
 */
public class ShaderRenderer implements Disposable {

	// Shader include that is going to be added at the beginning of *every* fragment shader
	protected final String fragmentShaderInclude = Gdx.files.internal("fragment_include.glsl").readString();
	protected ShaderProgram shader;
	protected Texture tex[] = new Texture[10];
	protected int textureCount = 0;
	protected SpriteBatch batch;
	protected int w, h;

	protected FileHandle vertexShader;

	public ShaderRenderer(String shaderFileName, int width, int height) {
		this(Gdx.files.internal(shaderFileName), width, height);
	}

	ShaderRenderer(FileHandle handle, int width, int height) {
		w = width;
		h = height;
		vertexShader = Gdx.files.internal("default.vs");

		Logger.dbg("Fragment shader " + handle);
		Logger.dbg("Vertex shader " + vertexShader);

		create(fragmentShaderInclude + handle.readString(), vertexShader.readString());

		String[] att = shader.getAttributes();
		String[] unif = shader.getUniforms();

		if(Version.isSnapshot) {
			Logger.dbg("Shader defined uniforms are :");
			for (String uniform : unif) {
				Logger.log("\t", uniform + ", " + shader.getAttributeType(uniform));
			}

			Logger.dbg("Shader defined attributes are :");
			for (String a : att) {
				Logger.log("\t", a);
			}
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
	 * Renders the shaders
	 *
	 * @param posX Center, x-position of the texture shader to draw
	 * @param posY Center, y-position of the texture shader to draw
	 * @param time The time information to pass to the shader
	 */
	public void render(int posX, int posY, float time) {
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
			// Pass time to the shader
			shader.setUniformf("time", time);
			// Note that LibGDX coordinate system origin is lower-left
			batch.draw(tex[0], posX - w / 2, posY - h / 2);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param value   The value of the variable, int
	 */
	public void setUniform(String uniform, int value) {
		batch.begin();
		shader.setUniformi(uniform, value);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param value   The value of the variable, boolean
	 */
	public void setUniform(String uniform, boolean value) {
		batch.begin();
		shader.setUniformi(uniform, value ? 1 : 0);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param value   The value of the variable, float
	 */
	public void setUniform(String uniform, float value) {
		batch.begin();
		shader.setUniformf(uniform, value);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param values   The value of the variable, vec2
	 */
	public void setUniform(String uniform, float[] values) {
		batch.begin();
		shader.setUniform1fv(uniform, values, 0, values.length);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param values   The value of the variable, vec2
	 */
	public void setUniform(String uniform, Vector2 values) {
		batch.begin();
		shader.setUniformf(uniform, values);
		batch.end();
	}

	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 *
	 * @param uniform The uniform variable to change
	 * @param values   The value of the variable, vec3
	 */
	public void setUniform(String uniform, Vector3 values) {
		batch.begin();
		shader.setUniformf(uniform, values);
		batch.end();
	}

	public int addTexture(Texture t, String n) {
		try {
			if (textureCount >= GL20.GL_MAX_TEXTURE_UNITS) {
				throw new Exception("Out of texture space!");
			}

			textureCount++;
			tex[textureCount] = t;

			// Transmit the texture as an uniform to the shader
			shader.begin();
			shader.setUniformi(n, textureCount);
			shader.end();

			Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0 + textureCount);
			//bind mask to glActiveTexture(GL_TEXTUREXXX)
			tex[textureCount].bind(textureCount);

			//now we need to reset glActiveTexture to zero!!!! 
			// since sprite batch does not do this for us
			Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return textureCount;
	}

	/**
	 * See {@link #addTexture(String, String)}
	 *
	 * @param width             The width of the texture to create
	 * @param height            The height of the texture to create
	 * @param textureIdentifier The uniform name passed to the shader
	 */
	public int addEmptyTexture(int width, int height, String textureIdentifier) {
		Texture t = new Texture(width, height, Format.RGBA8888);
		return addTexture(t, textureIdentifier);
	}

	/**
	 * Adds a new texture from an image to the OpenGL pipeline. It is bound with
	 * a name and a number.
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

	public void setTexture(Texture colorBufferTexture, int id) {
		tex[id] = colorBufferTexture;
	}
}