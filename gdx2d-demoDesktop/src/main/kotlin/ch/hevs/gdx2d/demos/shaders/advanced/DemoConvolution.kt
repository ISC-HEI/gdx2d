package ch.hevs.gdx2d.demos.shaders.advanced


import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger

/**
 * Demonstrates how to program a convolution using a shader
 * FIXME This could be improved a lot
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoConvolution : PortableApplication() {

    var currentMatrix = 0

    override fun onInit() {
        this.setTitle("Texture convolution - mui 2013")
        Logger.log("Click to change shader")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/advanced/convolution.fp")
            g.shaderRenderer.addTexture("images/lena.png", "texture0")
        }

        // TODO Improve this, pass the matrix directly
        g.shaderRenderer.setUniform("matrix", currentMatrix)

        g.clear()
        g.drawShader()
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        currentMatrix = (currentMatrix + 1) % 5
    }
}

fun main(args: Array<String>) {
  DemoConvolution()
}
