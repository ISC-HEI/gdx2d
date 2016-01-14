package ch.hevs.gdx2d.components.screen_management;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.GestureInterface;
import ch.hevs.gdx2d.lib.interfaces.KeyboardInterface;
import ch.hevs.gdx2d.lib.interfaces.TouchInterface;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Basic interface for all rendering screens
 * @author Pierre-André Mudry
 */
public abstract class RenderingScreen implements KeyboardInterface, TouchInterface, GestureInterface, Disposable {
	// Default physics world, shoudl not be present but prevents the app to crash
	// FIXME this is because we keep an instance of world in memory and the library is still
	// loaded (IMO)
	private World w = new World(new Vector2(0, -10), true);

	// Delegate to implementing class
	abstract public void onInit();
	abstract protected void onGraphicRender(GdxGraphics g);

	@Override
	public void onClick(int x, int y, int button) {
	}

	@Override
	public void onDrag(int x, int y) {
	}

	@Override
	public void onFling(float velocityX, float velocityY, int button) {
	}

	@Override
	public void onLongPress(float x, float y) {
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	}

	@Override
	public void onRelease(int x, int y, int button) {
	}

	@Override
	public void onScroll(int amount) {
	}

	@Override
	public void onTap(float x, float y, int count, int button) {
	}

	@Override
	public void onZoom(float initialDistance, float distance) {
	}

	@Override
	public void onKeyDown(int keycode) {
	}

	@Override
	public void onKeyUp(int keycode) {
	}

	@Override
	public void dispose() {
	}

	final public void render(GdxGraphics g) {
		g.begin();
			onGraphicRender(g);
		g.end();
	}
}