package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger

/**
 * Demonstrates how to program a convolution using a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoConvolution extends DesktopApplication {

  private var currentMatrix = 0

  override def onInit(): Unit = {
    setTitle("Texture convolution - mui 2013")
    Logger.log("Click to change shader")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/advanced/convolution.fp")
      g.getShaderRenderer.addTexture("images/lena.png", "texture0")
    }

    g.getShaderRenderer.setUniform("matrix", currentMatrix)

    g.clear()
    g.drawShader()
    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    currentMatrix = (currentMatrix + 1) % 5
  }
}

object DemoConvolution {
  def main(args: Array[String]): Unit = {
    new DemoConvolution().launch()
  }
}
