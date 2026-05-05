package ch.hevs.gdx2d.demos.music

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color

/**
 * This demonstrates how to play short samples with GDX2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoBabyPiano extends DesktopApplication {

  private var s1: SoundSample = _
  private var s2: SoundSample = _
  private var s3: SoundSample = _
  private var s4: SoundSample = _

  override def onInit(): Unit = {
    setTitle("Sound samples player, mui 2014")

    // Load the sound files
    s1 = new SoundSample("music/babypianosamples/Honky_C1.wav")
    s2 = new SoundSample("music/babypianosamples/Honky_C2.wav")
    s3 = new SoundSample("music/babypianosamples/Honky_C3.wav")
    s4 = new SoundSample("music/babypianosamples/Honky_C4.wav")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.BLACK)
    g.setColor(Color.LIGHT_GRAY)

    val text = "Press 1/2/3/4 to play different samples"
    g.drawString(20f, (getWindowHeight / 2).toFloat, text)

    g.drawSchoolLogo()
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)

    if (keycode == Keys.NUM_1) s1.play()
    if (keycode == Keys.NUM_2) s2.play()
    if (keycode == Keys.NUM_3) s3.play()
    if (keycode == Keys.NUM_4) s4.play()

    if (keycode == Keys.SPACE) {
      s1.setPitch(2f)
      s2.setPitch(2f)
      s3.setPitch(2f)
      s4.setPitch(2f)
    }
  }

  override def onDispose(): Unit = {
    super.onDispose()
    s1.dispose()
    s2.dispose()
    s3.dispose()
    s4.dispose()
  }
}

object DemoBabyPiano {
  def main(args: Array[String]): Unit = {
    new DemoBabyPiano().launch()
  }
}
