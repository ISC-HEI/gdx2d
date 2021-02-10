package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.math.Vector2

/**
 * Julia set as a shader, based on mei code
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 0.2
 */
class DemoJulia : PortableApplication() {

    var t = 0f
    var clicked = false
    var image1 = true
    var direction = 1
    var juliaPrm = 0.35f
    var scale = 1.10f
    var offset = Vector2(0f, 0f)

    override fun onInit() {
        this.setTitle("Julia set shader, mui 2013")
        Logger.log("Click to change picture")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/julia.fp")
            g.shaderRenderer.addTexture("shader/pal.png", "texture0")
        }

        g.shaderRenderer.setUniform("scale", scale)
        g.shaderRenderer.setUniform("offset", offset)
        g.shaderRenderer.setUniform("center", Vector2(juliaPrm, juliaPrm))
        juliaPrm += direction * (10.0f / 30000.0f)

        if (juliaPrm < 0.33 || juliaPrm > 0.4)
            direction *= -1

        g.clear()
        g.drawShader(t)
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onScroll(amount: Int) {
        super.onScroll(amount)
        scale += (amount * 0.03).toFloat()
    }

    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {
        super.onPan(x, y, deltaX, deltaY)
        offset.x -= (deltaX / 200.0).toFloat()
        offset.y -= (deltaY / 200.0).toFloat()
    }
}

fun main(args: Array<String>) {
  DemoJulia()
}
