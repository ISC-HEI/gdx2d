package ch.hevs.gdx2d.demos.music.midiplayer.midilayer

import javax.sound.midi._
import com.badlogic.gdx.Gdx
import java.io.IOException
import java.util.ArrayList
import scala.collection.mutable.ArrayBuffer

/**
 * A MIDI file loader and player.
 *
 * @author Mikael Follonier (fom)
 * @version 2.0
 */
class MIDIPLayer(s: String) {

  private var sequence: Sequence = _
  private val listeners = new ArrayBuffer[MIDIListener]()

  private var midiTimeResolution: Float = 0f
  private var running = false

  init()

  private def init(): Unit = {
    val handle = Gdx.files.internal(s)
    try {
      sequence = MidiSystem.getSequence(handle.read())
    } catch {
      case e: InvalidMidiDataException => e.printStackTrace()
      case e: IOException =>
        System.err.println(s"Could not open file $handle")
        e.printStackTrace()
    }
  }

  def startReader(): Unit = {
    running = true
    val thread = new Thread(new Runnable {
      override def run(): Unit = {
        var bpm = 120f
        var currentTick: Long = 0
        var lastTick: Long = 0
        var tickCount: Long = 0

        while (running) {
          for (track <- sequence.getTracks) {
            if (!running) return

            for (i <- 0 until track.size()) {
              if (!running) return

              val event = track.get(i)
              val message = event.getMessage
              tickCount += 1

              if (message.isInstanceOf[ShortMessage]) {
                val sm = message.asInstanceOf[ShortMessage]
                val key = sm.getData1
                val octave = key / 12 - 1
                if (sm.getCommand == MIDIPLayer.NOTE_ON || sm.getCommand == MIDIPLayer.NOTE_OFF) {
                  lastTick = currentTick
                  currentTick = event.getTick

                  val messages = new ArrayList[MIDIMessage]()
                  messages.add(new MIDIMessage(event.getTick, sm.getChannel, sm.getCommand, key, octave))
                  notifyListeners(messages)

                  try {
                    val waitTime = math.round(midiTimeResolution * (currentTick - lastTick).toFloat)
                    if (waitTime > 0) Thread.sleep(waitTime)
                  } catch {
                    case _: InterruptedException => terminate()
                  }
                }
              }

              if (message.isInstanceOf[MetaMessage]) {
                val mm = message.asInstanceOf[MetaMessage]
                if (mm.getType == 0x51) {
                  val data = mm.getData
                  val tempo = ((data(0) & 0xff) << 16) | ((data(1) & 0xff) << 8) | (data(2) & 0xff)
                  bpm = 60000000f / tempo
                  midiTimeResolution = 60000f / (sequence.getResolution * bpm)
                }
              }
            }
          }
          terminate()
          kill()
        }
      }
    })
    thread.start()
  }

  def addMIDIListener(im: MIDIListener): Unit = {
    listeners += im
  }

  def terminate(): Unit = {
    running = false
  }

  private def notifyListeners(msg: ArrayList[MIDIMessage]): Unit = {
    listeners.foreach(_.onMIDIMessage(msg))
  }

  private def kill(): Unit = {
    listeners.foreach(_.onTrackFinished())
  }
}

object MIDIPLayer {
  val NOTE_ON = 0x90
  val NOTE_OFF = 0x80
}
