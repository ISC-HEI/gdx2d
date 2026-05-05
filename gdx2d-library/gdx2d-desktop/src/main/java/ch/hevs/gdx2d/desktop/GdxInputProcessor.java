package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;


/**
 * Input mouse and keyboard processor for gdx2d.
 *
 * @see com.badlogic.gdx.InputProcessor
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
class GdxInputProcessor extends InputAdapter {

	private final DesktopApplication app;

	/**
	 * Input mouse and keyboard processor for gdx2d.
	 *
	 * This processor must be registered manually.
	 *
	 * @param app the desktop application (cannot be null)
	 * @throws IllegalArgumentException if the application is {@code null}
	 */
	GdxInputProcessor(final DesktopApplication app) {
		if (app == null)
			throw new IllegalArgumentException("DesktopApplication cannot be null !");

		this.app = app;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		app.onRelease(screenX, Gdx.graphics.getHeight() - screenY, button);
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
	public boolean keyUp(int keycode) {
		app.onKeyUp(keycode);
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		app.onKeyDown(keycode);
		return false;
	}
}
