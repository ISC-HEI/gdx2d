package ch.hevs.gdx2d.demos.menus.screens

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.ScreenManager
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input
import ch.hevs.gdx2d.demos.menus.screens.example_screens.CreditsScreen
import ch.hevs.gdx2d.demos.menus.screens.example_screens.PhysicsScreen
import ch.hevs.gdx2d.demos.menus.screens.example_screens.SplashScreen

/**
 * Show how to add multiple screen and switch between them with different transitions.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
class DemoScreen : PortableApplication() {

    private val s = ScreenManager()
    private var transactionTypeId: Int = 0

    override fun onInit() {
        setTitle("Multiple screens and transitions")
        Logger.log("Press enter/space to show the next screen, 1/2/3 to transition to them")
        s.registerScreen(SplashScreen::class.java)
        s.registerScreen(PhysicsScreen::class.java)
        s.registerScreen(CreditsScreen::class.java)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        s.render(g)
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        // Delegate the click to the child class
        s.activeScreen!!.onClick(x, y, button)
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)

        // Display the next screen without transition
        if (keycode == Input.Keys.ENTER)
            s.activateNextScreen()

        // Switch to next screen using all available transitions effects
        if (keycode == Input.Keys.SPACE) {
            s.transitionToNext(ScreenManager.TransactionType.values()[transactionTypeId])

            // Switch to the next transition effect
            transactionTypeId = (transactionTypeId + 1) % ScreenManager.TransactionType.values().size
        }

        if (keycode == Input.Keys.NUM_1)
            s.transitionTo(0, ScreenManager.TransactionType.SLICE) // s.activateScreen(0);

        if (keycode == Input.Keys.NUM_2)
            s.transitionTo(1, ScreenManager.TransactionType.SLIDE) // s.activateScreen(1);

        if (keycode == Input.Keys.NUM_3)
            s.transitionTo(2, ScreenManager.TransactionType.SMOOTH) // s.activateScreen(2);
    }
}

fun main(args: Array<String>) {
  DemoScreen()
}
