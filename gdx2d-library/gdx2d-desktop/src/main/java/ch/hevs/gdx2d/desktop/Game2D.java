package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.GdxNativesLoader;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.Version;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;

/**
 * A wrapper for the {@link ApplicationListener} class.
 * <p/>
 * Links together a {@link PortableApplication}, which is the class instantiated by the user and {@link GdxGraphics}.
 * This link is made by proper calls at {@link GdxGraphics} for important events such as rendering (in {@link #render()}
 * method) or scaling (in {@link #resize(int, int)}).
 *
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton
 * @version 1.1
 */
public class Game2D implements ApplicationListener {

	public static GdxGraphics g;

	// Force to load native libraries (for Android Proguard)
	// FIXME Is this really required?
	static {
		GdxNativesLoader.load();
	}

	public OrthographicCamera camera;
	protected PortableApplication app;
	protected ShapeRenderer shapeRenderer;
	protected SpriteBatch batch;

	/**
	 * Default constructor
	 */
	public Game2D(PortableApplication app) {
		this.app = app;
	}

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		// Print the library version
		Logger.log(Version.printVerbose());

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		g = new GdxGraphics(shapeRenderer, batch, camera);

		// Register multiple input processors for gestures, mouse and keyboard events
		final InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(new GdxGestureDetector(app))); // Gestures
		multiplexer.addProcessor(new GdxInputProcessor(app)); // Mouse and keyboard inputs

		Gdx.input.setInputProcessor(multiplexer);

		// Register the controllers input (extension required)
		Controllers.clearListeners();
		Controllers.addListener(new GdxControllersProcessor(app));

		app.onInit(); // Initialize app

		// FIXME: should we enumerate the controllers here and call the connect method ?
		// app.onControllerConnected();
	}

	/**
	 * Mostly delegates rendering to the {@link #app} class
	 */
	@Override
	public void render() {
		g.begin();
		app.onGraphicRender(g);
		g.end();
	}

	/**
	 * Called when the screen has been resized.
	 */
	@Override
	public void resize(int width, int height) {
	}

	/**
	 * Handles application life-cycle on Android and others.
	 */
	@Override
	public void pause() {
		app.onPause();
	}

	/**
	 * Handles application life-cycle on Android and others.
	 */
	@Override
	public void resume() {
		app.onResume();
	}

	/**
	 * Called to remove all the allocated resources.
	 */
	@Override
	public void dispose() {
		PhysicsWorld.dispose();
		g.dispose();
		app.onDispose();
	}
}
