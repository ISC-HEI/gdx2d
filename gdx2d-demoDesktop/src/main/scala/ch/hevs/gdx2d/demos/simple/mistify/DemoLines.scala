package ch.hevs.gdx2d.demos.simple.mistify

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * A classical Mistify screen saver clone.
 *
 * Code adapted from http://r3dux.org/2010/11/mystify-2-0/
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class DemoLines extends DesktopApplication {

  private val N_SHAPES = 3
  private var s: Array[BounceShape] = _
  private var frame: Int = 0

  override def onInit(): Unit = {
    setTitle("Mistify screensaver clone, mui 2013")
    s = Array.fill(N_SHAPES)(new BounceShape(getWindowWidth, getWindowHeight))
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    for (shape <- s) {
      shape.drawShape(g)
      shape.moveShape(g.getScreenWidth, g.getScreenHeight)
      shape.shiftShapeColour(frame)
      frame += 1
    }

    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoLines {
  def main(args: Array[String]): Unit = new DemoLines().launch()
}
