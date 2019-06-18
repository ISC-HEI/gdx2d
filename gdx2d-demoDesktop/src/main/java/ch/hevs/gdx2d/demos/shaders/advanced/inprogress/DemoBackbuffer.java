package ch.hevs.gdx2d.demos.shaders.advanced.inprogress

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Vector2

/**
 * @author Pierre-André Mudry (mui)
 * @version 0.1
 */
class DemoBackbuffer : PortableApplication() {

    internal lateinit var mouse: Vector2
    internal var currentMatrix = 0
    internal var time = 0f
    // Used for off screen rendering
    internal lateinit var fbo: FrameBuffer
    internal lateinit var t: Texture

    override fun onInit() {
        this.setTitle("Backbuffer - mui 2013")
        fbo = FrameBuffer(Format.RGBA8888, this.windowWidth, this.windowHeight, false)
        mouse = Vector2(0f, 0f)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (g.shaderRenderer == null) {
            g.setShader("shader/advanced/tbd/test.fp")
            t = Texture(512, 512, Format.RGB888)
            g.shaderRenderer.addTexture(t, "backbuffer")
        }

        fbo.begin()
        g.clear()
        //			g.drawFilledCircle(256, 256, 20, Color.RED);
        //			g.drawFilledCircle(0, 0, 20, Color.GREEN);
        g.drawFilledCircle(400f, 400f, 20f, Color.YELLOW)
        //			g.drawSchoolLogo();
        g.sbFlush()
        fbo.end()

        // Copy the offscreen buffer to the displayed bufer
        t = fbo.colorBufferTexture
        t.bind(1)
        //g.shaderRenderer.setTexture(t, 1);

        //		g.clear();
        //		g.spriteBatch.draw(t, 0, 0);

        time += Gdx.graphics.deltaTime
        g.drawShader(time)
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        mouse.x = x.toFloat()
        mouse.y = y.toFloat()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoBackbuffer()
        }
    }
}
