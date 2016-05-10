package ch.hevs.gdx2d.demos.controllers;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

/**
 * Demo program for the {@code controllers} extension.
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class DemoControllers extends PortableApplication {

  private final static String TAG = DemoControllers.class.getSimpleName();

  @Override
  public void onInit() {
    setTitle("Controllers demo, mei 2016");

    int nbrCtrl = Controllers.getControllers().size;
    if (nbrCtrl == 0) {
      Logger.error(TAG, "no controller found !");
      return;
    }

    Logger.log(TAG, "%d controller(s) found:", nbrCtrl);
    for (Controller controller : Controllers.getControllers()) {
      Logger.log(" - " + controller.getName());
    }
  }

  @Override
  public void onGraphicRender(GdxGraphics g) {
    g.clear();
    g.drawFPS();
    g.drawSchoolLogo();
  }

  public static void main(String[] args) {
    new DemoControllers();
  }
}
