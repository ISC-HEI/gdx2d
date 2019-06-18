package ch.hevs.gdx2d.demos.shaders.circles

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3


/**
 * Demonstrates how to render an anti-aliased circles when using
 * GLSL shader. This does not use the CPU but the GPU instead for rendering.
 * If a graphics card is supported (either on desktop or Android), this should
 * be way faster than other methods.
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.4
 */
class DemoShaderCircleAntiAlias : PortableApplication() {
    lateinit var c: Circle
    private var time = 0f
    private var radius = 100f

    override fun onInit() {
        this.setTitle("Antialiasing of a circle using shaders, mui 2013")
        c = Circle(this.windowWidth / 2, this.windowHeight / 2)
        Logger.log("Press mouse anywhere to move the circle to that location")
        Logger.log("Scroll mouse to change the radius")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Sets some values, once
        if (g.shaderRenderer == null) {
            g.setShader("shader/circles/circle_aa.fp")
            g.shaderRenderer.setUniform("color", Vector3(Color.PINK.r, Color.PINK.g, Color.PINK.b))
        }

        g.clear()
        g.shaderRenderer.setUniform("radius", radius)
        g.shaderRenderer.setUniform("position", Vector2(c.pos.x, c.pos.y))

        // Update time
        time += 3 * Gdx.graphics.deltaTime

        g.drawShader(time)
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        c = Circle(x, y)
    }

    override fun onScroll(amount: Int) {
        super.onScroll(amount)
        radius += (8 * amount).toFloat()
    }

    override fun onDrag(x: Int, y: Int) {
        super.onDrag(x, y)
        c = Circle(x, y)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoShaderCircleAntiAlias()
        }
    }
}
