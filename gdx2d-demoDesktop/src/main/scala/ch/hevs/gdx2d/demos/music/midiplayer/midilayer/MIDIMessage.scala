package ch.hevs.gdx2d.demos.music.midiplayer.midilayer

/**
 * A simple MIDI message class.
 *
 * @author Mikael Follonier (fom)
 * @version 2.0
 */
class MIDIMessage(val currentTime: Long, val channel: Int, val cmd: Int, rawKey: Int, val octave: Int) {
  private val OFFSET = 24
  val key: Int = rawKey - OFFSET
  val note: Int = key % 12
  val noteName: String = MIDIMessage.NOTE_NAMES(note)

  override def toString: String =
    s"Current tick : $currentTime, Channel : $channel, Command : $cmd, Note : $key $noteName, Octave : $octave"
}

object MIDIMessage {
  private val NOTE_NAMES = Array("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
}
