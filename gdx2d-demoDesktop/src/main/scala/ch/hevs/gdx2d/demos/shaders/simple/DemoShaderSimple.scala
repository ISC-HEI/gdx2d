package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderSimple extends DesktopApplication {

  override def onInit(): Unit = {
    setTitle("Simple shader demo, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/bicolor.fp")
    }

    g.clear()
    g.drawShader()
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoShaderSimple {
  def main(args: Array[String]): Unit = {
    new DemoShaderSimple().launch()
  }
}
