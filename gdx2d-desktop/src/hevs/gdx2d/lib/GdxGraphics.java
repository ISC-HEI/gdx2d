package hevs.gdx2d.lib;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.graphics.Polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * A game graphics implementation for LibGDX based on the
 * interface of FunGraphics for INF1 class
 * 
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton (chn)
 * @author Christopher Metrailler (mei)
 * @version 1.52
 */
public class GdxGraphics implements Disposable {
	/**
	 * For camera operations
	 */
	protected OrthographicCamera camera, fixedcamera;	
	
	public ShapeRenderer shapeRenderer;
	public SpriteBatch spriteBatch;
	public ShaderRenderer shaderRenderer;
	private CircleShaderRenderer circleRenderer;
	
	protected Color currentColor = Color.WHITE;
	protected Color backgroundColor = Color.BLACK;

	// The standard font
	protected BitmapFont font;
	
	// For optimizing the current rendering mode and minimizing the number of
	// calls to begin() and end() in spriteBatch
	private enum t_rendering_mode {SHAPE_FILLED, SHAPE_LINE, SHAPE_POINT, SPRITE}; 
	private t_rendering_mode rendering_mode = t_rendering_mode.SPRITE; 

	// For sprite-based logo
	final protected Texture logoTex = new Texture(Gdx.files.internal("lib/logo_hes.png"));	
	final protected Texture circleTex = new Texture(Gdx.files.internal("lib/circle.png"));
	
	/**
	 * When rendering with other methods than the one present here (for instance
	 * when using box2dlight), call this method to render shapes correctly
	 */
	public void resetRenderingMode(){
		checkmode(t_rendering_mode.SPRITE);
		checkmode(t_rendering_mode.SHAPE_LINE);
	}
	
	public GdxGraphics(ShapeRenderer shapeRenderer, 
					   SpriteBatch spriteBatch, OrthographicCamera camera) {
		this.shapeRenderer = shapeRenderer;	
		this.spriteBatch = spriteBatch;
		this.camera = camera;

		circleRenderer = new CircleShaderRenderer();
		
		/**
		 * Generates the fonts images from the TTF file
		 */
		FileHandle robotoF = Gdx.files.internal("font/RobotoSlab-Regular.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(robotoF);
		font = generator.generateFont(15);
		generator.dispose();
		
		// A camera that never moves
		this.fixedcamera = new OrthographicCamera();
		fixedcamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Enable alpha blending for shape renderer
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);			
	}
		
	@Override
	public void dispose() {
		logoTex.dispose();
		circleTex.dispose();		
		font.dispose();
		spriteBatch.dispose();
		
		if(shaderRenderer != null)
			shaderRenderer.dispose();
	}
	
	/**
	 * Draws frame per second (FPS) information
	 */
	public void drawFPS(){
		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		Color oldColor = font.getColor();
		font.setColor(Color.WHITE);		
		drawString(5, 15, "FPS: " + Gdx.graphics.getFramesPerSecond());
		font.setColor(oldColor);
		spriteBatch.setProjectionMatrix(camera.combined);
	}
	
	/**
	 * Draws the University of Applied Sciences Western Switzerland logo<br> As this project is mainly aimed the students
	 * from the <a href='http://inf1.begincoding.net'> inf1 course given at the HES-SO Valais</a>, systems engineering,
	 * it is nice to have a logo for them !
	 */
	public void drawSchoolLogo(){
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		spriteBatch.draw(logoTex, getScreenWidth() - logoTex.getWidth(), 0);
		spriteBatch.setProjectionMatrix(camera.combined);
	}
	
	/**
	 * Draws the school logo in the upper right corner of the screen.
	 * See {@link #drawSchoolLogo()}
	 */
	public void drawSchoolLogoUpperRight(){
		checkmode(t_rendering_mode.SPRITE);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		spriteBatch.draw(logoTex, width - logoTex.getWidth(), height - logoTex.getHeight());
		spriteBatch.setProjectionMatrix(camera.combined);
	}
	
	/**
	 * Switches to the correct rendering mode if needed, calling the
	 * required begin and end.
	 * @param mode
	 */
	private void checkmode(t_rendering_mode mode) {	
		if(rendering_mode != t_rendering_mode.SPRITE){
			shapeRenderer.end();
		}
		else
		{
			spriteBatch.end();
		}
		
		switch (mode) {
		case SHAPE_LINE:
			shapeRenderer.begin(ShapeType.Line);
			rendering_mode = t_rendering_mode.SHAPE_LINE;
			break;

		case SHAPE_FILLED:
			shapeRenderer.begin(ShapeType.Filled);
			rendering_mode = t_rendering_mode.SHAPE_FILLED;
			break;
			
		case SHAPE_POINT:
			shapeRenderer.begin(ShapeType.Point);
			rendering_mode = t_rendering_mode.SHAPE_POINT;
			break;
			
		case SPRITE:	
			spriteBatch.begin();
			rendering_mode = t_rendering_mode.SPRITE;
			break;
		}	
		
	}

	/**
	 * A very simple way to change line width for shape operators (line, ...)
	 * @param width
	 */
	public void setPenWidth(float width){
		/**
		 * TODO : Line capping is not working nicely for non vertical lines in OpenGL.
		 * This is a known problem but at the moment we have decided not to implement
		 * any solution
		 */
		// Mode should be put on sprite, this is not a mistake
		checkmode(t_rendering_mode.SPRITE);
		Gdx.gl20.glLineWidth(width);
	}	
		
	/**
	 * Gets the height of the screen
	 */
	public int getScreenHeight() {	
		return Gdx.graphics.getHeight();		
	}
	
	/**
	 * Gets the width of the rendering surface
	 */
	public int getScreenWidth() {	
		return Gdx.graphics.getWidth();
	}
	
	/**
	 * @param x 
	 * @param y
	 * @param w
	 * @param h
	 * @param angle in degrees
	 */
	public void drawRectangle(float x, float y, float w, float h, float angle) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.setColor(currentColor);
		shapeRenderer.translate(x + w / 2, y + w / 2, 0);		
		if(angle != 0)
			shapeRenderer.rotate(0, 0, 1, angle);		
		shapeRenderer.rect(-w / 2, -w / 2, w, h);
	}

	/**
	 * Clears the screen black
	 */
	public void clear() {
		clear(Color.BLACK);
	}

	boolean first = true;
	
	/**
	 * Clears the screen with a given color
	 * @param c
	 */
	public void clear(Color c) {
		Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(!first && rendering_mode == t_rendering_mode.SPRITE)
			spriteBatch.end();
		else
			first = !first;
		
		if(rendering_mode == t_rendering_mode.SPRITE)
			spriteBatch.begin();
	}

	/**
	 * Sets the current color used for graphics drawing
	 * @param c
	 */
	public void setColor(Color c) {
		currentColor = c;
		shapeRenderer.setColor(c);
	}

	/**
	 * Sets the color of a pixel using the current color ({@link #currentColor}
	 * @param x
	 * @param y
	 */
	public void setPixel(float x, float y) {
		checkmode(t_rendering_mode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(currentColor);
		shapeRenderer.point(x, y, 0);
	}

	/**
	 * Sets the color of a pixel using a color
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setPixel(float x, float y, Color c) {
		checkmode(t_rendering_mode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
	}
	
	public void clearPixel(float x, float y) {
		checkmode(t_rendering_mode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(backgroundColor);
		shapeRenderer.point(x, y, 0);
	}

	/**
	 * Draws a line that start at P(p1x, p1y) and ends at P(p2x, p2y)
	 * @param p1x Start coordinate X
	 * @param p1y Start coordinate Y
	 * @param p2x End coordinate X
	 * @param p2y End coordinate Y
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.setColor(currentColor);
		shapeRenderer.line(p1x, p1y, p2x, p2y);		
	}

	/**
	 * Draws a line that start at P(p1x, p1y) and ends at P(p2x, p2y) with color c
	 * @param p1x Start coordinate X
	 * @param p1y Start coordinate Y
	 * @param p2x End coordinate X
	 * @param p2y End coordinate Y
	 * @param c The color for drawing the line
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y, Color c) {
		shapeRenderer.setColor(c);
		drawLine(p1x, p1y, p2x, p2y);		
	}

	/**
	 * 
	 * @see #drawFilledRectangle(float, float, float, float, float, Color)
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param angle
	 */
	public void drawFilledRectangle(float x, float y, float w, float h, float angle) {
		checkmode(t_rendering_mode.SHAPE_FILLED);
		shapeRenderer.identity();			
		shapeRenderer.translate(x + w / 2, y + w / 2, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -w / 2, w, h);		
	}
	
	/**
	 * Draws a filled rectangle
	 * @param x Center x coordinate
	 * @param y Center y
	 * @param w Width of the rectangle
	 * @param h Height of the rectangle
	 * @param angle Rotation angle of the rectangle
	 * @param c The color to fill the rectangle with
	 */
	public void drawFilledRectangle(float x, float y, float w, float h, float angle, Color c) {
		shapeRenderer.setColor(c);		
		drawFilledRectangle(x, y, w, h, angle);
	}

	public void drawCircle(float centerX, float centerY, float radius, Color c) {
		shapeRenderer.setColor(c);
		drawCircle(centerX, centerY, radius);
	}
	
	public void drawCircle(float centerX, float centerY, float radius) {
		checkmode(t_rendering_mode.SHAPE_LINE);		
		shapeRenderer.circle(centerX, centerY, radius);
	}

	public void drawAntiAliasedCircle(float centerX, float centerY, float radius, Color c){
		circleRenderer.setColor(new Vector3(c.r, c.g, c.b));
		circleRenderer.setPosition(new Vector2(centerX, centerY));
		circleRenderer.setRadius(radius);
		circleRenderer.render();
	}
	
	public void drawFilledCircle(float centerX, float centerY, float radius, Color c) {		
		// TODO Do this with a shader instead of formulas or textures !!
		// Draw big circles with mathematical formulas
		if(radius > 100)
		{
			checkmode(t_rendering_mode.SHAPE_FILLED);
			shapeRenderer.setColor(c);
			shapeRenderer.identity();
			shapeRenderer.circle(centerX, centerY, radius);
		}
		else
		{		
			// Draw smaller circles with an image, this goes faster
			Color old = spriteBatch.getColor();
			checkmode(t_rendering_mode.SPRITE);
			spriteBatch.setColor(c);		
			spriteBatch.draw(circleTex, centerX-64, centerY-64, 64, 64, 128, 128, radius/64, radius/64, 0, 0, 0, 128, 128, false, false);
			spriteBatch.setColor(old);
		}
	}
	
	public void drawFilledBorderedCircle(float centerX, float centerY, float radius, Color inner, Color outer) {		
			checkmode(t_rendering_mode.SPRITE);			
			// This is not really beautiful but it works more or less
			// TODO Improve this
			drawFilledCircle(centerX, centerY, radius+3, outer);
			drawFilledCircle(centerX, centerY, radius+1, inner);			
	}

	public void drawString(float posX, float posY, String str) {
		checkmode(t_rendering_mode.SPRITE);
		font.drawMultiLine(spriteBatch, str, posX, posY);
		spriteBatch.flush(); // Fix issue #25 (mei)
	}
	
	/**
	 * Draws a string with a specific font
	 * @param posX
	 * @param posY
	 * @param str
	 * @param f
	 */
	public void drawString(float posX, float posY, String str, BitmapFont f) {
		checkmode(t_rendering_mode.SPRITE);
		f.drawMultiLine(spriteBatch, str, posX, posY);
	}
	
	/**
	 * Draws a string in the middle of the screen with a specific font
	 * @param posY
	 * @param str
	 * @param f
	 */
	public void drawStringCentered(float posY, String str, BitmapFont f) {
		float w = f.getBounds(str).width;
		drawString((getScreenWidth() - w )/ 2.0f, posY, str, f);
	}

	/**
	 * Draws a string in the middle of the screen with a specific font
	 * @param posY
	 * @param str
	 * @param f
	 */
	public void drawStringCentered(float posY, String str) {
		float w = font.getBounds(str).width;
		drawString((getScreenWidth() - w )/ 2.0f, posY, str);
	}
	
	/**
	 * Draws an image in the background that will not move with the camera.
	 * 
	 * @param t 
	 * @param i x coordinate in the screen space
	 * @param j y coordinate in the screen space
	 */
	public void drawBackground(BitmapImage t, float i, float j) {
		drawBackground(t.getImage(), i, j);
	}
	
	/**
	 * Draws a texture in background that will not move with the camera.
	 * 
	 * @param t
	 * @param i x coordinate in the screen space
	 * @param j y coordinate in the screen space
	 */
	public void drawBackground(Texture t, float i, float j) {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.setProjectionMatrix(fixedcamera.combined);		
		spriteBatch.disableBlending();
		spriteBatch.draw(t, i, j);
		spriteBatch.enableBlending();
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draws a picture at position ({@code posX, posY}).  
	 */
	public void drawPicture(float posX, float posY, BitmapImage bitmap) {						
		drawTransformedPicture(posX, posY, 0, 1, bitmap);		
	}
	
	/**
	 * Draws a picture at position ({@code posX, posY}). Center of rotation is located in the
	 * middle of the image. 
	 */
	public void drawTransformedPicture(float posX, float posY, float angle, float scale, BitmapImage bitmap) {
		drawTransformedPicture(posX, posY, bitmap.getImage().getWidth() / 2, bitmap.getImage().getHeight() / 2, angle, scale, bitmap);
	}
	
	/**
	 * Draws a picture at position ({@code posX, posY}) with a fixed width and height (not the one of the {@link BitmapImage} itself).
	 */
	public void drawTransformedPicture(float posX, float posY, float angle, float width, float height, BitmapImage bitmap) {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), (float)posX-width, (float)posY-height, width, height, width*2, height*2, 1.0f, 1.0f,(float) angle);
	}
	
	/**
	 * Draws a picture at position ({@code posX, posY}) with a selectable center of rotation which is located
	 * at position offset by ({@code centerX, centerY}).
	 * 
	 * @param posX
	 * @param posY
	 * @param centerX
	 * @param centerY
	 * @param angle
	 * @param scale
	 * @param bitmap
	 */
	public void drawTransformedPicture(float posX, float posY, float centerX, float centerY, float angle, float scale, BitmapImage bitmap) {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), posX-bitmap.getRegion().getRegionWidth()/2, posY-bitmap.getRegion().getRegionHeight()/2, centerX, centerY, (float) bitmap.getImage().getWidth(), (float) bitmap.getImage().getHeight(), (float) scale, (float) scale,(float) angle);
	}

	/**
	 * Draws a picture at position {@code pos} with a selectable alpha (transparency). See {@link #drawAlphaPicture(float, float, float, float, float, float, float, BitmapImage)}
	 * @param pos
	 * @param alpha
	 * @param img
	 */
	public void drawAlphaPicture(Vector2 pos, float alpha, BitmapImage img) {
		drawAlphaPicture(pos.x, pos.y, alpha, img);
	}

	public void drawAlphaPicture(float posX, float posY, float scale, float alpha, BitmapImage img) {
		drawAlphaPicture(posX, posY, img.getImage().getWidth() / 2, img.getImage().getHeight() / 2, 0, 1.0f, alpha, img);
	}
	
	public void drawAlphaPicture(float posX, float posY, float alpha, BitmapImage img) {
		drawAlphaPicture(posX, posY, img.getImage().getWidth() / 2, img.getImage().getHeight() / 2, 0, 1.0f, alpha, img);
	}
	
	public void drawAlphaPicture(float posX, float posY, float centerX, float centerY, float angle, float scale, float alpha, BitmapImage img) {		
		Color old = spriteBatch.getColor();
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
		drawTransformedPicture(posX, posY, angle, scale, img);
		spriteBatch.setColor(old);
	}
	
	public void drawPolygon(Polygon p) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.setColor(currentColor);
		shapeRenderer.identity();		
		shapeRenderer.polygon(p.getVertices());
	}

	public void drawFilledPolygon(Polygon polygon, Color c) {		
		float[] vertices =  polygon.getEarClippedVertices();
		checkmode(t_rendering_mode.SHAPE_FILLED);
		shapeRenderer.setColor(c);
		shapeRenderer.identity();

		for (int i = 0; i < vertices.length; i+=6) {
			shapeRenderer.triangle(vertices[i], vertices[i+1], vertices[i+2], vertices[i+3], vertices[i+4], vertices[i+5]);
		}		
	}
	
	/****************************************************
	 * Camera stuff
	 ****************************************************/
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	/**
	 * Move the camera to a fixed position
	 * @param x
	 * @param y
	 */
	public void moveCamera(float x, float y) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(x, y);		
	}
	
	/**
	 * Puts the camera at its original location
	 */
	public void resetCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());				
	}
	
	/**
	 * Displace the camera by a value
	 * @param dx
	 * @param dy
	 */
	public void scroll(float dx, float dy) {
		camera.translate(dx, dy);
		camera.update();
	}
	
	/**
	 * Zooms in and out the camera, 1 is the default zoom-level
	 * @param factor
	 */
	public void zoom(float factor) {
		camera.zoom = factor;
		camera.update();
	}

	
	/****************************************************
	 * Shaders stuff
	 ****************************************************/	
	public void drawShader() {
		drawShader(getScreenWidth()/2, getScreenHeight()/2, 0f);
	}
	
	public void drawShader(float shaderTime) {
		drawShader(getScreenWidth()/2, getScreenHeight()/2, shaderTime);
	}
	
	/**
	 * Request shader rendering that passes an uniform value with
	 * the current rendering location.
	 * @param posX The value passed to the shader, might be discarded
	 * @param posY The value passed to the shader, might be discarded
	 * @param shaderTime The value passed to the shader, might be discarded
	 */
	public void drawShader(int posX, int posY, float shaderTime) {
		if(shaderRenderer != null)
			shaderRenderer.render(posX, posY, shaderTime);
		else{
			try{
				new Exception("Shader renderer not set, aborting.");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets a shader that will be drawable
	 * @param s The path of the shader file
	 */
	public void setShader(String s) {
		setShader(s, getScreenWidth(), getScreenHeight());
	}
	
	/**
	 * Sets a shader that will be drawn
	 * @param s The path of the shader
	 * @param height The height of the texture to draw the shader on
	 * @param width The width of the texture to draw the shader on
	 */
	public void setShader(String s, int width, int height) {
		// TODO Allowing multiple shaders at once would be nice
		// Dispose of the allocated resources
		if(shaderRenderer != null){
			shaderRenderer.dispose();
		}
				
		shaderRenderer = new ShaderRenderer(s, width, height);		
	}
		
}