package ch.hevs.gdx2d.demos.music

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * Shows how to play music in the framework.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 2.0
 */
class DemoMusicPlay extends DesktopApplication {

  private var f: MusicPlayer = _

  override def onInit(): Unit = {
    setTitle("Music player, mui 2013")
    // Load the MP3 sound file
    f = new MusicPlayer("music/Blues-Loop.mp3")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.setColor(Color.WHITE)

    val text = if (f.isPlaying) "Playing song" else "Click to play"
    g.drawString(20f, (getWindowHeight / 2).toFloat, text)

    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    if (f.isPlaying)
      f.stop()
    else
      f.loop()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    f.dispose()
  }
}

object DemoMusicPlay {
  def main(args: Array[String]): Unit = {
    new DemoMusicPlay().launch()
  }
}
