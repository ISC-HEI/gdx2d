package ch.hevs.gdx2d.demos.tilemap.advanced

import ch.hevs.gdx2d.components.bitmaps.{BitmapImage, Spritesheet}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.math.{Interpolation, Vector2}

/**
 * Character for the demo.
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class Hero(initialPosition: Vector2) extends DrawableObject {

  def this(x: Int, y: Int) = this(new Vector2((Hero.SPRITE_WIDTH * x).toFloat, (Hero.SPRITE_HEIGHT * y).toFloat))
  def this() = this(new Vector2(0f, 0f))

  var textureX: Int = 0
  var textureY: Int = 1
  var speed: Float = 1f

  private var dt: Float = 0f
  private var currentFrame: Int = 0
  private val nFrames: Int = 4
  private val FRAME_TIME = 0.1f // Duration of each frame

  private val ss = new Spritesheet("images/lumberjack_sheet32.png", Hero.SPRITE_WIDTH, Hero.SPRITE_HEIGHT)

  private var lastPosition = new Vector2(initialPosition)
  private var newPosition = new Vector2(initialPosition)
  var position = new Vector2(initialPosition)

  var isMoving = false
  private[advanced] def setMoving(m: Boolean): Unit = { isMoving = m }

  def animate(elapsedTime: Double): Unit = {
    val frameTime = FRAME_TIME / speed

    position = new Vector2(lastPosition)
    if (isMoving) {
      dt += elapsedTime.toFloat
      val alpha = (dt + frameTime * currentFrame) / (frameTime * nFrames)
      position.interpolate(newPosition, alpha, Interpolation.linear)
    } else {
      dt = 0f
    }

    if (dt > frameTime) {
      dt -= frameTime
      currentFrame = (currentFrame + 1) % nFrames

      if (currentFrame == 0) {
        isMoving = false
        lastPosition = new Vector2(newPosition)
        position = new Vector2(newPosition)
      }
    }
  }

  def go(direction: Hero.Direction.Value): Unit = {
    isMoving = true
    direction match {
      case Hero.Direction.RIGHT => newPosition.add(Hero.SPRITE_WIDTH.toFloat, 0f)
      case Hero.Direction.LEFT  => newPosition.add(-Hero.SPRITE_WIDTH.toFloat, 0f)
      case Hero.Direction.UP    => newPosition.add(0f, Hero.SPRITE_HEIGHT.toFloat)
      case Hero.Direction.DOWN  => newPosition.add(0f, -Hero.SPRITE_HEIGHT.toFloat)
      case _ => ()
    }
    turn(direction)
  }

  def turn(direction: Hero.Direction.Value): Unit = {
    direction match {
      case Hero.Direction.RIGHT => textureY = 2
      case Hero.Direction.LEFT  => textureY = 1
      case Hero.Direction.UP    => textureY = 3
      case Hero.Direction.DOWN  => textureY = 0
      case _ => ()
    }
  }

  override def draw(g: GdxGraphics): Unit = {
    g.draw(ss.sprites(textureY)(currentFrame), position.x, position.y)
  }
}

object Hero {
  private val SPRITE_WIDTH = 32
  private val SPRITE_HEIGHT = 32

  object Direction extends Enumeration {
    val UP, DOWN, RIGHT, LEFT, NULL = Value
  }
}
