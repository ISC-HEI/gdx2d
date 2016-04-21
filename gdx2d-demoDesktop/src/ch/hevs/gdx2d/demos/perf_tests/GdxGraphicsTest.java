package ch.hevs.gdx2d.demos.perf_tests;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.graphics.Color;

/**
 * Torture test for graphics primitives. *
 * Test all graphic primitives in different order, colors, zoom...
 *
 * @author Marc Pignat (pim)
 */
public class GdxGraphicsTest extends PortableApplication {

  public static void main(String args[]) {
    new GdxGraphicsTest();
  }

  float zoom = 1.0f;
  boolean zoom_up = true;

  @Override
  public void onInit() {
    setTitle(this.getClass().getSimpleName());
  }

  void manage_zoom()
  {
    if (zoom_up)
    {
      zoom *= 1.01;
      if (zoom > 1.2)
      {
        zoom_up = false;
      }
    }
    else
    {
      zoom *= 0.99;
      if (zoom < 0.8)
      {
        zoom_up = true;
      }
    }
  }

  @Override
  public void onGraphicRender(GdxGraphics g) {

    manage_zoom();

    g.clear();
    g.zoom(zoom);

    for (int i = 0 ; i < 20 ; i++)
    {
      g.setPixel(40+i, 0+i, Color.BLUE);
    }
    g.drawCircle(10, 10, 10);
    g.drawFilledCircle(30, 10, 10, Color.RED);

    for (int i = 0 ; i < 20 ; i++)
    {
      g.setPixel(60-i, 0+i, Color.BLUE);
    }

    g.drawRectangle(70, 10, 10, 10, 30);
    g.drawFilledRectangle(90, 10, 10, 10, 60, Color.CYAN);
    g.drawSchoolLogoUpperRight();
    g.drawFPS();
  }

}
