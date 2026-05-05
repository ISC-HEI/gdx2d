package ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer

/**
 * A simple MIDI message class
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
class MIDIMessage
/**
 *
 * @param tick MIDI time base
 * @param channel The current MIDI channel (~ which instrument)
 * @param command ON or OFF for the note in the current message
 * @param key The MIDI note (from 0 to 127)
 * @param octave The number of the octave
 */
(var current_time: Long // MIDI time base
 , var channel: Int // The current MIDI channel (~ which instrument)
 , var cmd: Int // ON or OFF for the note in the current message
 , key: Int, var octave: Int // The number of the octave
) {
    var key: Int = 0 // The MIDI note (from 0 to 127)
    var note: Int = 0 // The corresponding note in the scale (C = 0, C# = 1...)
    var noteName: String

    /**
     * Warning, MIDI messages don't start with the C on a piano
     */
    private val OFFSET = 24

    init {
        this.key = key - OFFSET
        note = this.key % 12
        noteName = NOTE_NAMES[note]
    }

    override fun toString(): String {
        return "Current tick : " + current_time + ", Channel : " + channel + ", Command : " +
                "" + cmd + ", Note : " + key + " " + noteName + ", Octave : " + octave
    }

    companion object {

        private val NOTE_NAMES = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    }
}
