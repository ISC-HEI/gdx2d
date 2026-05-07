package ch.hevs.gdx2d.demos.menus.screens

import ch.hevs.gdx2d.demos.menus.screens.example_screens.{CreditsScreen, PhysicsScreen, SplashScreen}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input

/**
 * Show how to add multiple screens and switch between them with different
 * transitions.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoScreen extends DesktopApplication {

  private val s = new ScreenManager()
  private var transactionTypeId = 0

  override def onInit(): Unit = {
    setTitle("Multiple screens and transitions")
    Logger.log("Press enter/space to show the next screen, 1/2/3 to transition to them")
    s.registerScreen(classOf[SplashScreen])
    s.registerScreen(classOf[PhysicsScreen])
    s.registerScreen(classOf[CreditsScreen])
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    s.render(g)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    if (s.getActiveScreen != null) {
      s.getActiveScreen.onClick(x, y, button)
    }
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)

    if (keycode == Input.Keys.ENTER) s.activateNextScreen()

    if (keycode == Input.Keys.SPACE) {
      val types = ScreenManager.TransactionType.values()
      s.transitionToNext(types(transactionTypeId))
      transactionTypeId = (transactionTypeId + 1) % types.length
    }

    if (keycode == Input.Keys.NUM_1) s.transitionTo(0, ScreenManager.TransactionType.SLICE)
    if (keycode == Input.Keys.NUM_2) s.transitionTo(1, ScreenManager.TransactionType.SLIDE)
    if (keycode == Input.Keys.NUM_3) s.transitionTo(2, ScreenManager.TransactionType.SMOOTH)
  }
}

object DemoScreen {
  def main(args: Array[String]): Unit = {
    new DemoScreen().launch()
  }
}
