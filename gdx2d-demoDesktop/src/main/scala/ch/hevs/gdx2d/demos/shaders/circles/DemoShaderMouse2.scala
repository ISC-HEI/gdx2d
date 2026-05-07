package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger

/**
 * Shows how to pass parameters to a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderMouse2 extends DesktopApplication {

  private var c: Circle = _

  override def onInit(): Unit = {
    setTitle("Mouse shader interactions, mui 2013")
    c = new Circle(getWindowWidth / 2, getWindowHeight / 2)
    Logger.log("Press mouse anywhere to move the circle to that location")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/circles/circle2.fp")
      g.getShaderRenderer.setUniform("radius", 30.0f)
    }

    g.clear()
    g.getShaderRenderer.setUniform("mouse", c.pos)
    g.drawShader()

    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    c.pos.set(x.toFloat, y.toFloat)
  }

  override def onDrag(x: Int, y: Int): Unit = {
    super.onDrag(x, y)
    c.pos.set(x.toFloat, y.toFloat)
  }
}

object DemoShaderMouse2 {
  def main(args: Array[String]): Unit = {
    new DemoShaderMouse2().launch()
  }
}
