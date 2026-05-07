package ch.hevs.gdx2d.desktop;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector3;

/**
 * Controllers input processor for gdx2d.
 * <p/>
 * Require the libgdx {@code controllers} extension.
 *
 * @see ControllerListener
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
class GdxControllersProcessor extends ControllerAdapter {

	private final PortableApplication app;

	/**
	 * Controllers input processor for gdx2d.
	 *
	 * This processor must be registered manually to the {@link InputMultiplexer} class.
	 *
	 * @param app the portable application (cannot be null)
	 * @throws IllegalArgumentException if the application is {@code null}
	 */
	GdxControllersProcessor(final PortableApplication app) {
		if (app == null)
			throw new IllegalArgumentException("PortableApplication cannot be null !");

		this.app = app;
	}

	@Override
	public void connected(Controller controller) {
		app.onControllerConnected(controller);
	}

	@Override
	public void disconnected(Controller controller) {
		app.onControllerDisconnected(controller);
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		app.onControllerKeyDown(controller, buttonCode);
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		app.onControllerKeyUp(controller, buttonCode);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		app.onControllerAxisMoved(controller, axisCode, value);
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		app.onControllerPovMoved(controller, povCode, value);
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		app.onControllerXSliderMoved(controller, sliderCode, value);
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		app.onControllerYSliderMoved(controller, sliderCode, value);
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		app.onControllerAccelerometerMoved(controller, accelerometerCode, value);
		return false;
	}
}
