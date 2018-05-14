package ch.hevs.gdx2d.lib.interfaces;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Contains the methods to interact with controllers.
 *
 * It allows to use multiple controllers and use the callback methods given by the libgdx controllers extension.
 *
 * @see com.badlogic.gdx.controllers.ControllerListener
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
public interface ControllersInterface {

	// Wrapper based on `com/badlogic/gdx/controllers/ControllerListener.java` interface

	/**
	 * A {@link Controller} got connected.
	 *
	 * @param controller the connected controller
	 */
	void onControllerConnected(Controller controller);

	/**
	 * A {@link Controller} got disconnected.
	 *
	 * @param controller the connected controller
	 */
	void onControllerDisconnected(Controller controller);

	/**
	 * A button on the {@link Controller} was pressed.
	 *
	 * The buttonCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param buttonCode the pressed button code
	 */
	void onControllerKeyDown(Controller controller, int buttonCode);

	/**
	 * A button on the {@link Controller} was released.
	 *
	 * The buttonCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param buttonCode the released button code
	 */
	void onControllerKeyUp(Controller controller, int buttonCode);

	/**
	 * An axis on the {@link Controller} moved.
	 *
	 * The axisCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param axisCode the moved axis
	 * @param value the axis value is in the range [-1, 1]
	 */
	void onControllerAxisMoved(Controller controller, int axisCode, float value);

	/**
	 * A POV (Point of View) on the {@link Controller} moved.
	 *
	 * The povCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param povCode the POV code
	 * @param value the POV direction
	 */
	void onControllerPovMoved(Controller controller, int povCode, PovDirection value);

	/**
	 * An x-slider on the {@link Controller} moved.
	 *
	 * The sliderCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param sliderCode the slider code
	 * @param value the slider value
	 */
	void onControllerXSliderMoved(Controller controller, int sliderCode, boolean value);

	/**
	 * An y-slider on the {@link Controller} moved.
	 *
	 * The sliderCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for known
	 * controllers.
	 *
	 * @param controller the corresponding controller
	 * @param sliderCode the slider code
	 * @param value the slider value
	 */
	void onControllerYSliderMoved(Controller controller, int sliderCode, boolean value);

	/**
	 * An accelerometer value on the {@link Controller} changed.
	 *
	 * The accelerometerCode is controller specific, see the <code>com.badlogic.gdx.controllers.mapping</code> package for
	 * known controllers.
	 *
	 * @param controller the corresponding controller
	 * @param accelerometerCode the accelerometer code
	 * @param value acceleration on a 3-axis accelerometer in m/s^2
	 */
	void onControllerAccelerometerMoved(Controller controller, int accelerometerCode, Vector3 value);
}
