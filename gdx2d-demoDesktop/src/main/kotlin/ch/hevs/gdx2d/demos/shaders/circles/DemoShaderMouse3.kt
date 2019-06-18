package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx

/**
 * A circle that moves with the mouse, giggles and
 * has a nice color.
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.3
 */
class DemoShaderMouse3 : PortableApplication() {

    internal lateinit var c: Circle
    internal var time = 0f

    override fun onInit() {
        this.setTitle("Mouse shader interactions #2, mui 2013")
        c = Circle(this.windowWidth / 2, this.windowHeight / 2)
        Logger.log("Press mouse anywhere to move the circle to that location")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Sets some values, once
        if (g.shaderRenderer == null) {
            g.setShader("shader/circles/circle3.fp")
            g.shaderRenderer.setUniform("radius", 30f)
        }

        g.clear()

        // Pass the mouse position to the shader, always
        g.shaderRenderer.setUniform("mouse", c.pos)
        time += Gdx.graphics.deltaTime

        g.drawShader(time)
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
            DemoShaderMouse3()
        }
    }
}
