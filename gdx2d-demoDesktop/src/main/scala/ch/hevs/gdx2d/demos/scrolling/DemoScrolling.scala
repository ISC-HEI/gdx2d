package ch.hevs.gdx2d.demos.scrolling

import scala.collection.mutable.ArrayBuffer

import ch.hevs.gdx2d.demos.scrolling.objects._
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}

/**
 * Demonstrates how to scroll and zoom on a scene. Also demonstrates how to
 * delegate rendering to other objects through the [[DrawableObject]] interface.
 *
 * For some reason, running in windowed mode displays stuttering problems. Image
 * stuttering can be removed by playing the demo full screen.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoScrolling extends DesktopApplication(0, 0, true) {

  private val toDraw = new ArrayBuffer[DrawableObject]

  private var zoomFactor: Double = 1.0
  private var travelSpeed: Float = 2f
  private var scrolling: Boolean = true

  override def onInit(): Unit = {
    setTitle("Scrolling demo, mui 2013")
    Logger.log("Press s or w for zooming in or out")

    toDraw += new Sky()

    // Some pipes for a nice 'Mario' like atmosphere
    toDraw += new Pipe(100, 60)
    toDraw += new Pipe(600, 80)
    toDraw += new Pipe(1500, 90)

    // First (bottom) layer
    for (i <- 0 to 59) toDraw += new Brick(-500 + 64 * i, 20)

    // Coins
    for (i <- 0 to 4) toDraw += new Coin(250 + 64 * i, 120)

    // Some clouds
    toDraw += new Cloud(100, 450)
    toDraw += new Cloud(250, 600)
    toDraw += new Cloud(450, 250)
    toDraw += new Cloud(700, 350)
    toDraw += new Cloud(1000, 370)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    scrolling = !scrolling
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.zoom(zoomFactor.toFloat)

    // Handle input directly (not via onKeyDown) because we want continuous zoom
    if (Gdx.input.isKeyPressed(Input.Keys.W)) zoomFactor += 0.02
    if (Gdx.input.isKeyPressed(Input.Keys.S)) zoomFactor -= 0.02

    // We scroll only if the camera is not too big
    if (scrolling && g.getCamera.viewportWidth < 600) {
      if (g.getCamera.position.x > 600 || g.getCamera.position.x < 200) travelSpeed *= -1f
      g.scroll(travelSpeed, 0f)
    }

    for (obj <- toDraw) obj.draw(g)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object DemoScrolling {
  def main(args: Array[String]): Unit = new DemoScrolling().launch()
}
