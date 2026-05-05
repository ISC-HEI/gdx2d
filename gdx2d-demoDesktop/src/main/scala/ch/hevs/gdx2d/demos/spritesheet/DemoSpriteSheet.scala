package ch.hevs.gdx2d.demos.spritesheet

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

/**
 * This demo demonstrates how to load a spritesheet and display its content as a
 * simple animation.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSpriteSheet extends DesktopApplication {

  private val SPRITE_WIDTH = 64
  private val SPRITE_HEIGHT = 64
  private val FRAME_TIME = 0.15 // duration of each frame (seconds)

  private var ss: Spritesheet = _
  private var textureY: Int = 1

  private var dt: Float = 0f
  private var currentFrame: Int = 0
  private val nFrames: Int = 4

  override def onInit(): Unit = {
    setTitle("SpriteSheet demo")
    ss = new Spritesheet("images/lumberjack_sheet.png", SPRITE_WIDTH, SPRITE_HEIGHT)
    Logger.log("Press up/down to change the current animation")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)
    g.drawFPS()

    dt += Gdx.graphics.getDeltaTime
    if (dt > FRAME_TIME) {
      dt = 0f
      currentFrame = (currentFrame + 1) % nFrames
    }

    g.draw(ss.sprites(textureY)(currentFrame),
      (getWindowWidth / 2 - SPRITE_WIDTH / 2).toFloat,
      (getWindowHeight / 2 - SPRITE_HEIGHT / 2).toFloat)

    g.drawSchoolLogo()
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    keycode match {
      case Input.Keys.DOWN => textureY = (textureY + 1) % ss.sprites.length
      case Input.Keys.UP   => textureY = if (textureY - 1 < 0) ss.sprites.length - 1 else textureY - 1
      case _ => ()
    }
  }
}

object DemoSpriteSheet {
  def main(args: Array[String]): Unit = new DemoSpriteSheet().launch()
}
