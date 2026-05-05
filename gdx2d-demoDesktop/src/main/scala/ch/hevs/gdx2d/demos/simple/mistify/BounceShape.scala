package ch.hevs.gdx2d.demos.simple.mistify

import java.util.Random

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A classical Mistify screen saver clone.
 *
 * Code adapted from http://r3dux.org/2010/11/mystify-2-0/
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class BounceShape(width: Int, height: Int) {
  import BounceShape._

  private val colourChangeSpeed = 0.001
  private val xSpeedMultiplier = 6.0
  private val ySpeedMultiplier = 6.0
  private val xSpeedAdder = 1.0
  private val ySpeedAdder = 1.0

  private val x = new Array[Double](4)
  private val y = new Array[Double](4)
  private val xSpeed = new Array[Double](4)
  private val ySpeed = new Array[Double](4)

  private val c: Color = pickColour()
  private var targetC: Color = pickColour()

  // Initialise the x/y and xSpeeds/ySpeeds
  for (loop <- 0 until 4) {
    x(loop) = r.nextInt(width).toDouble
    y(loop) = r.nextInt(height).toDouble
    xSpeed(loop) = r.nextDouble() * xSpeedMultiplier
    ySpeed(loop) = r.nextDouble() * ySpeedMultiplier
  }

  private[mistify] def drawShape(g: GdxGraphics): Unit = {
    g.setColor(c)
    for (i <- 0 until 4) {
      g.drawLine(
        x(i).toFloat, y(i).toFloat,
        x((i + 1) % 4).toFloat, y((i + 1) % 4).toFloat)
    }
  }

  private[mistify] def moveShape(theScreenWidth: Int, theScreenHeight: Int): Unit = {
    for (loop <- 0 until 4) {
      x(loop) += xSpeed(loop)
      y(loop) += ySpeed(loop)

      if (x(loop) > theScreenWidth) {
        x(loop) = theScreenWidth.toDouble
        xSpeed(loop) = -xSpeedAdder + r.nextFloat() * (xSpeedMultiplier * -1)
      }
      if (x(loop) < 0.0) {
        x(loop) = 0.0
        xSpeed(loop) = xSpeedAdder + r.nextFloat() * xSpeedMultiplier
      }
      if (y(loop) > theScreenHeight) {
        y(loop) = theScreenHeight.toDouble
        ySpeed(loop) = -ySpeedAdder + r.nextFloat() * (ySpeedMultiplier * -1)
      }
      if (y(loop) < 0.0) {
        y(loop) = 0.0
        ySpeed(loop) = ySpeedAdder + r.nextFloat() * ySpeedMultiplier
      }
    }
  }

  private def pickColour(): Color =
    new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f)

  def shiftShapeColour(frameCount: Int): Unit = {
    // Change the target colour every 500 frames
    if (frameCount % 500 == 0) targetC = pickColour()

    if (c.r < targetC.r) c.r += colourChangeSpeed.toFloat
    if (c.r > targetC.r) c.r -= colourChangeSpeed.toFloat

    if (c.g < targetC.g) c.g += colourChangeSpeed.toFloat
    if (c.g > targetC.g) c.g -= colourChangeSpeed.toFloat

    if (c.b < targetC.b) c.b += colourChangeSpeed.toFloat
    if (c.b > targetC.b) c.b -= colourChangeSpeed.toFloat
  }
}

object BounceShape {
  private val r = new Random()
}
