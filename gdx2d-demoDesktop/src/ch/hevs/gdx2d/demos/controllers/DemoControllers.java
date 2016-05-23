package ch.hevs.gdx2d.demos.controllers;

import ch.hevs.gdx2d.demos.simple.mistify.BounceShape;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.Xbox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sun.org.apache.xpath.internal.objects.XBoolean;

/**
 * Demo program for the {@code controllers} extension.
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoControllers extends PortableApplication {

  private final static String TAG = DemoControllers.class.getSimpleName();

  private boolean controllersFound;
  private int frameCnt = 0;
  private BounceShape shape;
  private Vector2 lastTextPos;

  public static void main(String[] args) {
    new DemoControllers();
  }

  @Override
  public void onInit() {
    setTitle("Controllers demo, mei 2016");

    shape = new BounceShape(getWindowWidth(), getWindowHeight());

    controllersFound = Controllers.getControllers().size != 0;
    Logger.log(TAG, "%d controller(s) found", Controllers.getControllers().size);
    for (Controller controller : Controllers.getControllers()) {
      Logger.log(TAG, " - " + controller.getName());
    }
  }

  @Override
  public void onGraphicRender(GdxGraphics g) {
    g.clear();



    g.drawFPS();
    g.drawSchoolLogo();
  }

  @Override
  public void onControllerConnected(Controller controller) {
    System.out.println("Miaou");
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
