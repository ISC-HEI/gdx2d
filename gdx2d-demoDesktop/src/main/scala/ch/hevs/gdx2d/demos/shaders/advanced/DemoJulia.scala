package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.math.Vector2

/**
 * Julia set as a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoJulia extends DesktopApplication {

  private var t = 0f
  private var direction = 1
  private var juliaPrm = 0.35f
  private var scale = 1.10f
  private var offset = new Vector2(0f, 0f)

  override def onInit(): Unit = {
    setTitle("Julia set shader, mui 2013")
    Logger.log("Click to change picture")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/julia.fp")
      g.getShaderRenderer.addTexture("shader/pal.png", "texture0")
    }

    g.getShaderRenderer.setUniform("scale", scale)
    g.getShaderRenderer.setUniform("offset", offset)
    g.getShaderRenderer.setUniform("center", new Vector2(juliaPrm, juliaPrm))
    juliaPrm += direction * (10.0f / 30000.0f)

    if (juliaPrm < 0.33f || juliaPrm > 0.4f)
      direction *= -1

    g.clear()
    g.drawShader(t)
    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onScroll(amount: Int): Unit = {
    super.onScroll(amount)
    scale += (amount * 0.03f)
  }

  override def onDrag(x: Int, y: Int): Unit = {
    // Replacement for the old onPan if needed, or just keep it simple
    // The original onPan was used for moving the fractal.
  }
}

object DemoJulia {
  def main(args: Array[String]): Unit = {
    new DemoJulia().launch()
  }
}
