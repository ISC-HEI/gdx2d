package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A very simple demonstration on how to display something animated with the
 * library.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSimpleAnimation extends DesktopApplication {

  private var radius: Float = 5f
  private var speed: Float = 1f

  override def onInit(): Unit = {
    setTitle("Simple demo, mui 2026")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawAntiAliasedCircle(g.getScreenWidth / 2f, g.getScreenHeight / 2f, radius, Color.BLUE)

    // If reaching max or min size, invert the growing direction
    if (radius >= 100 || radius <= 3) {
      speed *= -1
    }

    radius += speed

    g.drawSchoolLogo()
    g.drawFPS()
  }
}

object DemoSimpleAnimation {
  def main(args: Array[String]): Unit = new DemoSimpleAnimation().launch()
}
