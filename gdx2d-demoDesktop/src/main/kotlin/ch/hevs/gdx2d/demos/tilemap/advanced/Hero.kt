package ch.hevs.gdx2d.demos.tilemap.advanced

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2

/**
 * Character for the demo.
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 */
class Hero
/**
 * Create the hero at the start position
 * @param initialPosition Start position [px] on the map.
 */
@JvmOverloads constructor(initialPosition: Vector2 = Vector2(0f, 0f)) : DrawableObject {

    /**
     * The currently selected sprite for animation
     */
    var textureX = 0
    var textureY = 1
    var speed = 1f

    var dt = 0f
    var currentFrame = 0
    var nFrames = 4
    internal val FRAME_TIME = 0.1f // Duration of each frime
    var ss: Spritesheet

    var lastPosition: Vector2
    var newPosition: Vector2
    /**
     * @return the current position of the hero on the map.
     */
    var position: Vector2
        internal set

    internal val img = BitmapImage("images/pipe.png")


    /**
     * @return True if the hero is actually doing a step.
     */
    var isMoving = false
        private set

    enum class Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT,
        NULL
    }

    /**
     * Create the hero at the given start tile.
     * @param x Column
     * @param y Line
     */
    constructor(x: Int, y: Int) : this(Vector2((SPRITE_WIDTH * x).toFloat(), (SPRITE_HEIGHT * y).toFloat())) {}

    init {

        lastPosition = Vector2(initialPosition)
        newPosition = Vector2(initialPosition)
        position = Vector2(initialPosition)

        ss = Spritesheet("images/lumberjack_sheet32.png", SPRITE_WIDTH, SPRITE_HEIGHT)
    }

    /**
     * Update the position and the texture of the hero.
     * @param elapsedTime The time [s] elapsed since the last time which this method was called.
     */
    fun animate(elapsedTime: Double) {
        val frameTime = FRAME_TIME / speed

        position = Vector2(lastPosition)
        if (isMoving) {
            dt += elapsedTime.toFloat()
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
                lastPosition = Vector2(newPosition)
                position = Vector2(newPosition)
            }
        }
    }

    /**
     * Do a step on the given direction
     * @param direction The direction to go.
     */
    fun go(direction: Direction) {
        isMoving = true
        when (direction) {
            Hero.Direction.RIGHT -> newPosition.add(SPRITE_WIDTH.toFloat(), 0f)
            Hero.Direction.LEFT -> newPosition.add((-SPRITE_WIDTH).toFloat(), 0f)
            Hero.Direction.UP -> newPosition.add(0f, SPRITE_HEIGHT.toFloat())
            Hero.Direction.DOWN -> newPosition.add(0f, (-SPRITE_HEIGHT).toFloat())
            else -> {
            }
        }

        turn(direction)
    }

    /**
     * Turn the hero on the given direction without do any step.
     * @param direction The direction to turn.
     */
    fun turn(direction: Direction) {
        when (direction) {
            Hero.Direction.RIGHT -> textureY = 2
            Hero.Direction.LEFT -> textureY = 1
            Hero.Direction.UP -> textureY = 3
            Hero.Direction.DOWN -> textureY = 0
            else -> {
            }
        }
    }

    /**
     * Draw the character on the graphic object.
     * @param g Graphic object.
     */
    override fun draw(g: GdxGraphics) {
        g.draw(ss.sprites[textureY][currentFrame], position.x, position.y)
    }

    companion object {
        private val SPRITE_WIDTH = 32
        private val SPRITE_HEIGHT = 32
    }
}
/**
 * Create the hero at the start position (0,0)
 */
