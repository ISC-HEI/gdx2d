package hevs.gdx2d.lib;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.lib.renderers.CircleShaderRenderer;
import hevs.gdx2d.lib.renderers.ShaderRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * A game graphics implementation for LibGDX based on the interface of
 * {@code FunGraphics} for <a href="http://inf1.begincoding.net">INF1 class</a>
 * given at the UAS Western Switzerland, Sion.
 * 
 * <p>
 * The objective of this implementation is to provide a simple, yet powerful,
 * set of graphics primitives used for the projects that take place in this
 * course.
 * </p>
 * 
 * <p>
 * Comments are welcome !
 * </p>
 * 
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton (chn)
 * @author Christopher Métrailler (mei)
 * @version 1.61
 */
public class GdxGraphics implements Disposable {
	/**
	 * For camera operations
	 */
	protected OrthographicCamera camera, fixedcamera;

	public SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;

	/**
	 * Used for rendering anti-aliased circles
	 */
	private CircleShaderRenderer circleRenderer;

	/**
	 * Used for rendering custom shaders
	 */
	private ShaderRenderer shaderRenderer;

	protected Color currentColor = Color.WHITE;
	protected Color backgroundColor = Color.BLACK;

	// The standard font
	protected BitmapFont font;

	/*
	 * For optimizing the current rendering mode and minimizing the number of
	 * calls to begin() and end() in spriteBatch
	 */
	private enum t_rendering_mode {
		SHAPE_FILLED, SHAPE_LINE, SHAPE_POINT, SPRITE
	};

	private t_rendering_mode rendering_mode = t_rendering_mode.SPRITE;

	// For sprite-based logo
	final protected Texture logoTex = new Texture(
			Gdx.files.internal("lib/logo_hes.png"));
	final protected Texture circleTex = new Texture(
			Gdx.files.internal("lib/circle.png"));

	public GdxGraphics(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch,
			OrthographicCamera camera) {
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
		fixedcamera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// Enable alpha blending for shape renderer
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Should be called to release all the resources that have been allocated in
	 * {@link GdxGraphics}. Normally called automatically
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
	 * when using box2dlight), call this method to render shapes correctly
	 */
	public void resetRenderingMode() {
		checkmode(t_rendering_mode.SPRITE);
		checkmode(t_rendering_mode.SHAPE_LINE);
	}

	/**
	 * Draws frame per second (FPS) information in the bottom left corner of the
	 * screen. The default {@link Color#WHITE} color is used.
	 * 
	 * @see GdxGraphics#drawFPS(Color)
	 */
	public void drawFPS() {
		drawFPS(Color.WHITE);
	}

	/**
	 * Draws frame per second (FPS) information in the bottom left corner of the
	 * screen using a custom color.
	 * 
	 * @param c
	 *            the text color
	 */
	public void drawFPS(Color c) {
		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		Color oldColor = font.getColor();
		font.setColor(c);
		drawString(5, 15, "FPS: " + Gdx.graphics.getFramesPerSecond());
		font.setColor(oldColor);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draws the University of Applied Sciences Western Switzerland logo in the
	 * bottom right corner of the screen.<br>
	 * As this project is mainly aimed the students from the <a
	 * href='http://inf1.begincoding.net'> inf1 course given at the HES-SO
	 * Valais</a>, systems engineering, it is nice to have a logo for them !
	 * 
	 * @see GdxGraphics#drawSchoolLogoUpperRight()
	 */
	public void drawSchoolLogo() {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		spriteBatch.draw(logoTex, getScreenWidth() - logoTex.getWidth(), 0);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Draws the school logo in the upper right corner of the screen.
	 * 
	 * @see GdxGraphics#drawSchoolLogo()
	 */
	public void drawSchoolLogoUpperRight() {
		checkmode(t_rendering_mode.SPRITE);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spriteBatch.setProjectionMatrix(fixedcamera.combined);
		spriteBatch.draw(logoTex, width - logoTex.getWidth(),
				height - logoTex.getHeight());
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Switches to the correct rendering mode if needed, calling the required
	 * begin and end.
	 * 
	 * @param mode
	 *            {@link t_rendering_mode}
	 */
	private void checkmode(t_rendering_mode mode) {
		if (rendering_mode != t_rendering_mode.SPRITE) {
			shapeRenderer.end();
		} else {
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
	 * 
	 * @param width
	 *            The width of the pen to use as from now
	 */
	public void setPenWidth(float width) {
		/**
		 * TODO : Line capping is not working nicely for non vertical lines in
		 * OpenGL. This is a known problem but at the moment we have decided not
		 * to implement any solution
		 */
		// Mode should be put on sprite, this is not a mistake
		checkmode(t_rendering_mode.SPRITE);
		Gdx.gl20.glLineWidth(width);
	}

	/**
	 * @return the height of the screen
	 */
	public int getScreenHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * @return the width of the rendering surface
	 */
	public int getScreenWidth() {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Clears the screen using the {@link #backgroundColor}.
	 * 
	 * @see GdxGraphics#clear(Color)
	 */
	public void clear() {
		clear(backgroundColor);
	}

	boolean first = true;

	/**
	 * Clears the screen with a given {@link Color}.
	 * 
	 * @see GdxGraphics#clear()
	 * @param c
	 */
	public void clear(Color c) {
		Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (!first && rendering_mode == t_rendering_mode.SPRITE)
			spriteBatch.end();
		else
			first = !first;

		if (rendering_mode == t_rendering_mode.SPRITE)
			spriteBatch.begin();
	}

	/**
	 * Sets the current color {@link #currentColor} used for graphics drawing.
	 * 
	 * @param c
	 *            the new color to use
	 */
	public void setColor(Color c) {
		currentColor = c;
		shapeRenderer.setColor(c);
		font.setColor(c);
	}

	/**
	 * Sets the color of a pixel using the current color {@link #currentColor}.
	 * 
	 * @see GdxGraphics#setPixel(float, float, Color)
	 * @param x
	 * @param y
	 */
	public void setPixel(float x, float y) {
		setPixel(x, y, currentColor);
	}

	/**
	 * Sets the color of a pixel using a custom {@link Color}.
	 * 
	 * @see GdxGraphics#setPixel(float, float)
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setPixel(float x, float y, Color c) {
		checkmode(t_rendering_mode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Sets the background color {@link GdxGraphics#backgroundColor} to use to
	 * clear pixels.
	 * 
	 * @param c
	 *            the new background color to use
	 */
	public void setBackgroundColor(Color c) {
		backgroundColor = c;
	}

	/**
	 * Clear a pixel using the {@link GdxGraphics#backgroundColor}.
	 * 
	 * @see GdxGraphics#clearPixel(float, float, Color)
	 * @param x
	 * @param y
	 */
	public void clearPixel(float x, float y) {
		clearPixel(x, y, backgroundColor);
	}

	/**
	 * Clear a pixel using the a custom {@link Color}.
	 * 
	 * @see GdxGraphics#clearPixel(float, float)
	 * @param x
	 * @param y
	 */
	public void clearPixel(float x, float y, Color c) {
		checkmode(t_rendering_mode.SHAPE_POINT);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draws a line that start at P(p1x, p1y) and ends at P(p2x, p2y)
	 * 
	 * @param p1x
	 *            Start coordinate X
	 * @param p1y
	 *            Start coordinate Y
	 * @param p2x
	 *            End coordinate X
	 * @param p2y
	 *            End coordinate Y
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.line(p1x, p1y, p2x, p2y);
	}

	/**
	 * Draws a line that start at P(p1x, p1y) and ends at P(p2x, p2y) with
	 * {@link Color} c
	 * 
	 * @param p1x
	 *            Start coordinate X
	 * @param p1y
	 *            Start coordinate Y
	 * @param p2x
	 *            End coordinate X
	 * @param p2y
	 *            End coordinate Y
	 * @param c
	 *            The color for drawing the line
	 */
	public void drawLine(float p1x, float p1y, float p2x, float p2y, Color c) {
		shapeRenderer.setColor(c);
		drawLine(p1x, p1y, p2x, p2y);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draws an empty rectangle
	 * 
	 * @see #drawFilledRectangle(float, float, float, float, float, Color)
	 * @param x
	 *            X position of the center of the rectangle
	 * @param y
	 *            Y position of the center of the rectangle
	 * @param w
	 *            Width of the rectangle, in pixels
	 * @param h
	 *            Height of the rectangle, in pixels
	 * @param angle
	 *            of rotation of the rectangle, in degrees
	 */
	public void drawRectangle(float x, float y, float w, float h, float angle) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.identity();
		shapeRenderer.translate(x, y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -h / 2, w, h);
	}

	/**
	 * Draws a filled rectangle
	 * 
	 * @see #drawFilledRectangle(float, float, float, float, float, Color)
	 * @param x
	 *            X position of the center of the rectangle
	 * @param y
	 *            Y position of the center of the rectangle
	 * @param w
	 *            Width of the rectangle
	 * @param h
	 *            Height of the rectangle
	 * @param angle
	 *            Rotation angle of the rectangle, in degrees
	 */
	public void drawFilledRectangle(float x, float y, float w, float h,
			float angle) {
		checkmode(t_rendering_mode.SHAPE_FILLED);
		shapeRenderer.identity();
		shapeRenderer.translate(x, y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -h / 2, w, h);
	}

	/**
	 * Draws a filled rectangle at a given location with a given color
	 * 
	 * @param x
	 *            X position of the center of the rectangle
	 * @param y
	 *            Y position of the center of the rectangle
	 * @param w
	 *            Width of the rectangle
	 * @param h
	 *            Height of the rectangle
	 * @param angle
	 *            Rotation angle of the rectangle, in degrees
	 * @param c
	 *            The color to fill the rectangle with
	 */
	public void drawFilledRectangle(float x, float y, float w, float h,
			float angle, Color c) {
		shapeRenderer.setColor(c);
		drawFilledRectangle(x, y, w, h, angle);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draws an empty circle
	 * 
	 * @param centerX
	 *            Center x position of the circle to draw
	 * @param centerY
	 *            Center y position of the circle to draw
	 * @param radius
	 *            The radius of the circle
	 * @param c
	 *            The {@link Color} to use
	 */
	public void drawCircle(float centerX, float centerY, float radius, Color c) {
		shapeRenderer.setColor(c);
		drawCircle(centerX, centerY, radius);
		shapeRenderer.setColor(currentColor);
	}

	/**
	 * Draws an empty circle
	 * 
	 * @see #drawCircle(float, float, float, Color)
	 * @param centerX
	 *            Center x position of the circle to draw
	 * @param centerY
	 *            Center y position of the circle to draw
	 * @param radius
	 *            The radius of the circle
	 */
	public void drawCircle(float centerX, float centerY, float radius) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.circle(centerX, centerY, radius);
	}

	/**
	 * Draws an anti-aliased circle (with nice border without jaggies). However,
	 * the performance for drawing such circles is still pretty slow. You should
	 * avoid this method when you have many circles to draw.
	 * 
	 * @param centerX
	 *            Center x position of the circle to draw
	 * @param centerY
	 *            Center y position of the circle to draw
	 * @param radius
	 *            The radius of the circle
	 * @param c
	 *            The {@link Color} to use
	 */
	public void drawAntiAliasedCircle(float centerX, float centerY,
			float radius, Color c) {
		circleRenderer.setColor(new Vector3(c.r, c.g, c.b));
		circleRenderer.setPosition(new Vector2(centerX, centerY));
		circleRenderer.setRadius(radius);
		circleRenderer.render();
	}

	/**
	 * Draws a filled circle with a given color. Depending on the size of the
	 * radius, either a texture is drawn or a "real" circle. This method goes
	 * fast.
	 * 
	 * @param centerX
	 *            Center x position of the circle to draw
	 * @param centerY
	 *            Center y position of the circle to draw
	 * @param radius
	 *            The radius of the circle
	 * @param c
	 *            The {@link Color} to use
	 */
	public void drawFilledCircle(float centerX, float centerY, float radius,
			Color c) {
		// TODO Do this with a shader instead of formulas or textures !!
		// Draw big circles with mathematical formulas
		if (radius > 100) {
			checkmode(t_rendering_mode.SHAPE_FILLED);
			shapeRenderer.setColor(c);
			shapeRenderer.identity();
			shapeRenderer.circle(centerX, centerY, radius);
			shapeRenderer.setColor(currentColor);
		} else {
			// Draw smaller circles with an image, this goes faster
			Color old = spriteBatch.getColor();
			checkmode(t_rendering_mode.SPRITE);
			spriteBatch.setColor(c);
			spriteBatch.draw(circleTex, centerX - 64, centerY - 64, 64, 64,
					128, 128, radius / 64, radius / 64, 0, 0, 0, 128, 128,
					false, false);
			spriteBatch.setColor(old);
		}
	}

	/**
	 * Draws a filled circle with a border. However, this does not work really
	 * well as of now. This should be improved in a future version of the
	 * libary.
	 * 
	 * @param centerX
	 *            Center x position of the circle to draw
	 * @param centerY
	 *            Center y position of the circle to draw
	 * @param radius
	 *            The radius of the circle to be drawn. Note that the actual
	 *            circle has a radius which is 3 pixels larger
	 * @param inner
	 *            The inner {@link Color} (used for inside the circle)s
	 * @param outer
	 *            The outer {@link Color} (used for the border)
	 */
	public void drawFilledBorderedCircle(float centerX, float centerY,
			float radius, Color inner, Color outer) {
		checkmode(t_rendering_mode.SPRITE);
		// This is not really beautiful but it works more or less
		// TODO Improve this
		drawFilledCircle(centerX, centerY, radius + 3, outer);
		drawFilledCircle(centerX, centerY, radius + 1, inner);
	}

	/**
	 * Draws a text at a specified position.
	 * 
	 * The default font type and font size is used and the text is left aligned.
	 * The text position reference is the top left corner.
	 * 
	 * @param posX
	 *            Left position of the text, in pixels
	 * @param posY
	 *            Top position of the text, in pixels
	 * @param str
	 *            The text to display on the screen
	 * @see {@link #drawString(float, float, String, BitmapFont, HAlignment)}
	 *      for more options
	 */
	public void drawString(float posX, float posY, String str) {
		drawString(posX, posY, str, font);
	}

	/**
	 * Draws a text with a specific font at a specified position.
	 * 
	 * The text position reference is the top left edge.
	 * 
	 * @param posX
	 *            left position of the text
	 * @param posY
	 *            Y top position of the text
	 * @param str
	 *            the text to display
	 * @param f
	 *            the custom font to use
	 */
	public void drawString(float posX, float posY, String str, BitmapFont f) {
		// Default alignment is left
		drawString(posX, posY, str, f, HAlignment.LEFT);
	}

	/**
	 * Draws a text at a specified position. Can be left, right or center
	 * aligned.
	 * 
	 * @param posX
	 *            left, right or center position of the text
	 * @param posY
	 *            Y top position of the text
	 * @param str
	 *            the text to display
	 * @param align
	 *            left, center or right align
	 */
	public void drawString(float posX, float posY, String str, HAlignment align) {
		drawString(posX, posY, str, font, align);
	}

	/**
	 * Draws a text with a specific font at a specified position. Can be left,
	 * right or center aligned.
	 * 
	 * @param posX
	 *            left, right or center position of the text
	 * @param posY
	 *            Y top position of the text
	 * @param str
	 *            the text to display
	 * @param f
	 *            the custom font to use
	 * @param align
	 *            left, center or right align
	 */
	public void drawString(float posX, float posY, String str, BitmapFont f,
			HAlignment align) {
		checkmode(t_rendering_mode.SPRITE);
		final float w = f.getBounds(str).width;
		final float alignmentWidth;
		switch (align) {
		case CENTER:
			alignmentWidth = w;
			posX -= w / 2.0f;
			break;
		case RIGHT:
			alignmentWidth = 0;
			break;
		default:
		case LEFT:
			alignmentWidth = w;
			break;
		}

		// Draw the string (reference is the top left edge)
		f.drawMultiLine(spriteBatch, str, posX, posY, alignmentWidth, align);
	}

	/**
	 * Draws a text in the middle of the screen.
	 * 
	 * The default font type and font size is used.
	 * 
	 * @param posY
	 *            Y top position of the text
	 * @param str
	 *            the text to display
	 */
	public void drawStringCentered(float posY, String str) {
		drawString(getScreenWidth() / 2.0f, posY, str, HAlignment.CENTER);
	}

	/**
	 * Draws a text in the middle of the screen with a specific font.
	 * 
	 * @param posY
	 *            centered Y position of the text
	 * @param str
	 *            the text to display
	 * @param f
	 *            the custom {@link BitmapFont} to use
	 */
	public void drawStringCentered(float posY, String str, BitmapFont f) {
		drawString(getScreenWidth() / 2.0f, posY, str, f, HAlignment.CENTER);
	}

	/**
	 * Draws an image in the background that will not move with the camera.
	 * 
	 * @param t
	 *            The {@link BitmapImage} image to draw
	 * @param i
	 *            x coordinate in the screen space
	 * @param j
	 *            y coordinate in the screen space
	 */
	public void drawBackground(BitmapImage t, float i, float j) {
		drawBackground(t.getImage(), i, j);
	}

	/**
	 * Draws a texture in background that will not move with the camera.
	 * 
	 * @param t
	 *            A {@link Texture}
	 * @param i
	 *            x coordinate of the center of the texture, in the screen space
	 * @param j
	 *            y coordinate of the center of the texture, in the screen space
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
	 * Draws a picture at position ({@code posX, posY}) using a
	 * {@link BitmapImage}
	 * 
	 * @param posX
	 * @param posY
	 * @param bitmap
	 */
	public void drawPicture(float posX, float posY, BitmapImage bitmap) {
		drawTransformedPicture(posX, posY, 0, 1, bitmap);
	}

	/**
	 * Draws a picture at position ({@code posX, posY}). Center of rotation is
	 * located in the middle of the image.
	 * 
	 * @param posX
	 * @param posY
	 * @param angle
	 * @param scale
	 * @param bitmap
	 */
	public void drawTransformedPicture(float posX, float posY, float angle,
			float scale, BitmapImage bitmap) {
		drawTransformedPicture(posX, posY, bitmap.getImage().getWidth() / 2,
				bitmap.getImage().getHeight() / 2, angle, scale, bitmap);
	}

	/**
	 * Draws a picture at position ({@code posX, posY}) with a fixed width and
	 * height (not the one of the {@link BitmapImage} itself).
	 * 
	 * @param posX
	 *            The x position at which we want to display the image
	 * @param posY
	 *            The y position at which we want to display the image
	 * @param angle
	 *            The image can be rotated by this angle (in degrees)
	 * @param width
	 *            The width of the resulting image (which will be scaled for
	 *            display)
	 * @param height
	 *            The height of the resulting image
	 * @param bitmap
	 *            The {@link BitmapImage} to use
	 */
	public void drawTransformedPicture(float posX, float posY, float angle,
			float width, float height, BitmapImage bitmap) {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), (float) posX - width, (float) posY
				- height, width, height, width * 2, height * 2, 1.0f, 1.0f,
				(float) angle);
	}

	/**
	 * Draws a picture at position ({@code posX, posY}) with a selectable center
	 * of rotation which is located at position offset by (
	 * {@code centerX, centerY}).
	 * 
	 * @param posX
	 *            The x position at which we want to display the image
	 * @param posY
	 *            The y position at which we want to display the image
	 * @param centerX
	 *            The x position of the rotation center
	 * @param centerY
	 *            The y position of the rotation center
	 * @param angle
	 *            The angle of rotation of the displayed image, in degrees
	 * @param scale
	 *            The scale factor of the image. {@code 1.0} is the default
	 *            value
	 * @param bitmap
	 *            The {@link BitmapImage} to use
	 */
	public void drawTransformedPicture(float posX, float posY, float centerX,
			float centerY, float angle, float scale, BitmapImage bitmap) {
		checkmode(t_rendering_mode.SPRITE);
		spriteBatch.draw(bitmap.getRegion(), posX
				- bitmap.getRegion().getRegionWidth() / 2, posY
				- bitmap.getRegion().getRegionHeight() / 2, centerX, centerY,
				(float) bitmap.getImage().getWidth(), (float) bitmap.getImage()
						.getHeight(), (float) scale, (float) scale,
				(float) angle);
	}

	/**
	 * Draws a picture at position {@code pos} with a selectable alpha
	 * (transparency). See
	 * {@link #drawAlphaPicture(float, float, float, float, float, float, float, BitmapImage)}
	 * 
	 * @param pos
	 *            The {@link Vector2} position at which we want to display the
	 *            image
	 * @param alpha
	 *            The alpha value to use. 0.0 is completely transparent, 1.0 is
	 *            opaque
	 * @param img
	 *            The {@link BitmapImage} to use
	 */
	public void drawAlphaPicture(Vector2 pos, float alpha, BitmapImage img) {
		drawAlphaPicture(pos.x, pos.y, alpha, img);
	}

	public void drawAlphaPicture(float posX, float posY, float scale,
			float alpha, BitmapImage img) {
		drawAlphaPicture(posX, posY, img.getImage().getWidth() / 2, img
				.getImage().getHeight() / 2, 0, 1.0f, alpha, img);
	}

	/**
	 * @see #drawAlphaPicture(Vector2, float, BitmapImage)
	 * @param posX
	 * @param posY
	 * @param alpha
	 * @param img
	 */
	public void drawAlphaPicture(float posX, float posY, float alpha,
			BitmapImage img) {
		drawAlphaPicture(posX, posY, img.getImage().getWidth() / 2, img
				.getImage().getHeight() / 2, 0, 1.0f, alpha, img);
	}

	/**
	 * @see GdxGraphics#drawAlphaPicture(Vector2, float, BitmapImage)
	 * @param posX
	 * @param posY
	 * @param centerX
	 * @param centerY
	 * @param angle
	 * @param scale
	 * @param alpha
	 * @param img
	 */
	public void drawAlphaPicture(float posX, float posY, float centerX,
			float centerY, float angle, float scale, float alpha,
			BitmapImage img) {
		Color old = spriteBatch.getColor();
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
		drawTransformedPicture(posX, posY, angle, scale, img);
		spriteBatch.setColor(old);
	}

	/**
	 * Draws a {@link Polygon} on the screen
	 * 
	 * @param p
	 *            The {@link Polygon} to draw
	 */
	public void drawPolygon(Polygon p) {
		checkmode(t_rendering_mode.SHAPE_LINE);
		shapeRenderer.identity();
		shapeRenderer.polygon(p.getVertices());
	}

	/**
	 * Draws a filled {@link Polygon} on the screen
	 * 
	 * @param polygon
	 *            The {@link Polygon} to draw
	 * @param c
	 *            The {@link Color} to use
	 */
	public void drawFilledPolygon(Polygon polygon, Color c) {
		float[] vertices = polygon.getEarClippedVertices();
		checkmode(t_rendering_mode.SHAPE_FILLED);
		shapeRenderer.setColor(c);
		shapeRenderer.identity();

		for (int i = 0; i < vertices.length; i += 6) {
			shapeRenderer.triangle(vertices[i], vertices[i + 1],
					vertices[i + 2], vertices[i + 3], vertices[i + 4],
					vertices[i + 5]);
		}

		shapeRenderer.setColor(currentColor);
	}

	/****************************************************
	 * Camera stuff
	 ****************************************************/

	/**
	 * @return The current {@link OrthographicCamera}
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Move the camera to a fixed position
	 * 
	 * @param x
	 *            The new x position of the camera
	 * @param y
	 *            The new y position of the camera
	 */
	public void moveCamera(float x, float y) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.translate(x, y);
	}

	/**
	 * Resets the camera at its original location, in the center of the screen
	 */
	public void resetCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
	}

	/**
	 * Displace the camera by a value
	 * 
	 * @param dx
	 *            Displacement along the x axis, can be positive or negative
	 * @param dy
	 *            Displacement along the y axis, can be positive or negative
	 */
	public void scroll(float dx, float dy) {
		camera.translate(dx, dy);
		camera.update();
	}

	/**
	 * Zooms in and out the camera, 1 is the default zoom-level
	 * 
	 * @param factor
	 *            The new zoom factor
	 */
	public void zoom(float factor) {
		camera.zoom = factor;
		camera.update();
	}

	/****************************************************
	 * Shaders stuff
	 ****************************************************/
	/**
	 * Draws the currently assigned shader
	 */
	public void drawShader() {
		drawShader(getScreenWidth() / 2, getScreenHeight() / 2, 0f);
	}

	/**
	 * Draws the currently assigned shader
	 * 
	 * @param shaderTime
	 *            The current time that is passed to the fragment shader
	 */
	public void drawShader(float shaderTime) {
		drawShader(getScreenWidth() / 2, getScreenHeight() / 2, shaderTime);
	}

	/**
	 * Request shader rendering that passes an uniform value with the current
	 * rendering location.
	 * 
	 * @param posX
	 *            The value passed to the shader, might be discarded
	 * @param posY
	 *            The value passed to the shader, might be discarded
	 * @param shaderTime
	 *            The value passed to the shader, might be discarded
	 */
	public void drawShader(int posX, int posY, float shaderTime) {
		if (getShaderRenderer() != null)
			getShaderRenderer().render(posX, posY, shaderTime);
		else {
			try {
				new Exception("Shader renderer not set, aborting.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets a shader that will be drawable
	 * 
	 * @param s
	 *            The path of the shader file
	 */
	public void setShader(String s) {
		setShader(s, getScreenWidth(), getScreenHeight());
	}

	/**
	 * Sets a shader that will be drawn
	 * 
	 * @param s
	 *            The path of the shader
	 * @param height
	 *            The height of the texture to draw the shader on
	 * @param width
	 *            The width of the texture to draw the shader on
	 */
	public void setShader(String s, int width, int height) {
		// TODO Allowing multiple shaders at once would be nice
		// Dispose of the allocated resources
		if (getShaderRenderer() != null) {
			getShaderRenderer().dispose();
		}

		setShaderRenderer(new ShaderRenderer(s, width, height));
	}

	public ShaderRenderer getShaderRenderer() {
		return shaderRenderer;
	}

	public void setShaderRenderer(ShaderRenderer shaderRenderer) {
		this.shaderRenderer = shaderRenderer;
	}

}
