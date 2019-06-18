package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger

/**
 * Shows how to pass parameters to a shader
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoShaderMouse2 : PortableApplication() {

    internal lateinit var c: Circle

    override fun onInit() {
        this.setTitle("Mouse shader interactions, mui 2013")
        c = Circle(this.windowWidth / 2, this.windowHeight / 2)
        Logger.log("Press mouse anywhere to move the circle to that location")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Sets some values, once
        if (g.shaderRenderer == null) {
            g.setShader("shader/circles/circle2.fp")
            g.shaderRenderer.setUniform("radius", 30.0f)
        }

        g.clear()

        // Pass the mouse position to the shader, always
        g.shaderRenderer.setUniform("mouse", c.pos)
        g.drawShader()

        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        c = Circle(x, y)
    }

    override fun onDrag(x: Int, y: Int) {
        super.onDrag(x, y)
        c = Circle(x, y)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderMouse2()
        }
    }
}
