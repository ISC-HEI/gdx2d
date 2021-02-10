package ch.hevs.gdx2d.demos.lights

import box2dLight.PointLight
import box2dLight.RayHandler
import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * A simple demo for the gdx2light integration (lights and shadows)
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
class DemoRotateLight : PortableApplication() {

    protected val radius = 0.3f
    // Various attributes used for light animation
    protected var width: Int = 0
    protected var height: Int = 0
    protected var angle: Float = 0.toFloat()
    protected var clicked = false
    protected var rotationCenter = Vector2()
    // The physics world (because light uses physics for positions)
    lateinit var world: World
    // This object manages the different lights of the scene
    lateinit var rayHandler: RayHandler
    // A single light
    lateinit var p: PointLight
    var hue = 0.3f
    var direction = 0.001f
    // The objects located in the middle of the screen that will cast shadows
    lateinit var circle1: PhysicsCircle
    lateinit var circle2: PhysicsCircle
    lateinit var circle3: PhysicsCircle

    override fun onInit() {
        this.setTitle("Rotate light demo, mui 2013")
        Gdx.app.log("[DemoLights]", "Left click to move the light source")

        world = PhysicsWorld.getInstance()

        // We don't need gravity
        world.gravity = Vector2(0f, 0f)

        width = Gdx.graphics.width
        height = Gdx.graphics.height

        rayHandler = RayHandler(world)

        // The light has a position, a manager, a color and an intensity
        p = PointLight(rayHandler, 800, Color.LIGHT_GRAY, 8f,
                (width / 2 - 30) * PhysicsConstants.PIXEL_TO_METERS,
                (height / 2 + 120) * PhysicsConstants.PIXEL_TO_METERS)

        p.distance = 8f
        p.isSoft = true
        p.color = ColorUtils.hsvToColor(hue, 0.8f, 1.0f)

        // Creates the objects that will cast shadows
        circle1 = PhysicsCircle("circle", Vector2((width / 2).toFloat(), (height / 2).toFloat()), 10f, 1.2f, 1f, 0.01f)
        circle2 = PhysicsCircle("circle", Vector2((width / 2 - width / 10).toFloat(), (height / 2).toFloat()), 10f, 1.2f, 1f, 0.01f)
        circle3 = PhysicsCircle("circle", Vector2((width / 2 + width / 10).toFloat(), (height / 2).toFloat()), 10f, 1.2f, 1f, 0.01f)

        // This has to be done once because the physics has other
        // coordinates than pixels
        val other = OrthographicCamera()
        other.setToOrtho(false)
        other.combined.scl(PhysicsConstants.METERS_TO_PIXELS)
        rayHandler.setCombinedMatrix(other.combined)

        rotationCenter.set((width / 2).toFloat(), height * 0.65f)
        rotationCenter.scl(PhysicsConstants.PIXEL_TO_METERS)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Render the blue objects
        var pos = circle1.bodyPosition
        g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)
        pos = circle2.bodyPosition
        g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)
        pos = circle3.bodyPosition
        g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)

        // Make the light turn
        angle += 0.05f

        // Change the light's color
        if (hue > 0.99f || hue <= 0.01) {
            direction *= -1f
        }
        hue += direction
        p.color = ColorUtils.hsvToColor(hue, 0.8f, 1.0f)

        // If the user plays with its mouse, move the light accordingly. When done, make it turn
        if (clicked)
            p.setPosition(rotationCenter.x, rotationCenter.y)
        else
            p.setPosition((rotationCenter.x - radius + radius * Math.cos(angle.toDouble())).toFloat(), (rotationCenter.y + radius * Math.sin(angle.toDouble())).toFloat())

        // Update the light rays
        g.beginCustomRendering()
        rayHandler.updateAndRender()
        g.endCustomRendering()

        // Update the physics
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawSchoolLogo()
        g.drawFPS()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        if (button == Input.Buttons.LEFT) {
            rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
            clicked = true
        }
    }

    override fun onDrag(x: Int, y: Int) {
        rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        super.onRelease(x, y, button)
        if (clicked) {
            angle = 0f
            clicked = false
        }
    }
}

fun main(args: Array<String>) {
  DemoRotateLight()
}
