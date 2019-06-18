package ch.hevs.gdx2d.demos.spritesheet

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color

/**
 * This demo demonstrates how to load a spritesheet
 * and display its content as a simple animation.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoSpriteSheet : PortableApplication() {

    /**
     * The size of each sprite in the sheet
     */
    internal val SPRITE_WIDTH = 64
    internal val SPRITE_HEIGHT = 64
    internal val FRAME_TIME = 0.15 // Duration of each frame
    internal lateinit var ss: Spritesheet
    /**
     * The currently selected sprite for animation
     */
    internal var textureX = 0
    internal var textureY = 1

    /**
     * Animation related parameters
     */
    internal var dt = 0f
    internal var currentFrame = 0
    internal var nFrames = 4

    override fun onInit() {
        setTitle("SpriteSheet demo")

        ss = Spritesheet("images/lumberjack_sheet.png", SPRITE_WIDTH, SPRITE_HEIGHT)
        Logger.log("Press up/down to change the current animation")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.LIGHT_GRAY)
        g.drawFPS()

        dt += Gdx.graphics.deltaTime

        // Do we have to display the next frame
        if (dt > FRAME_TIME) {
            dt = 0f
            currentFrame = (currentFrame + 1) % nFrames
        }

        // Display the current image of the animation
        g.draw(ss.sprites[textureY][currentFrame],
                (this.windowWidth / 2 - SPRITE_WIDTH / 2).toFloat(),
                (this.windowHeight / 2 - SPRITE_HEIGHT / 2).toFloat())

        g.drawSchoolLogo()

    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)

        when (keycode) {

            Input.Keys.DOWN -> textureY = (textureY + 1) % ss.sprites.size

            Input.Keys.UP -> textureY = if (textureY - 1 < 0) ss.sprites.size - 1 else textureY - 1

            else -> {
            }
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoSpriteSheet()
        }
    }

}

