package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * Minimal Scala hello world for gdx2d.
 *
 * Opens a 500x500 window, clears the background to white, draws
 * "Hello gdx2d" in the middle, and shows the current FPS. Used as the
 * integration smoke test across every phase of the libgdx 1.14
 * migration.
 */
class HelloGdx2d extends DesktopApplication(500, 500) {

  override def onInit(): Unit = {
    setTitle("Hello gdx2d (Scala)")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawStringCentered(getWindowHeight / 2.0f, "Hello gdx2d")
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
  }
}

object HelloGdx2d {
  def main(args: Array[String]): Unit = {
    new HelloGdx2d().launch()
  }
}
