package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoShaderSimple : PortableApplication() {

    private val time = 0f

    override fun onInit() {
        this.setTitle("Simple shader demo, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/bicolor.fp")
        }

        g.clear()

        // Draws the shader
        g.drawShader()

        g.drawFPS()
        g.drawSchoolLogo()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderSimple()
        }
    }
}
