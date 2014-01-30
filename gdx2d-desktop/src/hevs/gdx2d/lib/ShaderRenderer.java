package hevs.gdx2d.lib;

import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Renders things using a GLSL shader program included
 * in a file. 
 * 
 * TODO The interface is rather basic as of now
 * but should be improved in a future version.
 * 
 * @author Pierre-André Mudry
 * @version 0.3
 */
public class ShaderRenderer implements Disposable{
	private ShaderProgram shader;
	private Texture tex;
	private SpriteBatch batch;
	private int w, h;
	
	private FileHandle vertexShader;
	
	ShaderRenderer(){
		this(Gdx.files.internal("data/shader/colorRect.fp"), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());		
	}
	
	ShaderRenderer(String shaderFileName){
		this(shaderFileName, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	ShaderRenderer(String shaderFileName, int width, int height){
		this(Gdx.files.internal(shaderFileName), width, height);		
	}
		
	ShaderRenderer(FileHandle handle, int width, int height){	
		w = width;
		h = height;
		vertexShader = Gdx.files.internal("data/shader/default.vs");
		create(handle.readString(), vertexShader.readString());
		
		String[] att = shader.getAttributes();
		String[] unif = shader.getUniforms();
		
		System.out.println("Shader uniforms are :");
		for (String string : unif) {			
			System.out.println("\t" + string);
		}
		
		System.out.println("Shader attributes are :");
		for (String string : att) {			
			System.out.println("\t" + string);
		}
	}
	
	private void create(String fragmentShader, String vertexShader) {
		//the texture does not matter since we will ignore it anyways
		//tex = new Texture(w, h, Format.RGBA8888);
		tex = new Texture(Gdx.files.internal("data/images/back1_512.png"));
		//important since we aren't using some uniforms and attributes that SpriteBatch expects
		ShaderProgram.pedantic = false;
				
		shader = new ShaderProgram(vertexShader, fragmentShader);

		if (!shader.isCompiled()) {
			Logger.log("ShaderRenderer - " + shader.getLog());
			// FIXME This should call the proper gdx2 method to exit
			System.exit(0);
		}
		
		if (shader.getLog().length()!=0)
			Logger.log("ShaderRenderer - " + shader.getLog());

		// Creates a batch with the size of the texture
		batch = new SpriteBatch(1, shader);
		batch.setShader(shader);

		shader.begin();
			// Pass the size of the shader, once
			shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());		
		shader.end();
	}

	/**
	 * Renders the shaders
	 * @param posX Center, x-position of the texture shader to draw
	 * @param posY Center, y-position of the texture shader to draw
	 * @param time The time information to pass to the shader
	 */
	public void render(int posX, int posY, float time){
		// FIXME Should handle resolution changes (notably for Android)
		batch.begin();
			// Pass time to the shader
			shader.setUniformf("time", time);
			// Note that LibGDX coordinate system origin is lower-left
			//batch.draw(tex2, 0, 0);						
			batch.draw(tex, posX-w/2, posY-h/2);
		batch.end();
	}
	
	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * @param uniform The uniform variable to change
	 * @param value The value of the variable, float
	 */
	public void setUniform(String uniform, int value){
		batch.begin();
			shader.setUniformi(uniform, value);
		batch.end();
	}
	
	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * @param uniform The uniform variable to change
	 * @param value The value of the variable, float
	 */
	public void setUniform(String uniform, float value){
		batch.begin();
			shader.setUniformf(uniform, value);
		batch.end();
	}
	
	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * @param uniform The uniform variable to change
	 * @param value The value of the variable, vec2
	 */
	public void setUniform(String uniform, float[] values){
		batch.begin();
			shader.setUniform1fv(uniform, values, 0, values.length);
		batch.end();
	}
	
	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * @param uniform The uniform variable to change
	 * @param value The value of the variable, vec2
	 */
	public void setUniform(String uniform, Vector2 values){
		batch.begin();
			shader.setUniformf(uniform, values);
		batch.end();
	}
	
	/**
	 * Sets an uniform pair (key, value) that is passed to the shader
	 * @param uniform The uniform variable to change
	 * @param value The value of the variable, vec3
	 */
	public void setUniform(String uniform, Vector3 values){
		batch.begin();
			shader.setUniformf(uniform, values);
		batch.end();
	}

	public void setTexture(FileHandle h){
		tex = new Texture(h);
	}
	
	public void setTexture(String texturePath){
		setTexture(Gdx.files.internal(texturePath));
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
