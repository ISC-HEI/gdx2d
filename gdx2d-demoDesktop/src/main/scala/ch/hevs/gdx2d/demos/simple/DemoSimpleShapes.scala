package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.components.graphics.Polygon
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A very simple demonstration on how to display something with the library.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSimpleShapes extends DesktopApplication {

  override def onInit(): Unit = {
    setTitle("Simple shapes, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    // Draws a yellow circle
    g.setColor(Color.YELLOW)
    g.drawCircle(250f, 400f, 20f)

    // Draws a green rectangle
    g.setColor(Color.GREEN)
    g.drawRectangle(20f, 250f, 40f, 40f, 0f)

    g.drawFilledCircle(50f, 50f, 20f, Color.PINK)
    g.drawFilledRectangle(80f, 30f, 20f, 20f, 0f, new Color(0.5f, 0.5f, 0.5f, 0.5f))

    // Draws a blue polygon
    val points = Array(new Vector2(200f, 200f), new Vector2(250f, 250f), new Vector2(300f, 200f))
    val p = new Polygon(points)
    g.drawFilledPolygon(p, Color.BLUE)
  }
}

object DemoSimpleShapes {
  def main(args: Array[String]): Unit = {
    new DemoSimpleShapes().launch()
  }
}
