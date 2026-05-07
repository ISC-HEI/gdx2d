package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Draws a simple, yet ugly, circle. This uses a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderMouse1 extends DesktopApplication {

  private var c: Circle = _

  override def onInit(): Unit = {
    setTitle("Simple circle shader, mui 2013")
    c = new Circle(getWindowWidth / 2, getWindowHeight / 2)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/circles/circle1.fp")
      g.getShaderRenderer.setUniform("center", c.pos)
    }

    g.clear()
    g.drawShader()
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoShaderMouse1 {
  def main(args: Array[String]): Unit = {
    new DemoShaderMouse1().launch()
  }
}
