package ch.hevs.gdx2d.demos.controllers;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.Xbox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo program for the {@code controllers} extension.
 *
 * If you use the XBox One controller, see
 * <a href="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/360_controller.svg/450px-360_controller.svg.png">
 * this image</a> which describes each button and axes.
 *
 * The XBox One controller image comes <a href="from https://thenounproject.com/term/xbox-one/45114/">here</a> (CC BY
 * 3.0 US).
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoControllers extends PortableApplication {

	private static final String TAG = DemoControllers.class.getSimpleName();

	private BitmapImage background;
	private final Vector2 leftSickVal = Vector2.Zero.cpy(); // X,Y values of the left stick (POV)
	private final Vector2 rightSickVal = Vector2.Zero.cpy(); // X,Y values of the left stick (POV)

	private Vector2 leftStickPos; // Center coordinates of the left stick
	private Vector2 rightStickPos; // Center coordinates of the right stick

	private Vector2 endPos = new Vector2();
	private Vector2 center;

	// Contain some button positions used to draw a circle when they are pressed
	private static Map<Integer, Vector2> buttonPos = new HashMap<Integer, Vector2>();
	static {
		buttonPos.put(Xbox.X, new Vector2(85, 97));
		buttonPos.put(Xbox.Y, new Vector2(113, 123));
		buttonPos.put(Xbox.A, new Vector2(113, 71));
		buttonPos.put(Xbox.B, new Vector2(141, 97));
	}

	private Controller ctrl; // The connected controller

	private DemoControllers() {
		super(700, 700, false);
	}

	@Override
	public void onInit() {
		setTitle("Controllers demo, mui, mei 2016");

		Logger.log(TAG, "%d controller(s) found", Controllers.getControllers().size);
		for (Controller controller : Controllers.getControllers()) {
			Logger.log(TAG, " - " + controller.getName());
		}

		if (Controllers.getControllers().size > 0)
			ctrl = Controllers.getControllers().first();

		background = new BitmapImage("data/images/noun_45114_cc.png");

		center = new Vector2(getWindowWidth(), getWindowHeight()).scl(0.5f);
		leftStickPos = new Vector2(-105, 97).add(center);
		rightStickPos = new Vector2(58, 33).add(center);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.WHITE);

		if (ctrl == null)
			return;

		g.drawBackground(background, 0, 0);

		g.setColor(Color.RED);
		g.setPenWidth(3);

		// Left stick display
		endPos.x = leftSickVal.x * 34;
		endPos.y = leftSickVal.y * 34;
		endPos = endPos.limit(34); // Make a nice round
		g.drawLine(leftStickPos.x, leftStickPos.y, leftStickPos.x + endPos.x, leftStickPos.y - endPos.y);

		// Right stick display
		endPos.x = rightSickVal.x * 34;
		endPos.y = rightSickVal.y * 34;
		endPos = endPos.limit(34); // Make a nice round
		g.drawLine(rightStickPos.x, rightStickPos.y, rightStickPos.x + endPos.x, rightStickPos.y - endPos.y);

		// Buttons clicked
		for (Integer buttonCode : buttonPos.keySet()) {
			if (ctrl.getButton(buttonCode)) {
				Vector2 pos = buttonPos.get(buttonCode).cpy().add(center);
				g.drawFilledCircle(pos.x, pos.y, 10, Color.RED);
			}
		}

		g.drawFPS(Color.BLACK);
		g.drawSchoolLogo();
	}

	@Override
	public void onControllerConnected(Controller controller) {
		Logger.log(TAG, "A controller has been connected !");
		ctrl = controller;
	}

	@Override
	public void onControllerDisconnected(Controller controller) {
		ctrl = null;
	}

	@Override
	public void onControllerKeyDown(Controller controller, int buttonCode) {
		if (buttonCode == Xbox.X)
			Logger.log(TAG, "Button X pressed");
		if (buttonCode == Xbox.Y)
			Logger.log(TAG, "Button Y pressed");
		if (buttonCode == Xbox.A)
			Logger.log(TAG, "Button A pressed");
		if (buttonCode == Xbox.B)
			Logger.log(TAG, "Button B pressed");
		if (buttonCode == Xbox.L_TRIGGER)
			Logger.log(TAG, "Button L trigger pressed");
		if (buttonCode == Xbox.R_TRIGGER)
			Logger.log(TAG, "Button R trigger pressed");
	}

	@Override
	public void onControllerAxisMoved(Controller controller, int axisCode, float value) {
		super.onControllerAxisMoved(controller, axisCode, value);

		if (axisCode == Xbox.L_STICK_HORIZONTAL_AXIS)
			leftSickVal.x = value;

		if (axisCode == Xbox.L_STICK_VERTICAL_AXIS)
			leftSickVal.y = value;

		if (axisCode == Xbox.R_STICK_HORIZONTAL_AXIS)
			rightSickVal.x = value;

		if (axisCode == Xbox.R_STICK_VERTICAL_AXIS)
			rightSickVal.y = value;
	}

	@Override
	public void onControllerPovMoved(Controller controller, int povCode, PovDirection value) {
		Logger.log(TAG, "POV: " + value);
	}

	public static void main(String[] args) {
		new DemoControllers();
	}
}
