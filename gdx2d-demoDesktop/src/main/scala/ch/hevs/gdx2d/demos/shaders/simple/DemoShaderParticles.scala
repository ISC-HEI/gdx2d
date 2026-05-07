package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx

/**
 * A nice particle shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderParticles extends DesktopApplication {

  private var time = 0f

  override def onInit(): Unit = {
    setTitle("A nice particle shader demo, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("shader/particles.fp")
    }

    g.clear()

    time += Gdx.graphics.getDeltaTime
    g.drawShader(time)

    g.drawStringCentered(getWindowHeight * 0.95f, "Original shader code from http://glsl.heroku.com/e#13789.0")
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoShaderParticles {
  def main(args: Array[String]): Unit = {
    new DemoShaderParticles().launch()
  }
}
