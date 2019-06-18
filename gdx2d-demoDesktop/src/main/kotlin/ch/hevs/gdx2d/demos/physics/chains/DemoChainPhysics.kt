package ch.hevs.gdx2d.demos.physics.chains

import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.components.physics.PhysicsChain
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import java.util.LinkedList
import java.util.Random

/**
 *
 * Demonstration of chained objects. Here the chain is the thing on which the
 * balls fall.
 *
 *
 *
 * Based on ex 5.3 from the Nature of code book
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 * @see [The nature of code example](http://natureofcode.com/book/chapter-5-physics-libraries/)
 */
class DemoChainPhysics : PortableApplication {
    internal val balls = LinkedList<PhysicsBall>()
    internal val r = Random()

    lateinit var w: World
    lateinit var chain: PhysicsChain

    // The rate at which the balls are generated
    var GENERATION_RATE = 7

    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    var generate = false

    constructor() : super() {

        if (onAndroid())
            GENERATION_RATE = 3
    }

    constructor(w: Int, h: Int) : super(w, h) {}

    override fun onInit() {
        this.setTitle("Physics objects in well demo, mui 2013")

        Gdx.app.log("[DemoChainPhysics]", "Left click to generate balls")
        Gdx.app.log("[DemoChainPhysics]", "Right click to generate random terrain")
        Gdx.app.log("[DemoChainPhysics]", "Middle click to generate Catmull-Rom terrain")
        Gdx.app.log("[DemoChainPhysics]", "'r' to modify rendering type")

        w = PhysicsWorld.getInstance()

        width = windowWidth.toFloat()
        height = windowHeight.toFloat()

        chain = PhysicsChain(Vector2(width / 10, height * 0.33f),
                Vector2(width - width / 10, height * 0.33f), 8,
                PhysicsChain.chain_type.CATMUL)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.LIGHT_GRAY)
        chain.draw(g)

        // Draws the balls
        val iter = balls.iterator()
        while (iter.hasNext()) {
            val ball = iter.next()
            ball.draw(g)

            val p = ball.bodyPosition

            // If a ball is not visible anymore, it should be destroyed
            if (p.y > height || p.y < 0 || p.x > width || p.x < 0) {
                // Mark the ball for deletion when possible
                ball.destroy()

                // Remove the ball from the collection as well
                iter.remove()
            }
        }

        g.drawFPS()
        g.drawString(5f, 30f, "#Obj: " + w.bodyCount)
        g.drawSchoolLogo()

        // Generate new balls if required
        if (generate) {
            for (i in 0 until GENERATION_RATE) {
                val x = width / 10 + r.nextFloat() * (width - width / 10)
                val y = height * 0.8f + r.nextInt(10)
                val position = Vector2(x, y)
                val b = PhysicsBall(position,
                        r.nextInt(10) + 6,
                        Palette.pastel2[r.nextInt(Palette.pastel2.size)])
                balls.add(b)
            }
        }

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)

        if (keycode == Input.Keys.R) {
            PhysicsBall.change_rendering()
        }
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)

        if (button == Input.Buttons.LEFT)
            generate = true

        if (button == Input.Buttons.MIDDLE)
            chain.catmull_chain(5)

        if (button == Input.Buttons.RIGHT)
            chain.random_chain(15)
    }

    override fun onTap(x: Float, y: Float, count: Int, button: Int) {
        super.onTap(x, y, count, button)
        if (count == 2) {
            chain.catmull_chain(5)
        }
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        super.onRelease(x, y, button)
        generate = false
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoChainPhysics(1000, 600)
        }
    }

}
