package hevs.gdx2d.demos.music.midiplayer;

import java.util.ArrayList;

/**
 * Interface for communication between classes
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public interface MIDIListener {
	/**
	 * Method called when a MIDI message has to be handled
	 * @param msg
	 */
	void onMIDIMessage(ArrayList<MIDIMessage> msg);

	/**
	 * Method called when the MIDI track is finished
	 */
	void onTrackFinished();
}
