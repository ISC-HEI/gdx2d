package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * A screen to check that physics work in screens
 */
class PhysicsScreen : RenderingScreen() {
    // Contains all the objects that will be simulated
    var world = PhysicsWorld.getInstance()
    lateinit var ball: PhysicsCircle
    lateinit var debugRenderer: DebugRenderer

    override fun onInit() {
        val w = Gdx.graphics.width
        val h = Gdx.graphics.height

        // Build the walls around the screen
        PhysicsScreenBoundaries(w.toFloat(), h.toFloat())

        // The slope on which the objects roll
        PhysicsStaticBox("slope", Vector2((w / 2).toFloat(), (h / 2).toFloat()), (w / 3 * 2).toFloat(), 16f, Math.PI.toFloat() / 12.0f)

        // Build the falling object
        ball = PhysicsCircle("none", Vector2(w * 0.7f, h - 0.1f * h), 12f, 0.5f, 0.3f, 0.3f)
        ball.setBodyLinearVelocity(-1f, 1f)

        // Build the dominoes
        val nDominoes = 20
        val dominoSpace = (w - 60) / nDominoes

        for (i in 0 until nDominoes) {
            PhysicsBox("box$i", Vector2((60 + i * dominoSpace).toFloat(), 120f), 6f, 60f, 0.1f, 0.1f, 0.3f)
        }

        debugRenderer = DebugRenderer()
    }

    public override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        debugRenderer.render(world, g.camera.combined)
        PhysicsWorld.updatePhysics(Gdx.graphics.rawDeltaTime)
        g.drawStringCentered((g.screenHeight / 2).toFloat(), "2 - Main game screen")

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    override fun dispose() {
        super.dispose()
        PhysicsWorld.getInstance().dispose()
    }
}
