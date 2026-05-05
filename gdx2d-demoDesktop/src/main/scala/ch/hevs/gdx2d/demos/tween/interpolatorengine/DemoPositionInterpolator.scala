package ch.hevs.gdx2d.demos.tween.interpolatorengine

import ch.hevs.gdx2d.demos.tween.Ball
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation

/**
 * Demonstrates the usage of interpolation (tweening) for animation using the
 * [[Interpolation]] class of libgdx.
 *
 * @author Christopher Metrailler (mei)
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoPositionInterpolator extends DesktopApplication {

  private val ANIMATION_LENGTH = 1.3f // Animation length (in seconds)
  private var currentTime = 0f // In seconds
  private var direction = 1 // Direction of movement
  private var balls: Array[Ball] = _
  private var mHeight: Int = 0
  private var mWidth: Int = 0
  private var mMargin: Int = 0

  override def onInit(): Unit = {
    setTitle("Position interpolators, mei/mui 2013")

    mMargin = getWindowWidth / 8
    mHeight = getWindowHeight
    mWidth = getWindowWidth

    balls = Array.tabulate(8) { i =>
      new Ball(mMargin.toFloat, mHeight * (i + 1) / 10f)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Create an infinite "Yoyo effect"
    val animationPercentage = computePercentage()

    // Apply different types of interpolation to the balls between start position and
    // end position of the X attribute of the ball
    val start = mMargin.toFloat
    val end = (mWidth - mMargin).toFloat

    balls(0).posx = Interpolation.linear.apply(start, end, animationPercentage)
    balls(1).posx = Interpolation.elastic.apply(start, end, animationPercentage)
    balls(2).posx = Interpolation.sine.apply(start, end, animationPercentage)
    balls(3).posx = Interpolation.bounce.apply(start, end, animationPercentage)
    balls(4).posx = Interpolation.circle.apply(start, end, animationPercentage)
    balls(5).posx = Interpolation.swing.apply(start, end, animationPercentage)
    balls(6).posx = Interpolation.pow2.apply(start, end, animationPercentage)
    balls(7).posx = Interpolation.exp10.apply(start, end, animationPercentage)

    // Do the drawing
    g.clear()

    // Draw the two red boundaries
    g.setColor(Color.RED)
    g.drawLine(mMargin.toFloat, mHeight * 1 / 10f, mMargin.toFloat, mHeight * 8 / 10f)
    g.drawLine((mWidth - mMargin).toFloat, mHeight * 1 / 10f, (mWidth - mMargin).toFloat, mHeight * 8 / 10f)

    // Draw the balls
    for (ball <- balls) {
      ball.draw(g, 0.5f)
    }

    g.drawFPS()
    g.drawSchoolLogoUpperRight()
  }

  private def computePercentage(): Float = {
    if (direction == 1)
      currentTime += Gdx.graphics.getDeltaTime
    else
      currentTime -= Gdx.graphics.getDeltaTime

    if (currentTime >= ANIMATION_LENGTH || currentTime <= 0)
      direction *= -1

    currentTime / ANIMATION_LENGTH
  }
}

object DemoPositionInterpolator {
  def main(args: Array[String]): Unit = {
    new DemoPositionInterpolator().launch()
  }
}
