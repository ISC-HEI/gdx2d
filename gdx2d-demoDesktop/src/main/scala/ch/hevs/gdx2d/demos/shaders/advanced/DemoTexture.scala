package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx

/**
 * Demonstrates the use of a texture in a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoTexture extends DesktopApplication {

  private var t = 0f

  override def onInit(): Unit = {
    setTitle("Texture shader / simple animation, mui 2013")
    Logger.log("Click to change picture")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/advanced/vignette.fp")
      g.getShaderRenderer.addTexture("images/lena.png", "texture0")
    }

    t += Gdx.graphics.getDeltaTime

    g.clear()
    g.drawShader(t)
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoTexture {
  def main(args: Array[String]): Unit = {
    new DemoTexture().launch()
  }
}
