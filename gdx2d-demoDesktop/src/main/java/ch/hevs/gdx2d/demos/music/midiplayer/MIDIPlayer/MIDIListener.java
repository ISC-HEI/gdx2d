package ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer

import java.util.ArrayList

/**
 * Interface for communication between classes
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
interface MIDIListener {
    /**
     * Method called when a MIDI message has to be handled
     * @param msg
     */
    fun onMIDIMessage(msg: ArrayList<MIDIMessage>)

    /**
     * Method called when the MIDI track is finished
     */
    fun onTrackFinished()
}
