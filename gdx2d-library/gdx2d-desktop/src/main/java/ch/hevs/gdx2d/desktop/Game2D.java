package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxNativesLoader;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.Version;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;

import javax.swing.JFrame;

/**
 * A wrapper for the {@link ApplicationListener} class.
 * <p>
 * Links together a {@link DesktopApplication}, which is the class instantiated by the user and {@link GdxGraphics}.
 * This link is made by proper calls at {@link GdxGraphics} for important events such as rendering (in {@link #render()}
 * method) or scaling (in {@link #resize(int, int)}).
 *
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton
 * @version 2.0
 */
public class Game2D implements ApplicationListener {

	public static GdxGraphics g;

	static {
		GdxNativesLoader.load();
	}

	public OrthographicCamera camera;
	protected DesktopApplication app;
	protected ShapeRenderer shapeRenderer;
	protected SpriteBatch batch;

	/**
	 * Default constructor
	 */
	public Game2D(DesktopApplication app) {
		this.app = app;
	}

	public void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		// Print the library version
		if (Gdx.app.getLogLevel() >= Logger.INFO)
			Logger.log(Version.printVerbose());

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		g = new GdxGraphics(shapeRenderer, batch, camera);

		// Register the input processor for mouse and keyboard events
		final InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GdxInputProcessor(app)); // Mouse and keyboard inputs

		Gdx.input.setInputProcessor(multiplexer);

		app.onInit(); // Initialize app
	}

	/**
	 * Mostly delegates rendering to the {@link #app} class
	 */
	public void render() {
		g.begin();
		app.onGraphicRender(g);
		g.end();
	}

	/**
	 * Called when the screen has been resized.
	 */
	public void resize(int width, int height) {
	}

	/**
	 * Handles application life-cycle on Android and others.
	 */
	public void pause() {
		app.onPause();
	}

	/**
	 * Handles application life-cycle on Android and others.
	 */
	public void resume() {
		app.onResume();
	}

	/**
	 * Called to remove all the allocated resources.
	 */
	public void dispose() {
		PhysicsWorld.dispose();
		g.dispose();
		app.onDispose();
	}
}
