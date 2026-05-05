package ch.hevs.gdx2d.demos.perf_tests

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * Torture test for graphics primitives. Tests all graphic primitives in
 * different orders, colors and zoom levels.
 *
 * @author Marc Pignat (pim)
 */
class GdxGraphicsTest extends DesktopApplication {

  private var zoomLevel: Float = 1.0f
  private var zoomUp: Boolean = true

  override def onInit(): Unit = {
    setTitle(getClass.getSimpleName)
  }

  private def manageZoom(): Unit = {
    if (zoomUp) {
      zoomLevel *= 1.01f
      if (zoomLevel > 1.2f) zoomUp = false
    } else {
      zoomLevel *= 0.99f
      if (zoomLevel < 0.8f) zoomUp = true
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    manageZoom()
    g.clear()
    g.zoom(zoomLevel)

    for (i <- 0 to 19) g.setPixel((40 + i).toFloat, i.toFloat, Color.BLUE)
    g.drawCircle(10f, 10f, 10f)
    g.drawFilledCircle(30f, 10f, 10f, Color.RED)

    for (i <- 0 to 19) g.setPixel((60 - i).toFloat, i.toFloat, Color.BLUE)

    g.drawRectangle(70f, 10f, 10f, 10f, 30f)
    g.drawFilledRectangle(90f, 10f, 10f, 10f, 60f, Color.CYAN)
    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object GdxGraphicsTest {
  def main(args: Array[String]): Unit = new GdxGraphicsTest().launch()
}
