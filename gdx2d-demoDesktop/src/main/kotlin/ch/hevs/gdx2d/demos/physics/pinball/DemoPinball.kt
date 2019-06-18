package ch.hevs.gdx2d.demos.physics.pinball

import java.util.LinkedList

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticCircle
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World


/**
 * WORK IN PROGRESS, DO NOT USE
 */
class DemoPinball : PortableApplication() {

    var world = PhysicsWorld.getInstance()
    private var debugRenderer: DebugRenderer? = null

    private var draw_mode = draw_mode_e.DEBUG_STD

    private var leftFlipper: Flipper? = null
    private var rightFlipper: Flipper? = null
    private var ball: Ball? = null
    private val ball_position = Vector2(51f, 150f)
    private var bumperLeft: PhysicsStaticLine? = null
    private var bumperRight: PhysicsStaticLine? = null
    private var ballSprite: Spritesheet? = null
    private var displayHelp = 500
    private lateinit var renderer: Renderer
    private lateinit var decorations: LinkedList<TemporaryDrawable>

    private enum class draw_mode_e {
        STD, DEBUG, DEBUG_STD
    }

    private open inner class DrawablePhysicsStaticLine(name: String, private val p1: Vector2, private val p2: Vector2,
                                                       density: Float, restitution: Float, friction: Float) : PhysicsStaticLine(name, p1, p2, density, restitution, friction), DrawableObject {

        override fun draw(g: GdxGraphics) {
            g.setColor(Color.GRAY)
            g.drawLine(p1.x, p1.y, p2.x, p2.y)
        }
    }

    private inner class DrawablePhysicsPolygon : PhysicsPolygon, DrawableObject {

        constructor(name: String, vertices: Array<Vector2>, density: Float, restitution: Float, friction: Float, dynamic: Boolean) : super(name, vertices, density, restitution, friction, dynamic) {

        }

        constructor(name: String, vertices: Array<Vector2>,
                    dynamic: Boolean) : super(name, vertices, dynamic) {
        }

        override fun draw(g: GdxGraphics) {
            g.setColor(Color.GRAY)
            g.drawPolygon(this.polygon)
        }
    }

    private open inner class DrawablePhysicsStaticCircle : PhysicsStaticCircle, DrawableObject {
        private var radius: Float = 0.toFloat()

        constructor(name: String, position: Vector2, radius: Float) : super(name, position, radius) {
            this.radius = radius
        }

        constructor(name: String, position: Vector2, radius: Float, density: Float, restitution: Float, friction: Float) : super(name, position, radius, density, restitution, friction) {
            this.radius = radius
        }

        override fun draw(g: GdxGraphics) {
            g.setColor(Color.GRAY)
            g.drawCircle(this.bodyPosition.x, this.bodyPosition.y, radius)
        }

    }

    fun scale(x: Float): Float {
        val h = Gdx.graphics.height.toFloat()
        val w = Gdx.graphics.width.toFloat()

        return x * Math.min(h, w)
    }

    override fun onInit() {
        ballSprite = Spritesheet("images/pinball/sprites.png", 20, 20)
        decorations = LinkedList()
        val h = Gdx.graphics.height.toFloat()
        val w = Gdx.graphics.width.toFloat()
        setTitle("Pinball, pim 2015")
        Logger.log("Hello")

        world.gravity = PinballSettings.G
        PhysicsScreenBoundaries(h * PinballSettings.PINBALL_SIZE.x, w * PinballSettings.PINBALL_SIZE.y)
        debugRenderer = DebugRenderer()
        renderer = Renderer()

        val left_center = Vector2(50f, 50f)
        val right_center = Vector2(170f, 50f)

        leftFlipper = Flipper("left_flipper", Vector2(left_center).add(
                3f, -3f), 50f, 10f, -25f, 50f, ballSprite!!.sprites[1])
        rightFlipper = Flipper("right_flipper",
                Vector2(right_center).add(-3f, -3f), 50f, 10f, (-180 + 25).toFloat(), -50f,
                ballSprite!!.sprites[1])

        DrawablePhysicsPolygon("out1", arrayOf(Vector2(0f, 0f), Vector2(0f, left_center.y * 2), Vector2(left_center), Vector2(left_center.x, 1f)), false)
        DrawablePhysicsPolygon("out2", arrayOf(Vector2(right_center.x, 0f), Vector2(right_center), Vector2(w, h / 2), Vector2(w, 0f)), false)
        DrawablePhysicsPolygon("bumperLeft_frame", arrayOf(Vector2(left_center).add(-20f, 55f), Vector2(left_center).add(-20f, 95f), Vector2(left_center).add(5f, 55f)), false)

        bumperLeft = object : DrawablePhysicsStaticLine("bumperLeft", Vector2(
                left_center).add(-19f, 96f), Vector2(left_center).add(4f, 56f),
                4f, 2f, .6f) {
            override fun collision(theOtherObject: AbstractPhysicsObject,
                                   energy: Float) {
                super.collision(theOtherObject, energy)
                println("yahoo")
            }
        }

        DrawablePhysicsPolygon("bumperRight_frame", arrayOf(Vector2(right_center).add(20f, 55f), Vector2(right_center).add(20f, 95f), Vector2(right_center).add(-5f, 55f)), false)

        SensorSpinner("toto", Vector2(0f, left_center.y).add(
                12.5f, 55f), 25f, 25f, ballSprite!!.sprites[2])

        bumperRight = DrawablePhysicsStaticLine("bumperRight", Vector2(
                right_center).add(19f, 96f),
                Vector2(right_center).add(-4f, 56f), 4f, 2f, .6f)

        ball = Ball("ball", ball_position, scale(PinballSettings.BALL_DIAMETER / 2), ballSprite!!.sprites[0])
        ball!!.enableCollisionListener()

        object : DrawablePhysicsStaticCircle("b1", Vector2(250f, 450f), 20f, 1f, 2f, .6f) {
            override fun collision(theOtherObject: AbstractPhysicsObject,
                                   energy: Float) {
                println("houla")
            }
        }
        DrawablePhysicsStaticCircle("b2", Vector2(325f, 525f), 20f, 1f, 2f, .6f)
        DrawablePhysicsStaticCircle("b3", Vector2(400f, 450f), 20f, 1f, 2f, .6f)
    }

    private fun moveCamera(g: GdxGraphics, p: Vector2) {
        val h = Gdx.graphics.height.toFloat()
        // float w = Gdx.graphics.getWidth();

        // Set the ball in the upper part of the screen
        var y = p.y - h / PinballSettings.PINBALL_SIZE.y - h / 3

        // Restrict the camera inside the world
        if (y > h) {
            y = h
        }
        if (y < 0) {
            y = 0f
        }

        g.moveCamera(0f, y)
    }

    fun drawStd(g: GdxGraphics) {
        val h = Gdx.graphics.height.toFloat()
        val w = Gdx.graphics.width.toFloat()

        g.setBackgroundColor(Color.WHITE)

        g.setColor(Color(.8f, .8f, .8f, 1f))
        var x = 0
        while (x < h) {
            g.drawLine(x.toFloat(), 0f, x.toFloat(), 2 * h)
            x += 25
        }

        var y = 0
        while (y < 2 * h) {
            g.drawLine(0f, y.toFloat(), w, y.toFloat())
            y += 25
        }

        renderer.draw(g, world, decorations)

        g.drawSchoolLogo()
    }

    override fun onGraphicRender(g: GdxGraphics) {

        g.clear()
        PhysicsWorld.updatePhysics()
        moveCamera(g, PhysicsConstants.coordMetersToPixels(ball!!.body
                .position))
        //g.zoom(2f);

        when (draw_mode) {
            DemoPinball.draw_mode_e.DEBUG -> {
                debugRenderer!!.render(world, g.camera.combined)
                g.drawFPS(Color.BLACK)
            }
            DemoPinball.draw_mode_e.DEBUG_STD -> {
                debugRenderer!!.render(world, g.camera.combined)
                drawStd(g)
                g.drawFPS(Color.BLACK)
            }
            DemoPinball.draw_mode_e.STD -> drawStd(g)
        }

        if (displayHelp > 0) {
            displayHelp--
            g.setColor(Color(Color.BLACK).mul(displayHelp.toFloat() / 100))
            g.drawStringCentered(g.camera.position.y,
                    "Warning : work in progress\n" +
                            "CTRL keys : flippers\n" +
                            "SPACE : new ball\n" +
                            "d : debug view\n"
            )
        }
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)
        when (keycode) {
            Keys.CONTROL_LEFT -> leftFlipper!!.power(true)
            Keys.CONTROL_RIGHT -> rightFlipper!!.power(true)

            Keys.SPACE, Keys.D -> {
            }

            else -> displayHelp = 100
        }
    }

    override fun onKeyUp(keycode: Int) {
        super.onKeyUp(keycode)
        when (keycode) {
            Keys.CONTROL_LEFT -> leftFlipper!!.power(false)

            Keys.CONTROL_RIGHT -> rightFlipper!!.power(false)

            Keys.D -> when (draw_mode) {
                DemoPinball.draw_mode_e.DEBUG -> draw_mode = draw_mode_e.DEBUG_STD

                DemoPinball.draw_mode_e.DEBUG_STD -> draw_mode = draw_mode_e.STD

                DemoPinball.draw_mode_e.STD -> draw_mode = draw_mode_e.DEBUG
            }

            Keys.SPACE -> {
                val h = Gdx.graphics.height.toFloat()
                val w = Gdx.graphics.width.toFloat()
                ball!!.destroy()
                // ball = new Ball("ball", ball_position, 10f,
                // ballSprite.sprites[0]);
                ball = Ball("ball", Vector2(0f, h), scale(PinballSettings.BALL_DIAMETER / 2), ballSprite!!.sprites[0])
                ball!!.enableCollisionListener()
            }

            else -> displayHelp = 300
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoPinball()
        }
    }
}
