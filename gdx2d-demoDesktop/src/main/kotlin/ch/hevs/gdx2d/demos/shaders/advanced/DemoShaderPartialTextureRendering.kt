package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoShaderPartialTextureRendering : PortableApplication() {

    var t = 0.0

    override fun onInit() {
        this.setTitle("Partial screen shader demo, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null)
            g.setShader("shader/bicolor.fp", 200, 200)

        g.clear()
        g.drawFPS()
        g.drawShader(256, (256 + 128.0 * Math.sin(t)).toInt(), 0f)
        g.drawSchoolLogo()

        t += 0.05
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderPartialTextureRendering()
        }
    }
}
