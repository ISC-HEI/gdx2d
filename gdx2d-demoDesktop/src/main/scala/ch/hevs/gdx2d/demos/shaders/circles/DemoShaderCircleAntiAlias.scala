package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Vector2, Vector3}

/**
 * Demonstrates how to render anti-aliased circles when using GLSL shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderCircleAntiAlias extends DesktopApplication {

  private var c: Circle = _
  private var time = 0f
  private var radius = 100f

  override def onInit(): Unit = {
    setTitle("Antialiasing of a circle using shaders, mui 2013")
    c = new Circle(getWindowWidth / 2, getWindowHeight / 2)
    Logger.log("Press mouse anywhere to move the circle to that location")
    Logger.log("Scroll mouse to change the radius")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/circles/circle_aa.fp")
      g.getShaderRenderer.setUniform("color", new Vector3(Color.PINK.r, Color.PINK.g, Color.PINK.b))
    }

    g.clear()
    g.getShaderRenderer.setUniform("radius", radius)
    g.getShaderRenderer.setUniform("position", c.pos)

    time += 3 * Gdx.graphics.getDeltaTime
    g.drawShader(time)

    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    c.pos.set(x.toFloat, y.toFloat)
  }

  override def onScroll(amount: Int): Unit = {
    super.onScroll(amount)
    radius += 8f * amount
  }

  override def onDrag(x: Int, y: Int): Unit = {
    super.onDrag(x, y)
    c.pos.set(x.toFloat, y.toFloat)
  }
}

object DemoShaderCircleAntiAlias {
  def main(args: Array[String]): Unit = {
    new DemoShaderCircleAntiAlias().launch()
  }
}
