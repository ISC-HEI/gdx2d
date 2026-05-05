package ch.hevs.gdx2d.demos.music.midiplayer.midilayer

import java.util.ArrayList

/**
 * Interface for communication between classes.
 *
 * @author Mikael Follonier (fom)
 * @version 2.0
 */
trait MIDIListener {
  /**
   * Method called when a MIDI message has to be handled.
   */
  def onMIDIMessage(msg: ArrayList[MIDIMessage]): Unit

  /**
   * Method called when the MIDI track is finished.
   */
  def onTrackFinished(): Unit
}
