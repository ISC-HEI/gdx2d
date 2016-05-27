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
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoControllers extends PortableApplication {

  private final static String TAG = DemoControllers.class.getSimpleName();

  public static void main(String[] args) {
    new DemoControllers();
  }

  @Override
  public void onInit() {
    setTitle("Controllers demo, mei 2016");

    Logger.log(TAG, "%d controller(s) found", Controllers.getControllers().size);
    for (Controller controller : Controllers.getControllers()) {
      Logger.log(TAG, " - " + controller.getName());
    }
  }

  @Override
  public void onGraphicRender(GdxGraphics g) {
    g.clear();

    //TODO: add a basic demo

    g.drawFPS();
    g.drawSchoolLogo();
  }

  @Override
  public void onControllerConnected(Controller controller) {
  	Logger.log("A controller has been connected !");
  }

  @Override
  public void onControllerKeyDown(Controller controller, int buttonCode) {
    boolean found = Xbox.isXboxController(controller);
    if(!found) {
      Logger.log("Xbox controller not found !");
      return;
    }

    Logger.log("Xbox controller found !");
    Logger.log("Button " + buttonCode + " pressed");

    if(buttonCode == Xbox.X)
      Logger.log("Button X pressed");
  }

  @Override
  public void onControllerPovMoved(Controller controller, int povCode, PovDirection value) {
    Logger.log("POV: " + value);
  }
}
