package hevs.gdx2d.lib;

import hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

/**
 * A wrapper for the {@link ApplicationListener} class provided for the INF1
 * class. Used mainly for creating a {@link LwjglApplication} in {@link PortableApplication}.
 * 
 * Links together a {@link PortableApplication}, which is the class instantiated
 * by the user and {@link GdxGraphics}. This link is made by proper calls at {@link GdxGraphics}
 * for important events such as rendering (in {@link #render()} method) or scaling (in {@link #resize(int, int)}). 
 * 
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton
 * @date 2013
 * @version 1.1
 */
public class Game2D implements ApplicationListener {
	protected PortableApplication app;
	static public GdxGraphics g;

	public OrthographicCamera camera;
	protected ShapeRenderer shapeRenderer;
	protected int angle;
	protected SpriteBatch batch;

	/**
	 * Default constructor
	 * @param app
	 */
	public Game2D(PortableApplication app) {
		this.app = app;
	}

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		// Print the library version
		Logger.log(Version.print());

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		g = new GdxGraphics(shapeRenderer, batch, camera);

		// TODO Should the following comment be removed ?
		// batch.setProjectionMatrix(camera.combined);
		// shapeRenderer.setProjectionMatrix(camera.combined);

		// Let's have multiple input processors
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(new GestureListener() {

			@Override
			public boolean zoom(float initialDistance, float distance) {
				app.onZoom(initialDistance, distance);
				return false;
			}

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				app.onTap(x, y, count, button);
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1,
					Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				app.onPinch(initialPointer1, initialPointer2, pointer1,
						pointer2);
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				app.onPan(x, y, deltaX, deltaY);
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				app.onLongPress(x, y);
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				app.onFling(velocityX, velocityY, button);
				return false;
			}
		}));

		multiplexer.addProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				app.onRelease(screenX, screenY, button);
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				app.onDrag(screenX, Gdx.graphics.getHeight() - screenY);
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				app.onClick(screenX, Gdx.graphics.getHeight() - screenY, button);
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				app.onScroll(amount);
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				app.onKeyUp(keycode);
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// Trigger about box when pressing the menu button on Android
				if (keycode == Input.Keys.MENU) {
					// resolver.showAboutBox();
				}
				if (keycode == Input.Keys.ESCAPE) {
					Gdx.app.exit();
				}
				app.onKeyDown(keycode);
				return false;
			}
		});

		Gdx.input.setInputProcessor(multiplexer);

		// Initialize app
		app.onInit();
	}

	/**
	 * Mostly delegates rendering to the {@link #app} class
	 */
	@Override
	public void render() {
		app.onGraphicRender(g);
	}

	/**
	 * Called when the screen has been resized
	 */
	@Override
	public void resize(int width, int height) {
	}

	/**
	 * Handles application life-cycle on Android and others
	 */
	@Override
	public void pause() {
		app.onPause();
	}

	/**
	 * Handles application life-cycle on Android and others
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
		g.dispose();
		app.onDispose();
		PhysicsWorld.dispose();
	}
}
