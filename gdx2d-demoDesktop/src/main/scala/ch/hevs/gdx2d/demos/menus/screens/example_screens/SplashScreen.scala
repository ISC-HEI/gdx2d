package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A simple [[SplashScreen]] to demonstrate transition between screens.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class SplashScreen extends RenderingScreen {

  private var imgBitmap: BitmapImage = _

  override def onInit(): Unit = {
    imgBitmap = new BitmapImage("images/Android_PI_48x48.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.NAVY)
    g.drawStringCentered((g.getScreenHeight / 2).toFloat, "1 - Splash screen")
    g.drawPicture(g.getScreenWidth / 2.0f, g.getScreenHeight / 3.0f, imgBitmap)
    g.drawSchoolLogo()
  }

  override def dispose(): Unit = {
    imgBitmap.dispose()
  }
}
