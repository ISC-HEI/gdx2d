package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Input gesture processor for gdx2d.
 *
 * @see GestureDetector.GestureListener
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
class GdxGestureDetector extends GestureDetector.GestureAdapter {

	private final PortableApplication app;

	/**
	 * Input gesture processor for gdx2d.
	 *
	 * This processor must be registered manually to the {@link InputMultiplexer} class.
	 *
	 * @param app the portable application (cannot be null)
	 * @throws IllegalArgumentException if the application is {@code null}
	 */
	GdxGestureDetector(final PortableApplication app) {
		if (app == null)
			throw new IllegalArgumentException("PortableApplication cannot be null !");

		this.app = app;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		app.onZoom(initialDistance, distance);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		app.onTap(x, y, count, button);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
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
}
