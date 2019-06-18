package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoShaderGradient : PortableApplication() {

    override fun onInit() {
        this.setTitle("Gradient shader, no animation, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null)
            g.setShader("shader/gradient.fp")

        g.clear()
        g.drawFPS()
        g.drawShader()
        g.drawSchoolLogo()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderGradient()
        }
    }
}
