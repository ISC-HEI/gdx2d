package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Demo application to demonstrate how to rotate an image.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoRotatingImage extends DemoSimpleImage {

  override def onInit(): Unit = {
    super.onInit()
    setTitle("Rotating image, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    g.drawTransformedPicture(
      (getWindowWidth / 2).toFloat,
      (getWindowHeight / 2).toFloat,
      counter,
      1f,
      imgBitmap)

    g.drawFPS()
    g.drawSchoolLogo()

    if (counter >= 360) counter = 0f
    counter += 1f
  }
}

object DemoRotatingImage {
  def main(args: Array[String]): Unit = new DemoRotatingImage().launch()
}
