package hevs.gdx2d.demos.shaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * A port of ShaderLesson1 from lwjgl-basics to LibGDX:
 * https://github.com/mattdesl/lwjgl-basics/wiki/ShaderLesson1
 * 
 * @author davedes
 */
public class DemoShader implements ApplicationListener {
	
	//Minor differences: 
		//LibGDX Position attribute is a vec4
		//u_projView is called u_projTrans
		//we need to set ShaderProgram.pedantic to false 
		//LibGDX uses lower-left as origin (0, 0)
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "GDXGraphics shader";
		cfg.useGL20 = true;
		cfg.width = 256;
		cfg.height = 256;
		cfg.resizable = false;

		new LwjglApplication(new DemoShader(), cfg);
	}
	
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
		
	FileHandle fragmentShader, vertexShader;
		
	Texture tex;
	SpriteBatch batch;
	OrthographicCamera cam;
	ShaderProgram shader;
	
	@Override
	public void create() {
		//the texture does not matter since we will ignore it anyways
		tex = new Texture(256, 256, Format.RGBA8888);
		
		//important since we aren't using some uniforms and attributes that SpriteBatch expects
		ShaderProgram.pedantic = false;
		
//		fragmentShader = Gdx.files.internal("data/shader/uniform.fs");
//		fragmentShader = Gdx.files.internal("data/shader/pulse.fs");
		fragmentShader = Gdx.files.internal("data/shader/colorRect.fs");
//		fragmentShader = Gdx.files.internal("data/shader/watermix.fs");
		//fragmentShader = Gdx.files.internal("data/shader/plasma.fs");

		//vertexShader = Gdx.files.internal("data/shader/default.vs");
		
		final String FRAG = fragmentShader.readString();
		  
		
		shader = new ShaderProgram(VERT, FRAG);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}
		
		if (shader.getLog().length()!=0)
			System.out.println(shader.getLog());

		batch = new SpriteBatch(1000, shader);
		batch.setShader(shader);

		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false);
	}

	@Override
	public void resize(int width, int height) {
		shader.begin();
		// Pass resolution to the shader
		shader.setUniformf("resolution", width, height);		
		shader.end();
		cam.setToOrtho(false, width, height);
		batch.setProjectionMatrix(cam.combined);
	}

	float time = 0.0f;
	
	@Override
	public void render() {
		batch.begin();

		// Pass time to the shader
		shader.setUniformf("time", time+=Gdx.graphics.getDeltaTime());
		shader.setUniformf("surfacePosition", 0, 0);
		
		//notice that LibGDX coordinate system origin is lower-left
		batch.draw(tex, 0,0);
		
//		//batch.draw(tex, 10, 320, 32, 32);
//		batch.draw(tex, 10, 320, 32, 32);
		
		batch.end();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		shader.dispose();
		tex.dispose();
	}	
}