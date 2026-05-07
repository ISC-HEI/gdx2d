package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color

/**
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class CreditsScreen extends RenderingScreen {
  override def onInit(): Unit = {}

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.DARK_GRAY)
    g.drawStringCentered((g.getScreenHeight / 2).toFloat, "3 - Ending screen")
  }

  override def dispose(): Unit = {
    PhysicsWorld.dispose()
    super.dispose()
  }
}
