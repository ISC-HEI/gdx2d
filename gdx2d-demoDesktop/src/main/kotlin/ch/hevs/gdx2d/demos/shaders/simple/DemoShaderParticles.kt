package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx


/**
 * A nice particle shader. Shader code taken from
 * http://glsl.heroku.com/e#13789.0
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
class DemoShaderParticles : PortableApplication() {

    private var time = 0f

    override fun onInit() {
        this.setTitle("A nice particle shader demo, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null)
            g.setShader("shader/particles.fp")

        g.clear()

        // Draws the shader
        time += Gdx.graphics.deltaTime
        g.drawShader(time)

        g.drawStringCentered(windowHeight * 0.95f,
                "Original shader code from http://glsl.heroku.com/e#13789.0")
        g.drawFPS()
        g.drawSchoolLogo()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderParticles()
        }
    }
}
