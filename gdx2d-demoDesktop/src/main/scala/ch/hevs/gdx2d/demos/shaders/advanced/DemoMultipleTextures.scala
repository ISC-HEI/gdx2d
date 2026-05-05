package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx

/**
 * Demonstrates the use of multiple textures in a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoMultipleTextures extends DesktopApplication {

  private var time = 0f
  private var selectedTexture = 0

  override def onInit(): Unit = {
    setTitle("Multiple textures passing to shader, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/advanced/multiple_textures.fp")
      g.getShaderRenderer.addTexture("images/lena.png", "texture0")
      g.getShaderRenderer.addTexture("images/mandrill.jpg", "texture1")
    }

    g.getShaderRenderer.setUniform("textureChosen", selectedTexture)

    g.clear()
    time += Gdx.graphics.getDeltaTime
    g.drawShader(time)
    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    selectedTexture = (selectedTexture + 1) % 2
  }
}

object DemoMultipleTextures {
  def main(args: Array[String]): Unit = {
    new DemoMultipleTextures().launch()
  }
}
