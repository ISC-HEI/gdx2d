package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderGradient extends DesktopApplication {

  override def onInit(): Unit = {
    setTitle("Gradient shader, no animation, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/gradient.fp")
    }

    g.clear()
    g.drawFPS()
    g.drawShader()
    g.drawSchoolLogo()
  }
}

object DemoShaderGradient {
  def main(args: Array[String]): Unit = {
    new DemoShaderGradient().launch()
  }
}
