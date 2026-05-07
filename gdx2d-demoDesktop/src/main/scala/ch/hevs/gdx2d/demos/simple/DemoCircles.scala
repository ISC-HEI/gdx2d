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
class DemoCircles extends DesktopApplication {

  private val radius = new Array[Int](8)
  private val speed = new Array[Int](8)

  override def onInit(): Unit = {
    setTitle("Moving circles demo, mui 2013")

    for (i <- 0 until 8) {
      radius(7 - i) = (i + 1) * (40 / 8)
      speed(i) = 1
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.DARK_GRAY)

    // Draws the circles
    for (i <- 0 until 8) {
      if (radius(i) >= 40 || radius(i) <= 3) {
        speed(i) *= -1
      }

      radius(i) += speed(i)

      g.drawFilledCircle(
        (getWindowWidth / 10 + i * (getWindowWidth - getWindowWidth / 10) / 8).toFloat,
        (getWindowHeight / 2).toFloat,
        radius(i).toFloat,
        new Color(radius(i) / 40.0f, 0f, 0f, 1f))
    }

    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoCircles {
  def main(args: Array[String]): Unit = {
    new DemoCircles().launch()
  }
}
