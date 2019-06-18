package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef

/**
 * A demo on how to build chains with rope joints with box2d
 *
 * @author Pierre-André Mudry, mui
 * @author Thierry Hischier, hit
 * @version 1.2
 * @date 2014
 */
class DemoRopeJoint : PortableApplication() {
    internal var world = PhysicsWorld.getInstance()

    // Contains all the objects that will be simulated
    internal lateinit var debugRenderer: DebugRenderer

    override fun onInit() {
        setTitle("Rope joints simulation, hit/mui 2014")
        val w = windowWidth
        val h = windowHeight

        debugRenderer = DebugRenderer()

        // The amount of rope segments
        val nSegments = 10

        /**
         * The circle in the middle of the screen for the origin
         */
        val p = PhysicsStaticBox("", Vector2(w / 2.0f, h / 1.4f), 20f, 20f)

        // The first object in the chain is the static circle
        var prevBody = p.body

        val segmentLength = 20
        val spaceBetweenSegments = 10

        // Build the body and the RevoluteJointDef for the chain elements.
        for (i in 0 until nSegments) {
            // Create a rope segment element
            val box = PhysicsBox("", Vector2((w / 2 + i * (segmentLength + spaceBetweenSegments)).toFloat(), h / 1.4f),
                    segmentLength.toFloat(),
                    4f)

            // Connect each element with the element before
            val anchorA = prevBody.localCenter.add(0f, 0f)

            // The anchor point should be outside the object here to make it nice
            val anchorB = box.bodyLocalCenter.add((-(segmentLength + spaceBetweenSegments)).toFloat(), 0f)

            // Create a joint between the previous and current object
            val revoluteJointDefRope = RevoluteJointDef()
            revoluteJointDefRope.bodyA = prevBody
            revoluteJointDefRope.bodyB = box.body
            revoluteJointDefRope.collideConnected = false
            revoluteJointDefRope.localAnchorA.set(PhysicsConstants.coordPixelsToMeters(anchorA))
            revoluteJointDefRope.localAnchorB.set(PhysicsConstants.coordPixelsToMeters(anchorB))
            world.createJoint(revoluteJointDefRope)

            // Create the joint and set the previous body to the created one
            prevBody = box.body
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)
        debugRenderer.render(world, g.camera.combined)

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoRopeJoint()
        }
    }
}
