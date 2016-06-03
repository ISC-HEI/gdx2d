package ch.hevs.gdx2d.demos.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.Xbox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;

/**
 * Demo program for the {@code controllers} extension.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoControllers extends PortableApplication {

	private static final String TAG = DemoControllers.class.getSimpleName();

	public static void main(String[] args) {
		new DemoControllers();
	}

	@Override
	public void onInit() {
		setTitle("Controllers demo, mui / mei 2016");

		Logger.log(TAG, "%d controller(s) found", Controllers.getControllers().size);
		for (Controller controller : Controllers.getControllers()) {
			Logger.log(TAG, " - " + controller.getName());
		}

		leftDisplayPosX = this.getWindowWidth() / 2 - 100;
		leftDisplayPosY = this.getWindowHeight() / 2;

		rightDisplayPosX = this.getWindowWidth() / 2 + 100;
		rightDisplayPosY = this.getWindowHeight() / 2;

	}

	float xLeft, yLeft;
	float xRight, yRight;

	int leftDisplayPosX, leftDisplayPosY;
	int rightDisplayPosX, rightDisplayPosY;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Left controller display
		float end_x = leftDisplayPosX + xLeft * 80;
		float end_y = leftDisplayPosY - yLeft * 80;
		g.drawLine(leftDisplayPosX, leftDisplayPosY, end_x, end_y);

		// Right controller display
		end_x = rightDisplayPosX + xRight * 80;
		end_y = rightDisplayPosY - yRight * 80;
		g.drawLine(rightDisplayPosX, rightDisplayPosY, end_x, end_y);

		g.drawFPS();
		g.drawSchoolLogo();
	}

	@Override
	public void onControllerConnected(Controller controller) {
		Logger.log("A controller has been connected !");
	}

	@Override
	public void onControllerKeyDown(Controller controller, int buttonCode) {
		if (buttonCode == Xbox.X)
			Logger.log("Button X pressed");
		if (buttonCode == Xbox.Y)
			Logger.log("Button Y pressed");
		if (buttonCode == Xbox.A)
			Logger.log("Button A pressed");
		if (buttonCode == Xbox.B)
			Logger.log("Button B pressed");
		if (buttonCode == Xbox.L_TRIGGER)
			Logger.log("Button L trigger pressed");
		if (buttonCode == Xbox.R_TRIGGER)
			Logger.log("Button R trigger pressed");

	}

	@Override
	public void onControllerAxisMoved(Controller controller, int axisCode, float value) {
		super.onControllerAxisMoved(controller, axisCode, value);

		if (axisCode == Xbox.L_STICK_HORIZONTAL_AXIS) {
			xLeft = value;
		}

		if (axisCode == Xbox.L_STICK_VERTICAL_AXIS) {
			yLeft = value;
		}

		if (axisCode == Xbox.R_STICK_HORIZONTAL_AXIS) {
			xRight = value;
		}

		if (axisCode == Xbox.R_STICK_VERTICAL_AXIS) {
			yRight = value;
		}
	}

	@Override
	public void onControllerPovMoved(Controller controller, int povCode, PovDirection value) {
		Logger.log("POV: " + value);
	}
}
