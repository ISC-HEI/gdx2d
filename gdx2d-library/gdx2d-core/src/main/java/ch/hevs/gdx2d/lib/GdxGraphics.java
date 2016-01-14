package ch.hevs.gdx2d.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.graphics.Polygon;
import ch.hevs.gdx2d.lib.renderers.CircleShaderRenderer;
import ch.hevs.gdx2d.lib.renderers.ShaderRenderer;

/**
 * A game graphic class implementation based on the LibGDX library.
 * <p/>
 * The {@link GdxGraphics} class has a similar interface to the {@code FunGraphics} class used for the
 * <a href="http://inf1.begincoding.net">INF1 course</a> given at the University of Applied Sciences School in Sion.
 * <p/>
 * The main objective of this implementation is to provide a simple, yet powerful,
 * set of graphics primitives used for the projects that take place in this course.
 * <p/>
 * Comments are welcome !
 *
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton (chn)
 * @author Christopher Métrailler (mei)
 * @version 1.61
 */
public class GdxGraphics implements Disposable {
	// For sprite-based logo
	final protected Texture logoTex = new Texture(Gdx.files.internal("res/lib/logo_hes.png"));
	final protected Texture circleTex = new Texture(Gdx.files.internal("res/lib/circle.png"));
	protected SpriteBatch spriteBatch;

	protected OrthographicCamera camera, fixedCamera; // For camera operations
	protected BitmapFont font; // The standard font

	protected Color currentColor = Color.WHITE;
	protected Color backgroundColor = Color.BLACK;
	private ShapeRenderer shapeRenderer;

	/** Used for rendering anti-aliased circles */
	private CircleShaderRenderer circleRenderer;
	/** Used for rendering custom shader */
	private ShaderRenderer shaderRenderer;

	private RenderingMode renderingMode = RenderingMode.NONE;

	public GdxGraphics(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch,
					   OrthographicCamera camera) {
		this.shapeRenderer = shapeRenderer;
		this.spriteBatch = spriteBatch;
		this.camera = camera;

		circleRenderer = new CircleShaderRenderer();

		// Generates the fonts images from the TTF file
		FileHandle robotoF = Gdx.files.internal("res/lib/RobotoSlab-Regular.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(robotoF);

		// Font parameters for the standard font
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.color = Color.WHITE;
		parameter.size = 15;
		font = generator.generateFont(parameter);
		generator.dispose();

		// A camera that never moves
		this.fixedCamera = new OrthographicCamera();
		fixedCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		// Enable alpha blending for shape renderer
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Should be called to release all the resources that have been allocated in
	 * {@link GdxGraphics}. Normally called automatically.
	 */
	@Override
	public void dispose() {
		logoTex.dispose();
		circleTex.dispose();
		font.dispose();
		spriteBatch.dispose();

		if (getShaderRenderer() != null)
			getShaderRenderer().dispose();
	}

	/**
	 * When rendering with other methods than the one present here (for instance
	 * when using box2dlight), call this method to render shapes correctly.
	 */
	private void resetRenderingMode() {
		checkMode(RenderingMode.NONE);
	}

	/**
	 * Draw frame per second (FPS) information in the bottom left corner of the
	 * screen. The default {@link Color#WHITE} color is used.
	 *
	 * @see GdxGraphics#drawFPS(Color)
	 */
	public void drawFPS() {
		drawFPS(Color.WHITE);
	}

	/**
	 * Draw frame per second (FPS) information in the bottom left corner of the
	 * screen using a custom color.
	 *
	 * @param c the text color
	 */
	public void drawFPS(Color c) {
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		Color oldColor = font.getColor();
		font.setColor(c);
		drawString(5, 15, "FPS: " + Gdx.graphics.getFramesPerSecond());
		font.setColor(oldColor);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draw the University of Applied Sciences Western Switzerland logo in the
	 * bottom right corner of the screen.
	 * <p/>
	 * As this project is mainly aimed the students from the <a
	 * href='http://inf1.begincoding.net'> inf1 course given at the HES-SO
	 * Valais</a>, systems engineering, it is nice to have a logo for them !
	 *
	 * @see GdxGraphics#drawSchoolLogoUpperRight()
	 */
	public void drawSchoolLogo() {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.draw(logoTex, getScreenWidth() - logoTex.getWidth(), 0);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draw the school logo in the upper right corner of the screen.
	 *
	 * @see GdxGraphics#drawSchoolLogo()
	 */
	public void drawSchoolLogoUpperRight() {
		checkMode(RenderingMode.SPRITE);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.draw(logoTex, width - logoTex.getWidth(),
				height - logoTex.getHeight());
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Switch to the correct rendering mode and, if needed, call the required
	 * {@link #begin()} and {@link #end()} methods.
	 *
	 * @param mode {@link RenderingMode}
	 */
	private void checkMode(RenderingMode mode) {
		if (mode == renderingMode) {
			return;
		}

		switch (renderingMode) {
			case SPRITE:
				spriteBatch.end();
				break;

			case NONE:
				break;

			default:
				shapeRenderer.end();
				break;
		}

		switch (mode) {
			case SHAPE_LINE:
				shapeRenderer.begin(ShapeType.Line);
				shapeRenderer.setProjectionMatrix(camera.combined);
				break;

			case SHAPE_FILLED:
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setProjectionMatrix(camera.combined);
				break;

			case SHAPE_POINT:
				shapeRenderer.begin(ShapeType.Point);
				shapeRenderer.setProjectionMatrix(camera.combined);
				shapeRenderer.identity();
				break;

			case SPRITE:
				spriteBatch.begin();
				spriteBatch.setProjectionMatrix(camera.combined);
				break;

			case NONE:
				break;
		}

		renderingMode = mode;

	}

	/**
	 * A very simple way to change line width for shape operators (line, ...).
	 *
	 * @param width the width of the pen to use as from now
	 */
	public void setPenWidth(float width) {
		/*
		 * TODO : Line capping is not working nicely for non vertical lines in OpenGL.
		 * This is a known problem but at the moment we have decided not
		 * to implement any solution
		 */
		// Mode should be put on sprite, this is not a mistake
		checkMode(RenderingMode.SPRITE);
		Gdx.gl20.glLineWidth(width);
	}

	/**
	 * Get the height of the rendering surface.
	 * @return the height of the rendering surface
	 */
	public int getScreenHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * Get the width of the rendering surface.
	 * @return the width of the rendering surface
	 */
	public int getScreenWidth() {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Clear the screen using the {@link #backgroundColor}.
	 * @see GdxGraphics#clear(Color)
	 */
	public void clear() {
		clear(backgroundColor);
	}

	/**
	 * Begin drawing.
	 * <p/>
	 * Usually called by {@code Game2D} before
	 * {@code ch.hevs.gdx2d.desktop.PortableApplication#onGraphicRender(GdxGraphics)}.
	 */
	public void begin() {
		// Empty
	}

	/**
	 * End drawing.
	 * <p/>
	 * Usually called by {@code Game2D} before
	 * {@code ch.hevs.gdx2d.desktop.PortableApplication#onGraphicRender(GdxGraphics)}.
	 */
	public void end() {
		checkMode(RenderingMode.NONE);
	}

	/**
	 * Clear the screen with a given {@link Color}.
	 *
	 * @param c The color to clear the screen with
	 * @see GdxGraphics#clear()
	 */
	public void clear(Color c) {
		Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Set the current color {@link #currentColor} used for graphics drawing.
	 *
	 * @param c the new color to use
	 */
	public void setColor(Color c) {
		currentColor = c;
		shapeRenderer.setColor(c);
		font.setColor(c);
	}

	/**
	 * Set the color of a pixel using the current color {@link #currentColor}.
	 *
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @see GdxGraphics#setPixel(float, float, Color)
	 */
	public void setPixel(float x, float y) {
		setPixel(x, y, currentColor);
	}

	/**
	 * Sets the color of a pixel using a custom {@link Color}.
	 * <p/>
	 * The {@link #currentColor} is not modified.
	 *
	 * @param x the X coordinate of the pixel
	 * @param y the Y coordinate of the pixel
	 * @param c the new {@link Color} of the pixel
	 * @see GdxGraphics#setPixel(float, float)
	 */
	public void setPixel(float x, float y, Color c) {
		checkMode(RenderingMode.SHAPE_POINT);
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Set the background color used when pixels are cleared.
	 *
	 * @param c the new background color to use
	 */
	public void setBackgroundColor(Color c) {
		backgroundColor = c;
	}

	/**
	 * Clear a pixel and set it to the {@link GdxGraphics#backgroundColor}.
	 *
	 * @param x the X coordinate of the pixel
	 * @param y the Y coordinate of the pixel
	 * @see GdxGraphics#clearPixel(float, float, Color)
	 */
	public void clearPixel(float x, float y) {
		clearPixel(x, y, backgroundColor);
	}

	/**
	 * Clear a pixel and set it to a custom {@link Color}.
	 * <p/>
	 * The {@link #backgroundColor} is not modified.
	 *
	 * @param x the X coordinate of the pixel
	 * @param y the Y coordinate of the pixel
	 * @see GdxGraphics#clearPixel(float, float)
	 */
	public void clearPixel(float x, float y, Color c) {
		checkMode(RenderingMode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draw a line with the current {@link #currentColor}.
	 * <p/>
	 * The line starts at {@code P(p1x, p1y)} and ends at {@code P(p1x, p1y)}.
	 *
	 * @param p1x start coordinate X
	 * @param p1y start coordinate Y
	 * @param p2x end coordinate X
	 * @param p2y end coordinate Y
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y) {
		checkMode(RenderingMode.SHAPE_LINE);
		shapeRenderer.identity();
		shapeRenderer.setColor(currentColor);
		shapeRenderer.line(p1x, p1y, p2x, p2y);
	}

	/**
	 * Draws a line with a specific color.
	 * <p/>
	 * The line starts at {@code P(p1x, p1y)} and ends at {@code P(p1x, p1y)}.
	 * The {@link #currentColor} is not modified.
	 *
	 * @param p1x start coordinate X
	 * @param p1y start coordinate Y
	 * @param p2x end coordinate X
	 * @param p2y end coordinate Y
	 * @param c   the color of the line
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y, Color c) {
		shapeRenderer.setColor(c);
		drawLine(p1x, p1y, p2x, p2y);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draw an empty (not filled) rectangle.
	 *
	 * @param x     X position of the center of the rectangle
	 * @param y     Y position of the center of the rectangle
	 * @param w     width of the rectangle, in pixel
	 * @param h     height of the rectangle, in pixel
	 * @param angle rotation of the rectangle, in degrees
	 * @see #drawFilledRectangle(float, float, float, float, float, Color)
	 */
	public void drawRectangle(float x, float y, float w, float h, float angle) {
		checkMode(RenderingMode.SHAPE_LINE);
		shapeRenderer.identity();
		shapeRenderer.translate(x, y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -h / 2, w, h);
	}

	/**
	 * Draw a filled rectangle.
	 *
	 * @param x     X position of the center of the rectangle
	 * @param y     Y position of the center of the rectangle
	 * @param w     width of the rectangle, in pixel
	 * @param h     height of the rectangle, in pixel
	 * @param angle rotation of the rectangle, in degrees
	 * @see #drawFilledRectangle(float, float, float, float, float, Color)
	 */
	public void drawFilledRectangle(float x, float y, float w, float h, float angle) {
		checkMode(RenderingMode.SHAPE_FILLED);
		shapeRenderer.identity();
		shapeRenderer.translate(x, y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -h / 2, w, h);
	}

	/**
	 * Draw a filled rectangle with a custom color.
	 * <p/>
	 * The {@link #currentColor} is not modified.
	 *
	 * @param x     X position of the center of the rectangle
	 * @param y     Y position of the center of the rectangle
	 * @param w     width of the rectangle, in pixel
	 * @param h     height of the rectangle, in pixel
	 * @param angle rotation of the rectangle, in degrees
	 * @param c     the color to fill the rectangle with
	 */
	public void drawFilledRectangle(float x, float y, float w, float h, float angle, Color c) {
		shapeRenderer.setColor(c);
		drawFilledRectangle(x, y, w, h, angle);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draw an empty (non filled) circle with a custom color.
	 * <p/>
	 * The {@link #currentColor} is not modified.
	 *
	 * @param centerX center X position of the circle
	 * @param centerY center Y position of the circle
	 * @param radius  radius of the circle, in pixel
	 * @param c       the color to fill the circle with
	 */
	public void drawCircle(float centerX, float centerY, float radius, Color c) {
		shapeRenderer.setColor(c);
		drawCircle(centerX, centerY, radius);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draw an empty (non filled) circle.
	 *
	 * @param centerX center X position of the circle
	 * @param centerY center Y position of the circle
	 * @param radius  radius of the circle, in pixel
	 * @see #drawCircle(float, float, float, Color)
	 */
	public void drawCircle(float centerX, float centerY, float radius) {
		checkMode(RenderingMode.SHAPE_LINE);
		shapeRenderer.circle(centerX, centerY, radius);
	}

	/**
	 * Draw an anti-aliased circle (with nice border without jaggies).
	 * <p />
	 * The performance for drawing such circles is still pretty slow. You should
	 * avoid this method when you have many circles to draw.
	 * <p/>
	 * The {@link #currentColor} is not modified.
	 *
	 * @param centerX center X position of the circle
	 * @param centerY center Y position of the circle
	 * @param radius  radius of the circle, in pixel
	 * @param c       the color to fill the circle with
	 */
	public void drawAntiAliasedCircle(float centerX, float centerY, float radius, Color c) {
		circleRenderer.setColor(new Vector3(c.r, c.g, c.b));
		circleRenderer.setPosition(new Vector2(centerX, centerY));
		circleRenderer.setRadius(radius);
		circleRenderer.render();
	}

	/**
	 * Draw a filled circle with a custom color.
	 * <p/>
	 * Depending on the circle radius, either a texture is drawn or a "real" circle.
	 * This method is fast.
	 *
	 * @param centerX center X position of the circle
	 * @param centerY center Y position of the circle
	 * @param radius  radius of the circle, in pixel
	 * @param c       the color to fill the circle with
	 */
	public void drawFilledCircle(float centerX, float centerY, float radius, Color c) {
		// TODO Do this with a shader instead of formulas or textures !!
		// Draw big circles with mathematical formulas
		if (radius > 100) {
			checkMode(RenderingMode.SHAPE_FILLED);
			shapeRenderer.setColor(c);
			shapeRenderer.identity();
			shapeRenderer.circle(centerX, centerY, radius);
			shapeRenderer.setColor(currentColor);
		} else {
			// Draw smaller circles with an image, this goes faster
			Color old = spriteBatch.getColor();
			checkMode(RenderingMode.SPRITE);
			spriteBatch.setColor(c);
			spriteBatch.draw(circleTex, centerX - 64, centerY - 64, 64, 64,
					128, 128, radius / 64, radius / 64, 0, 0, 0, 128, 128,
					false, false);
			spriteBatch.setColor(old);
		}
	}

	/**
	 * Draw a filled circle with a border.
	 * <p/>
	 * This does not work really well as of now.
	 * This should be improved in a future version of the library.
	 *
	 * @param centerX center X position of the circle
	 * @param centerY center Y position of the circle
	 * @param radius  radius of the circle to be drawn. Note that the actual
	 *                circle has a radius which is 3 pixels larger
	 * @param inner   inner {@link Color} (used inside the circle)
	 * @param outer   outer {@link Color} (used for the border)
	 */
	public void drawFilledBorderedCircle(float centerX, float centerY,
										 float radius, Color inner, Color outer) {
		checkMode(RenderingMode.SPRITE);
		// This is not really beautiful but it works more or less
		// TODO Improve this
		drawFilledCircle(centerX, centerY, radius + 3, outer);
		drawFilledCircle(centerX, centerY, radius + 1, inner);
	}

	/**
	 * Draw a text at a specified position.
	 * <p/>
	 * The default font type and font size is used and the text is left aligned.
	 * The text position reference is the top left corner.
	 *
	 * @param posX left position of the text
	 * @param posY top position of the text
	 * @param str  the text to display on the screen
	 * @see #drawString(float, float, String, BitmapFont, int)
	 */
	public void drawString(float posX, float posY, String str) {
		drawString(posX, posY, str, font);
	}

	/**
	 * Draw a text with a specific font at a specified position.
	 * <p/>
	 * The text position reference is the top left edge. By default, the text is left aligned.
	 *
	 * @param posX left position of the text
	 * @param posY Y top position of the text
	 * @param str  the text to display on the screen
	 * @param f    the custom font to use
	 */
	public void drawString(float posX, float posY, String str, BitmapFont f) {
		drawString(posX, posY, str, f, Align.left); // Default alignment is left
	}

	/**
	 * Draw a text at a specified position.
	 * <p/>
	 * The text position reference is the top left edge. Can be left, right or center aligned.
	 *
	 * @param posX left, right or center position of the text
	 * @param posY Y top position of the text
	 * @param str  the text to display on the screen
	 * @param align left, center or right aligned
	 */
	public void drawString(float posX, float posY, String str, int align) {
		drawString(posX, posY, str, font, align);
	}

	/**
	 * Draw a text with a specific font at a specified position.
	 * <p/>
	 * The text position reference is the top left edge. Can be left, right or center aligned.
	 *
	 * @param posX  left, right or center position of the text
	 * @param posY Y top position of the text
	 * @param str  the text to display on the screen
	 * @param f     the custom font to use
	 * @param align left, center or right aligned
	 */
	public void drawString(float posX, float posY, String str, BitmapFont f, int align) {
		checkMode(RenderingMode.SPRITE);
		final float w = f.getScaleX();

		// Draw the string (reference is the top left edge)
		f.draw(spriteBatch, str, posX, posY, w, align, false);
	}

	/**
	 * Draw a text in the middle of the screen.
	 * <p/>
	 * The default font type and font size is used.
	 *
	 * @param posY centered Y position of the text
	 * @param str  the text to display
	 */
	public void drawStringCentered(float posY, String str) {
		drawString(getScreenWidth() / 2.0f, posY, str, Align.center);
	}

	/**
	 * Draw a text in the middle of the screen with a specific font.
	 *
	 * @param posY centered Y position of the text
	 * @param str  the text to display
	 * @param f    the custom font to use
	 */
	public void drawStringCentered(float posY, String str, BitmapFont f) {
		drawString(getScreenWidth() / 2.0f, posY, str, f, Align.center);
	}

	/**
	 * Draw an image in the background that will not move with the camera.
	 *
	 * @param t the image to draw
	 * @param i X coordinate in the screen space
	 * @param j Y coordinate in the screen space
	 */
	public void drawBackground(BitmapImage t, float i, float j) {
		drawBackground(t.getImage(), i, j);
	}

	/**
	 * Draws a texture in background that will not move with the camera.
	 *
	 * @param t the texture to draw
	 * @param i X coordinate of the center of the texture, in the screen space
	 * @param j Y coordinate of the center of the texture, in the screen space
	 */
	public void drawBackground(Texture t, float i, float j) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.disableBlending();
		spriteBatch.draw(t, i, j);
		spriteBatch.enableBlending();
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draw a picture at a specific position.
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param bitmap the image to draw
	 */
	public void drawPicture(float posX, float posY, BitmapImage bitmap) {
		drawTransformedPicture(posX, posY, 0, 1, bitmap);
	}

	/**
	 * Draws a picture at a specific position with a scale and an angle.
	 * <p/>
	 * The center of rotation is located in the middle of the image.
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param angle the rotation angle, in degree
	 * @param scale scale of the image to draw ({@code 1} is not scaled)
	 * @param bitmap the image to draw
	 */
	public void drawTransformedPicture(float posX, float posY, float angle,
									   float scale, BitmapImage bitmap) {
		drawTransformedPicture(posX, posY, bitmap.getImage().getWidth() / 2,
				bitmap.getImage().getHeight() / 2, angle, scale, bitmap);
	}

	/**
	 * Draw a stretched picture at a specific position.
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param angle the rotation angle, in degree
	 * @param width  the width of the resulting image (which will be scaled for display)
	 * @param height the height of the resulting image
	 * @param bitmap the image to draw
	 */
	public void drawTransformedPicture(float posX, float posY, float angle,
									   float width, float height, BitmapImage bitmap) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), posX - width, posY
						- height, width, height, width * 2, height * 2, 1.0f, 1.0f,
				angle);
	}

	/**
	 * Draw a picture at a specific position with a custom center of rotation.
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param centerX X position of the rotation center
	 * @param centerY Y position of the rotation center
	 * @param angle the rotation angle, in degree
	 * @param scale scale of the image to draw ({@code 1} is not scaled)
	 * @param bitmap the image to draw
	 */
	public void drawTransformedPicture(float posX, float posY, float centerX,
									   float centerY, float angle, float scale, BitmapImage bitmap) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), posX
						- bitmap.getRegion().getRegionWidth() / 2, posY
						- bitmap.getRegion().getRegionHeight() / 2, centerX, centerY,
				(float) bitmap.getImage().getWidth(), (float) bitmap.getImage()
						.getHeight(), scale, scale,
				angle);
	}

	/**
	 * Draw a picture at a specific position with transparency (alpha).
	 *
	 * @param pos   the {@link Vector2} position to draw the image
	 * @param alpha transparency value (0.0 is transparent, 1.0 is opaque)
	 * @param img   the image to draw
	 */
	public void drawAlphaPicture(Vector2 pos, float alpha, BitmapImage img) {
		drawAlphaPicture(pos.x, pos.y, alpha, img);
	}

	/**
	 * Draw a picture at a specific position with transparency (alpha).
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param alpha transparency value (0.0 is transparent, 1.0 is opaque)
	 * @param img   the image to draw
	 * @see #drawAlphaPicture(Vector2, float, BitmapImage)
	 */
	public void drawAlphaPicture(float posX, float posY, float alpha, BitmapImage img) {
		drawAlphaPicture(posX, posY, 0, 1.0f, alpha, img);
	}

	/**
	 * Draw a picture at a specific position with transparency (alpha).
	 *
	 * @param posX X coordinate in the screen space
	 * @param posY Y coordinate in the screen space
	 * @param angle the rotation angle, in degree
	 * @param scale scale of the image to draw ({@code 1} is not scaled)
	 * @param alpha transparency value (0.0 is transparent, 1.0 is opaque)
	 * @param img   the image to draw
	 * @see GdxGraphics#drawAlphaPicture(Vector2, float, BitmapImage)
	 */
	public void drawAlphaPicture(float posX, float posY, float angle, float scale, float alpha,
								 BitmapImage img) {
		Color old = spriteBatch.getColor();
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
		drawTransformedPicture(posX, posY, angle, scale, img);
		spriteBatch.setColor(old);
	}

	/**
	 * Draw an empty (not filled) {@link Polygon}.
	 *
	 * @param p the polygon to draw
	 */
	public void drawPolygon(Polygon p) {
		checkMode(RenderingMode.SHAPE_LINE);
		shapeRenderer.identity();
		shapeRenderer.polygon(p.getVertices());
	}

	/**
	 * Draw a filled {@link Polygon}.
	 *
	 * @param p the polygon to draw
	 * @param c the color to fill the polygon with
	 */
	public void drawFilledPolygon(Polygon p, Color c) {
		float[] vertices = p.getEarClippedVertices();

		checkMode(RenderingMode.SHAPE_FILLED);
		shapeRenderer.setColor(c);
		shapeRenderer.identity();

		for (int i = 0; i < vertices.length; i += 6) {
			shapeRenderer.triangle(vertices[i], vertices[i + 1],
					vertices[i + 2], vertices[i + 3], vertices[i + 4],
					vertices[i + 5]);
		}

		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Get the current camera.
	 * @return the current {@link OrthographicCamera}
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/****************************************************
	 * Camera stuff
	 ****************************************************/

	/**
	 * Move the camera to a fixed position.
	 *
	 * @param x X position of the camera
	 * @param y Y position of the camera
	 */
	public void moveCamera(float x, float y) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(x, y);
		cameraUpdated();
	}

	private Vector2 inside(Vector2 v, Vector2 p1, Vector2 p2) {
		return new Vector2((float) Math.min(Math.max(p1.x, v.x), p2.x),
				(float) Math.min(Math.max(p1.y, v.y), p2.y));
	}

	/**
	 * Point the camera to the center of interest limited by the game size.
	 *
	 * @param interest_x X position of the center of interest
	 * @param interest_y Y position of the center of interest
	 * @param world_width max width of the game
	 * @param world_height max height of the game
	 */
	public void moveCamera(float interest_x, float interest_y, float world_width, float world_height) {
		Vector2 camera_min = new Vector2(0,0);
		Vector2 camera_max = new Vector2(
			world_width - Gdx.graphics.getWidth() * camera.zoom,
			world_height - Gdx.graphics.getHeight() * camera.zoom
		);

		// Center the camera on the interest
		Vector2 pos = new Vector2(
				interest_x, interest_y).sub(Gdx.graphics.getWidth() / 2 * camera.zoom, Gdx.graphics.getHeight() / 2 * camera.zoom);

		// Limit the camera position inside the game
		pos = inside(pos, camera_min, camera_max);

		moveCamera(pos.x, pos.y);
	}


	/**
	 * Resets the camera at its original location, in the center of the screen.
	 */
	public void resetCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraUpdated();
	}

	/**
	 * Translate the camera by a value on the X and Y axes.
	 *
	 * @param dx displacement along the x axis, can be positive or negative
	 * @param dy displacement along the y axis, can be positive or negative
	 */
	public void scroll(float dx, float dy) {
		camera.translate(dx, dy);
		cameraUpdated();
	}

	/**
	 * Zooms in and out the camera.
	 *
	 * @param factor zoom factor (1 is the default zoom-level)
	 */
	public void zoom(float factor) {
		camera.zoom = factor;
		cameraUpdated();
	}

	private void cameraUpdated() {
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draw the current assigned shader.
	 */
	public void drawShader() {
		drawShader(getScreenWidth() / 2, getScreenHeight() / 2, 0f);
	}

	/****************************************************
	 * Shader stuff
	 ****************************************************/

	/**
	 * Draw the current assigned shader.
	 *
	 * @param shaderTime the current time that is passed to the fragment shader
	 */
	public void drawShader(float shaderTime) {
		drawShader(getScreenWidth() / 2, getScreenHeight() / 2, shaderTime);
	}

	/**
	 * Request shader rendering that passes an uniform value with the current
	 * rendering location.
	 *
	 * @param posX       value passed to the shader, might be discarded
	 * @param posY       value passed to the shader, might be discarded
	 * @param shaderTime value passed to the shader, might be discarded
	 */
	public void drawShader(int posX, int posY, float shaderTime) {
		if (getShaderRenderer() != null)
			getShaderRenderer().render(posX, posY, shaderTime);
		else {
			try {
				throw new Exception("Shader renderer not set, aborting.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resetRenderingMode();
	}

	/**
	 * Set a shader to be drawn.
	 *
	 * @param s the path of the shader file
	 */
	public void setShader(String s) {
		setShader(s, getScreenWidth(), getScreenHeight());
	}

	/**
	 * Set a shader to be drawn with a specific size.
	 *
	 * @param s the path of the shader file
	 * @param height height of the texture to draw the shader on
	 * @param width  width of the texture to draw the shader on
	 */
	public void setShader(String s, int width, int height) {
		// TODO Allowing multiple shader at once would be nice
		// Dispose of the allocated resources
		if (getShaderRenderer() != null) {
			getShaderRenderer().dispose();
		}

		setShaderRenderer(new ShaderRenderer(s, width, height));
	}

	/**
	 * Get the shader renderer.
	 * @return the shader renderer
	 */
	public ShaderRenderer getShaderRenderer() {
		return shaderRenderer;
	}

	/**
	 * Set a custom shader renderer.
	 * @param shaderRenderer the shader renderer
	 */
	public void setShaderRenderer(ShaderRenderer shaderRenderer) {
		this.shaderRenderer = shaderRenderer;
	}

	/**
	 * Set blending function on the SpriteBatch.
	 *
	 * @param srcFunc source blending function
	 * @param dstFunc destination blending function
	 *
	 * @see #resetBlend()
	 */
	public void setBlendFunction(int srcFunc, int dstFunc) {
		spriteBatch.setBlendFunction(srcFunc, dstFunc);
	}

	/**
	 * Restore the default blending function on the SpriteBatch.
	 */
	public void resetBlend() {
		setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y, float originX,
			float originY, float width, float height, float scaleX,
			float scaleY, float rotation, int srcX, int srcY, int srcWidth,
			int srcHeight, boolean flipX, boolean flipY) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y, originX, originY, width, height,
				scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight,
				flipX, flipY);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y, float width,
			float height, int srcX, int srcY, int srcWidth, int srcHeight,
			boolean flipX, boolean flipY) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y, width, height, srcX, srcY, srcWidth,
				srcHeight, flipX, flipY);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y, srcX, srcY, srcWidth, srcHeight);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y, float width,
			float height, float u, float v, float u2, float v2) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y, width, height, u, v, u2, v2);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float x, float y, float width,
			float height) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, x, y, width, height);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(Texture texture, float[] spriteVertices, int offset,
			int count) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(texture, spriteVertices, offset, count);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(TextureRegion region, float x, float y) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(region, x, y);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(TextureRegion region, float x, float y, float width,
			float height) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(region, x, y, width, height);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(TextureRegion region, float x, float y, float originX,
			float originY, float width, float height, float scaleX,
			float scaleY, float rotation) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(region, x, y, originX, originY, width, height, scaleX,
				scaleY, rotation);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(TextureRegion region, float x, float y, float originX,
			float originY, float width, float height, float scaleX,
			float scaleY, float rotation, boolean clockwise) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(region, x, y, originX, originY, width, height, scaleX,
				scaleY, rotation, clockwise);
	}

	/**
	 * Draw on the SpriteBatch.
	 */
	public void draw(TextureRegion region, float width, float height,
			Affine2 transform) {
		checkMode(RenderingMode.SPRITE);
		spriteBatch.draw(region, width, height, transform);
	}

	// For optimizing the current rendering mode and minimizing the number of
	// calls to begin() and end() in SpriteBatch.
	private enum RenderingMode {
		NONE, SHAPE_FILLED, SHAPE_LINE, SHAPE_POINT, SPRITE
	}

	/**
	 * Get the current color of the SpriteBatch.
	 *
	 * @return the current color of the SpriteBatch
	 */
	public Color sbGetColor() {
		return spriteBatch.getColor();
	}

	/**
	 * Set the current color of the SpriteBatch.
	 *
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @param a alpha
	 */
	public void sbSetColor(float r, float g, float b, float a) {
		spriteBatch.setColor(r, g, b, a);
	}

	/**
	 * Set the current color of the SpriteBatch.
	 *
	 * @param col the color
	 */
	public void sbSetColor(Color col) {
		spriteBatch.setColor(col);
	}

	/**
	 * Flush the current SpriteBatch.
	 */
	public void sbFlush() {
		spriteBatch.flush();
	}

	/**
	 * Begin custom rendering.
	 * <p/>
	 * Must be called before a custom renderer.
	 */
	public void beginCustomRendering() {
		checkMode(RenderingMode.NONE);
	}

	/**
	 * Begin custom rendering.
	 * <p/>
	 * Must be called after a custom renderer.
	 */
	public void endCustomRendering() {
		// Do nothing at this time, but can be useful later...
	}
}
