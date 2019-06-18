package ch.hevs.gdx2d.demos.physics.collisions

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

import java.util.LinkedList

/**
 * Simple collision handling in box2d
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
class DemoCollisionListener : PortableApplication() {
    val otherBalls = LinkedList<BumpyBall>()
    var world = PhysicsWorld.getInstance()
    lateinit  var dbgRenderer: DebugRenderer
    var time = 0
    var generate = false
    lateinit var b1 : BumpyBall
    var b2: BumpyBall? = null
    var b3: BumpyBall? = null
    var b4: BumpyBall? = null

    override fun onInit() {
        dbgRenderer = DebugRenderer()
        setTitle("Collision demo for box2d, mui 2013")

        PhysicsScreenBoundaries(windowWidth.toFloat(), windowHeight.toFloat())
      
        // A BumpyBall has redefined its collision method.
        b1 = BumpyBall("ball 1", Vector2(100f, 250f), 30)

        // Indicate that the ball should be informed for collisions
        b1.enableCollisionListener()
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        b1.draw(g)
        b2?.draw(g)
        b3?.draw(g)
        b4?.draw(g)

        // Draw all the manually added balls
        for (b in otherBalls) {
            b.draw(g)
        }

        // Add balls from time to time
        if (time == 100) {
            b2 = BumpyBall("ball 2", Vector2(105f, 300f), 40)
            b2!!.enableCollisionListener()
        }

        if (time == 150) {
            b3 = BumpyBall("ball 3", Vector2(120f, 300f), 20)
            b3!!.enableCollisionListener()
        }

        if (time == 200) {
            b4 = BumpyBall("ball 4", Vector2(130f, 310f), 30)
            b4!!.enableCollisionListener()
        }

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
        time++
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        if (button == Input.Buttons.LEFT) {
            val newBall = BumpyBall("a ball", Vector2(x.toFloat(), y.toFloat()), 50)
            newBall.enableCollisionListener()
            otherBalls.add(newBall)
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoCollisionListener()
        }
    }
}
