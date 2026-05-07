package ch.hevs.gdx2d.demos.music.midiplayer

import java.util.{ArrayList, Collections}

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import ch.hevs.gdx2d.demos.music.midiplayer.midilayer.{MIDIListener, MIDIMessage, MIDIPLayer}
import com.badlogic.gdx.graphics.Color

/**
 * A MIDI sampler demo.
 *
 * @author Mikael Follonier (fom)
 * @version 2.0
 */
class DemoMIDIPLayer extends DesktopApplication {

  private val nSamples = 104
  private val DATADIR = "music/midiplayer/pianonotes/"
  private val FILEPATH = "music/midiplayer/brahms_lullaby.mid"

  private var notesSamples: ArrayList[SoundSample] = _
  private val currentlyPlayingNotes = new ArrayList[Integer]()
  private var player: MIDIPLayer = _

  override def onInit(): Unit = {
    setTitle("MIDI demo, fom 2015")
    notesSamples = new ArrayList[SoundSample]()

    for (i <- 0 until nSamples) {
      notesSamples.add(new SoundSample(s"$DATADIR${"%03d".format(i)}.mp3"))
    }

    val midiListener = new MIDIListener {
      override def onMIDIMessage(msg: ArrayList[MIDIMessage]): Unit = {
        val it = msg.iterator()
        while (it.hasNext) {
          val message = it.next()
          currentlyPlayingNotes.synchronized {
            if (message.cmd == MIDIPLayer.NOTE_ON) {
              if (message.key >= 0 && message.key < notesSamples.size()) {
                notesSamples.get(message.key).play()
              }
              currentlyPlayingNotes.add(Integer.valueOf(message.note))
            }
            if (message.cmd == MIDIPLayer.NOTE_OFF) {
              currentlyPlayingNotes.remove(Integer.valueOf(message.note))
            }
            Collections.sort(currentlyPlayingNotes)
          }
        }
      }

      override def onTrackFinished(): Unit = {
        Logger.log("Track is over")
      }
    }

    try {
      player = new MIDIPLayer(FILEPATH)
      player.addMIDIListener(midiListener)
      player.startReader()
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawSchoolLogoUpperRight()

    currentlyPlayingNotes.synchronized {
      val it = currentlyPlayingNotes.iterator()
      while (it.hasNext) {
        val note = it.next()
        val noteColor = ColorUtils.hsvToColor(0.4f, 1f - note / 10.0f, 1f)
        g.drawFilledCircle(250f, 250f, 10f + 120f - note * 10f, noteColor)
      }
    }
  }

  override def onDispose(): Unit = {
    val it = notesSamples.iterator()
    while (it.hasNext) {
      val s = it.next()
      s.stop()
      s.dispose()
    }

    if (player != null) player.terminate()
    super.onDispose()
  }
}

object DemoMIDIPLayer {
  def main(args: Array[String]): Unit = {
    new DemoMIDIPLayer().launch()
  }
}
