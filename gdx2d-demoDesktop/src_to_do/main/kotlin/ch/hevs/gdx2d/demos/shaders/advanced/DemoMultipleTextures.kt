package ch.hevs.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx

/**
 * Demonstrates the use of multiple textures in a shader
 *
 * @author Pierre-André Mudry (mui)
 * @version 0.5
 */
class DemoMultipleTextures : PortableApplication() {

    var time = 0f
    var i = 0

    override fun onInit() {
        this.setTitle("Multiple textures passing to shader, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/advanced/multiple_textures.fp")
            g.shaderRenderer.addTexture("images/lena.png", "texture0")
            g.shaderRenderer.addTexture("images/mandrill.jpg", "texture1")
        }

        g.shaderRenderer.setUniform("textureChosen", i)

        g.clear()
        time += Gdx.graphics.deltaTime
        g.drawShader(time)
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        i = (i + 1) % 2
    }
}

fun main(args: Array<String>) {
  DemoMultipleTextures()
}
