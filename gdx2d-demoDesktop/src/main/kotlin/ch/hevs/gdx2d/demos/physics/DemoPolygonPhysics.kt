package ch.hevs.gdx2d.demos.physics

import ch.hevs.gdx2d.components.graphics.GeomUtils
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * Demonstrates how to use general polygons as physics objects
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
class DemoPolygonPhysics : PortableApplication() {

    internal lateinit var p1: PhysicsPolygon
    internal lateinit var p2: PhysicsPolygon
    internal lateinit var debugRenderer: DebugRenderer

    override fun onInit() {
        // A triangle
        val obj1 = arrayOf(Vector2(100f, 100f), Vector2(200f, 100f), Vector2(150f, 200f))

        // A special polygon
        val obj2 = arrayOf(Vector2(0f, 0f), Vector2(4f, 0f), Vector2(5f, 3f), Vector2(2f, 8f), Vector2(-1f, 3f))

        GeomUtils.translate(obj1, Vector2(250f, 100f))
        GeomUtils.rotate(obj1, 12f)

        GeomUtils.scale(obj2, 20f)
        GeomUtils.rotate(obj2, 14f)
        GeomUtils.translate(obj2, Vector2(100f, 100f))

        p1 = PhysicsPolygon("poly1", obj1, true)
        p2 = PhysicsPolygon("poly2", obj2, true)

        PhysicsScreenBoundaries(this.windowWidth.toFloat(), this.windowHeight.toFloat())

        debugRenderer = DebugRenderer()
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        PhysicsWorld.updatePhysics(Gdx.graphics.rawDeltaTime)
        debugRenderer.render(PhysicsWorld.getInstance(), g.camera.combined)

        g.drawFilledPolygon(p1.polygon, Color.YELLOW)
        g.drawFilledPolygon(p2.polygon, Color.RED)

        g.drawSchoolLogo()
        g.drawFPS()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoPolygonPhysics()
        }
    }

}
