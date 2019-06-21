package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx

/**
 * Demonstrates the use of a texture in a shader
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.2
 */
class DemoTexture : PortableApplication() {

    var t = 0f
    var clicked = false
    var image1 = true

    override fun onInit() {
        this.setTitle("Texture shader / simple animation, mui 2013")
        Logger.log("Click to change picture")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/advanced/vignette.fp")
            g.shaderRenderer.addTexture("images/lena.png", "texture0")
        }

        t += Gdx.graphics.deltaTime

        g.clear()
        g.drawShader(t)
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        clicked = true
    }
}

fun main(args: Array<String>) {
  DemoTexture()
}
