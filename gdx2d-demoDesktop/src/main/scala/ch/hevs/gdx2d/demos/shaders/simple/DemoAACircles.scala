package ch.hevs.gdx2d.demos.shaders.simple

import java.util.Random

import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Demonstrates the use of anti-aliased circles function.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoAACircles extends DesktopApplication {

  private val rnd = new Random()
  private val Q = 5
  private val x = new Array[Int](Q)
  private val y = new Array[Int](Q)
  private val rad = new Array[Int](Q)
  private val p = new Array[Int](Q)

  override def onInit(): Unit = {
    for (i <- 0 until Q) {
      x(i) = rnd.nextInt(500)
      y(i) = rnd.nextInt(500)
      rad(i) = rnd.nextInt(30) + 10
      p(i) = rnd.nextInt(Palette.pastel2.length)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    for (i <- 0 until Q) {
      g.drawAntiAliasedCircle(x(i).toFloat, y(i).toFloat, rad(i).toFloat, Palette.pastel2(p(i)))
    }
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoAACircles {
  def main(args: Array[String]): Unit = {
    new DemoAACircles().launch()
  }
}
