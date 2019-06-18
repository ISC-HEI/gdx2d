package ch.hevs.gdx2d.demos.shaders.circles


import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Draws a simple, yet ugly, circle. This uses a shader.
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
class DemoShaderMouse1 : PortableApplication() {

    lateinit var c: Circle

    override fun onInit() {
        this.setTitle("Simple circle shader, mui 2013")
        c = Circle(this.windowWidth / 2, this.windowHeight / 2)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Sets some values, once
        if (g.shaderRenderer == null) {
            g.setShader("shader/circles/circle1.fp")
            // Pass the some information to the shader.
            g.shaderRenderer.setUniform("center", c.pos)
        }

        g.clear()
        g.drawShader()
        g.drawFPS()
        g.drawSchoolLogo()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderMouse1()
        }
    }
}
