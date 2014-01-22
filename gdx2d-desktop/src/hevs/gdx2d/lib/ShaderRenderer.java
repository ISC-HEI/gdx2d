package hevs.gdx2d.lib;

import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

/**
 * Renders things using a GLSL shader program included
 * in a file. 
 * 
 * TODO The interface is rather basic as of now
 * but should be improved in a future version.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
public class ShaderRenderer implements Disposable{
	private ShaderProgram shader;
	private Texture tex;
	private SpriteBatch batch;
	
	// Default vertex shader
	// TODO Should be in a file, however comes the question on how
	// to interpolate the string.
	final String VERT =  
			"attribute vec4 "+ShaderProgram.POSITION_ATTRIBUTE+";\n" +	
		    "attribute vec2 surfacePosAttrib;\n" + 
			"varying vec2 surfacePosition;\n" +
			"uniform mat4 u_projTrans;\n" + 
			" \n" + 
			"void main() {\n" +  
			"   surfacePosition = surfacePosAttrib;\n"+
			"	gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
			"}";
	
	
	ShaderRenderer(String shaderFileName){
		this(Gdx.files.internal(shaderFileName));
		//fragmentShader = Gdx.files.internal("data/shader/uniform.fs");
		//fragmentShader = Gdx.files.internal("data/shader/pulse.fs");
		//fragmentShader = Gdx.files.internal("data/shader/colorRect.fs");
		//fragmentShader = Gdx.files.internal("data/shader/watermix.fs");
		//fragmentShader = Gdx.files.internal("data/shader/plasma.fs");
		//vertexShader = Gdx.files.internal("data/shader/default.vs");
	}
	
	ShaderRenderer(FileHandle handle){		
		create(handle.readString());
	}
	
	ShaderRenderer(){
		this(Gdx.files.internal("data/shader/colorRect.fs"));
	}
	
	private void create(String fragmentShader) {
		//the texture does not matter since we will ignore it anyways
		tex = new Texture(256, 256, Format.RGBA8888);
		
		//important since we aren't using some uniforms and attributes that SpriteBatch expects
		ShaderProgram.pedantic = false;
		
		shader = new ShaderProgram(VERT, fragmentShader);

		if (!shader.isCompiled()) {
			Logger.log(shader.getLog());
			// FIXME This should call the proper gdx2 method to exit
			System.exit(0);
		}
		
		if (shader.getLog().length()!=0)
			Logger.log(shader.getLog());

		batch = new SpriteBatch(1000, shader);
		batch.setShader(shader);

		shader.begin();

		// Pass resolution to the shader, once
		shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		shader.end();
	}

	// FIXME Should handle resolution changes (notably for Android)			
	public void render(int posX, int posY, float time){
		batch.begin();
			// Pass time to the shader
			shader.setUniformf("time", time);
			shader.setUniformf("surfacePosition", posX, posY);
		
			// Note that LibGDX coordinate system origin is lower-left
			batch.draw(tex, posX, posY);
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
