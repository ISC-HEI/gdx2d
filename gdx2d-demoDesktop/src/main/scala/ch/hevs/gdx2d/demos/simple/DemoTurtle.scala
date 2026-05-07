package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.components.graphics.Turtle
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A very simple demonstration on how to display something animated with the
 * turtle paradigm.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoTurtle extends DesktopApplication {

  private var t: Turtle = _
  private var flakeSize: Int = 2
  private var animDirection: Int = 1

  /**
   * Draws the snowflake.
   *
   * @param length length of a side of the flake
   * @param level  complexity level of the recursion
   */
  private def drawFlake(length: Double, level: Int): Unit = {
    drawSegment(length, level)
    t.turn(120.0)
    drawSegment(length, level)
    t.turn(120.0)
    drawSegment(length, level)
    t.turn(120.0)
  }

  /**
   * Draws a side of the flake (Koch curve).
   */
  private def drawSegment(length: Double, level: Int): Unit = {
    if (level == 0) {
      t.forward(length)
      return
    }
    val currentLevel = length / 3.0
    drawSegment(currentLevel, level - 1)
    t.turn(-60.0)
    drawSegment(currentLevel, level - 1)
    t.turn(120.0)
    drawSegment(currentLevel, level - 1)
    t.turn(-60.0)
    drawSegment(currentLevel, level - 1)
  }

  override def onInit(): Unit = {
    setTitle("Moving turtle demo, mui 2016")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (t == null) t = new Turtle(g, g.getScreenWidth.toFloat, g.getScreenHeight.toFloat)

    g.clear(Color.DARK_GRAY)

    t.changeColor(Color.WHITE)
    t.penDown()
    t.setAngle(60.0)
    t.jump((g.getScreenWidth * 0.5).toFloat, (g.getScreenHeight * 0.25).toFloat)

    drawFlake((20 + flakeSize * 2).toDouble, 4)

    flakeSize += animDirection
    if (flakeSize == 100 || flakeSize == 2) animDirection *= -1

    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoTurtle {
  def main(args: Array[String]): Unit = new DemoTurtle().launch()
}
