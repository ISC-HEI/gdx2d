package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color

/**
 * Created by Pierre-André Mudry on 07.05.2015.
 * HES-SO Valais, 2015
 */
class CreditsScreen : RenderingScreen() {
    override fun onInit() {}

    public override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.DARK_GRAY)
        g.drawStringCentered((g.screenHeight / 2).toFloat(), "3 - Ending screen")
    }

    override fun dispose() {
        PhysicsWorld.dispose()
        super.dispose()
    }
}
