package ch.hevs.gdx2d.desktop;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.Version;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxNativesLoader;

/**
 * A wrapper for the {@link ApplicationListener} class.
 * <p/>
 * Links together a {@link PortableApplication}, which is the class instantiated
 * by the user and {@link GdxGraphics}. This link is made by proper calls at {@link GdxGraphics}
 * for important events such as rendering (in {@link #render()} method) or scaling (in {@link #resize(int, int)}).
 *
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton
 * @version 1.1
 */
public class Game2D implements ApplicationListener {

    static public GdxGraphics g;

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
                app.onPinch(initialPointer1, initialPointer2, pointer1, pointer2);
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

            @Override
            public boolean panStop(float v, float v1, int i, int i1) {
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
        g.begin();
        app.onGraphicRender(g);
        g.end();
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
        PhysicsWorld.dispose();
        g.dispose();
        app.onDispose();
    }
}
