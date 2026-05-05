package ch.hevs.gdx2d.demos.complex_shapes

import scala.collection.mutable.ArrayBuffer

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A first try at reproducing http://lab.hakim.se/hypnos/ (not done yet).
 *
 * @author Pierre-André Mudry, mui
 * @version 2.0
 */
class Coord(var x: Float, var y: Float, var r: Float)

class DemoHypnos extends DesktopApplication {

  private val points = new ArrayBuffer[Coord]
  private val quality = 100f
  private var layerSize: Float = 0f
  private var radius: Float = 0f
  private var screenWidth: Int = 0
  private var screenHeight: Int = 0
  private var angle: Float = 0f

  private def generateObjects(): Unit = {
    var i = 0
    while (i < quality) {
      val x = screenWidth / 2 + Math.sin(i / quality * 2.0 * Math.PI).toFloat * radius - layerSize
      val y = screenHeight / 2 + Math.cos(i / quality * 2.0 * Math.PI).toFloat * radius - layerSize
      val r = (i / quality * Math.PI).toFloat
      points += new Coord(x, y, r)
      i += 1
    }
  }

  override def onInit(): Unit = {
    setTitle("Demo shapes, mui 2013")
    screenWidth = getWindowWidth
    screenHeight = getWindowHeight
    radius = Math.min(screenWidth, screenHeight) * 0.2f
    layerSize = radius * 0.25f
    generateObjects()
  }

  private def update(): Unit = {
    for (c <- points) c.r += 0.3f
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    angle = if (angle >= 360) 0f else angle + 0.2f
    g.clear(Color.WHITE)
    update()

    for (c <- points) {
      g.setColor(Color.WHITE)
      g.drawFilledRectangle(c.x, c.y, 50f, 50f, c.r)
      g.setColor(Color.BLACK)
      g.drawRectangle(c.x, c.y, 50f, 50f, c.r)
    }

    val beg = points(0)
    g.setColor(Color.WHITE)
    g.drawFilledRectangle(beg.x, beg.y, 50f, 50f, beg.r)
    g.setColor(Color.BLACK)
    g.drawRectangle(beg.x, beg.y, 50f, 50f, beg.r)

    var i = (quality - 30).toInt
    while (i < quality) {
      val c = points(i)
      g.setColor(Color.WHITE)
      g.drawFilledRectangle(c.x, c.y, 50f, 50f, c.r)
      g.setColor(Color.BLACK)
      g.drawRectangle(c.x, c.y, 50f, 50f, c.r)
      i += 1
    }

    g.drawSchoolLogo()
    g.drawFPS()
  }
}

object DemoHypnos {
  def main(args: Array[String]): Unit = new DemoHypnos().launch()
}
