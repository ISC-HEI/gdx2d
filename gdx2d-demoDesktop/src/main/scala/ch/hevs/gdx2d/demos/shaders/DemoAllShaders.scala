package ch.hevs.gdx2d.demos.shaders

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.math.Vector2

/**
 * A demo that shows many shaders.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoAllShaders extends DesktopApplication {

  private val shaders = Array(
    "underwater.fp", "galaxy.fp", "joyDivision.fp", "stars.fp", "colorRect.fp",
    "plasma.fp", "gradient.fp", "particles.fp", "pulse.fp", "circles/circle3.fp",
    "advanced/vignette.fp")

  private var time = 0f
  private var currentShaderID = 0
  private var previousShaderID = -1
  private val mouse = new Vector2()

  override def onInit(): Unit = {
    setTitle("Shaders demos (some from Heroku), right click to change, mui 2014")
    Logger.log("Right click to change the shader")
    mouse.set(getWindowWidth / 2f, getWindowHeight / 2f)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (currentShaderID != previousShaderID) {
      g.setShader(s"shader/${shaders(currentShaderID)}")
      val sr = g.getShaderRenderer
      if (sr != null) {
        sr.addTexture("images/lena.png", "texture0")
      }
      Logger.log(s"Current shader set to ${shaders(currentShaderID)}")
      previousShaderID = currentShaderID
    }

    g.clear()

    val sr = g.getShaderRenderer
    if (sr != null) {
      sr.setUniform("mouse", mouse)
    }

    g.drawShader(time)
    time += Gdx.graphics.getDeltaTime

    g.drawFPS()
    g.drawStringCentered((0.98 * g.getScreenHeight).toInt.toFloat,
      s"Shader demo '${shaders(currentShaderID)}' ${currentShaderID + 1}/${shaders.length}")
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Buttons.RIGHT) {
      currentShaderID = (currentShaderID + 1) % shaders.length
    }
    mouse.set(x.toFloat, y.toFloat)
  }

  override def onDrag(x: Int, y: Int): Unit = {
    super.onDrag(x, y)
    mouse.set(x.toFloat, y.toFloat)
  }
}

object DemoAllShaders {
  def main(args: Array[String]): Unit = {
    new DemoAllShaders().launch()
  }
}
