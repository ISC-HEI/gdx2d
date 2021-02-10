package ch.hevs.gdx2d.demos.physics.mouse_interaction

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef

import java.util.Random

/**
 * A demo on how to use the mouse to move objects with `Box2D`.
 *
 * @author Pierre-André Mudry, mui 2013
 * @version 1.1
 */
class DemoPhysicsMouse : PortableApplication {
    protected var hitBody: Body? = null
    protected lateinit var groundBody: Body // Ground body to connect the mouse joint to
    protected var mouseJoint: MouseJoint? = null // Our mouse joint

    var world = PhysicsWorld.getInstance()

    lateinit var debugRenderer: DebugRenderer // Contains all the objects that will be simulated
    /**
     * we instantiate this vector and the callback here so we don't irritate the
     * GC
     */
    var testPoint = Vector2()
    var target = Vector2()

    // Called for AABB lookup
    var callback: QueryCallback = QueryCallback { fixture ->
        // if the hit point is inside the fixture of the body we report it
        if (fixture.testPoint(testPoint.x, testPoint.y)) {
            hitBody = fixture.body
            false
        } else
            true
    }

    //Constructor used by DemoSelectorGui via reflection
    constructor() : super()

  constructor(w: Int, h: Int) : super(w, h)

  override fun onInit() {
        setTitle("Mouse interactions in box2d, mui 2013")

        // We also need an invisible zero size ground body to which we can connect the mouse joint
        val bodyDef = BodyDef()
        groundBody = world.createBody(bodyDef)

        PhysicsScreenBoundaries(this.windowWidth.toFloat(), this.windowHeight.toFloat())
        PhysicsStaticBox("wall in the middle", Vector2((windowWidth / 2).toFloat(), 50f), 20f, 100f)

        // Build some boxes
        for (i in 0..9) {
            val r = Random()
            PhysicsBox("box", Vector2((100 + r.nextInt(100)).toFloat(), (200 + r.nextInt(100)).toFloat()),
                    16f, (r.nextInt(80) + 40).toFloat(), 1000f, 0.2f, 0.2f)
        }

        // Build the ball
        PhysicsCircle("ball", Vector2(100f, 500f), 20f)

        debugRenderer = DebugRenderer()
        debugRenderer.isDrawJoints = true
        debugRenderer.isDrawContacts = true
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()
        debugRenderer.render(world, g.camera.combined)
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawFPS()
        g.drawSchoolLogoUpperRight()
    }

    override fun onDrag(x: Int, y: Int) {
        if (mouseJoint != null) {
            // If a mouse joint exists we simply update the target of the joint based on the new mouse coordinates
            mouseJoint!!.target = target.set(x.toFloat(), y.toFloat()).scl(PhysicsConstants.PIXEL_TO_METERS)
        }
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        if (mouseJoint != null) {
            // If a mouse joint exists we simply destroy it
            world.destroyJoint(mouseJoint!!)
            mouseJoint = null
        }
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        // Translate the mouse coordinates to world coordinates
        testPoint.set(x.toFloat(), y.toFloat()).scl(PhysicsConstants.PIXEL_TO_METERS)

        // Ask the world which bodies are within the given bounding box around the mouse pointer
        hitBody = null

        world.QueryAABB(callback, testPoint.x - 5, testPoint.y - 5, testPoint.x + 5, testPoint.y + 5)

        // Ignore kinematic bodies, they don't work with the mouse joint
        if (hitBody == null || hitBody!!.type == BodyType.KinematicBody)
            return

        // If we hit something we create a new mouse joint and attach it to the hit body
        if (hitBody != null) {
            val def = MouseJointDef()
            def.bodyA = groundBody
            def.bodyB = hitBody
            def.collideConnected = true
            def.dampingRatio = 0.8f
            def.target.set(testPoint.x, testPoint.y)
            def.maxForce = 100.0f * hitBody!!.mass

            mouseJoint = world.createJoint(def) as MouseJoint
            hitBody!!.isAwake = true
        }
    }

    override fun onDispose() {
        super.onDispose()
        debugRenderer.dispose()
    }
}

fun main(args: Array<String>) {
  DemoPhysicsMouse(800, 500)
}
