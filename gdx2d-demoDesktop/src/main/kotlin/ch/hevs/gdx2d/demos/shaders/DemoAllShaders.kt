package ch.hevs.gdx2d.demos.shaders

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.math.Vector2

/**
 * A demo that shows many shaders, some of them from
 * Heroku, some of them original. The source of the shader
 * is always clearly indicated in the .fp file.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoAllShaders : PortableApplication() {

    val shaders = arrayOf("underwater.fp", "galaxy.fp", "joyDivision.fp", "stars.fp", "colorRect.fp", "plasma.fp", "gradient.fp", "particles.fp", "pulse.fp", "circles/circle3.fp", "advanced/vignette.fp")
    private var time = 0f
    private var currentShaderID = 0
    private var previousShaderID = currentShaderID
    private val mouse = Vector2()

    override fun onInit() {
        this.setTitle("Shaders demos (some from Heroku), right click to change, mui 2014")
        Logger.log("Right click to change the shader")
        mouse.x = (this.windowWidth / 2).toFloat()
        mouse.y = (this.windowHeight / 2).toFloat()
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/" + shaders[currentShaderID])
            g.shaderRenderer.addTexture("images/lena.png", "texture0")
        }

        if (currentShaderID != previousShaderID) {
            g.setShader("shader/" + shaders[currentShaderID])
            g.shaderRenderer.addTexture("images/lena.png", "texture0")
            Logger.log("Current shader set to " + shaders[currentShaderID])
            previousShaderID = currentShaderID
        }

        // Clears the screen
        g.clear()

        // Draws the shader
        g.shaderRenderer.setUniform("mouse", mouse)
        g.drawShader(time)

        // Update time
        time += Gdx.graphics.deltaTime

        // Draws the rest of the stuff
        g.drawFPS()
        g.drawStringCentered((0.98 * g.screenHeight).toInt().toFloat(),
                "Shader demo \"" + shaders[currentShaderID] + "\" " + (currentShaderID + 1)
                        + "/" + shaders.size)
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)

        if (button == Buttons.RIGHT || onAndroid())
            currentShaderID = (currentShaderID + 1) % shaders.size

        mouse.x = x.toFloat()
        mouse.y = y.toFloat()
    }

    override fun onDrag(x: Int, y: Int) {
        super.onDrag(x, y)
        mouse.x = x.toFloat()
        mouse.y = y.toFloat()
    }
}

fun main(args: Array<String>) {
  DemoAllShaders()
}
