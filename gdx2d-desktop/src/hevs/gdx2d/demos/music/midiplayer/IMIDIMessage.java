package hevs.gdx2d.demos.music.midiplayer;

import java.util.ArrayList;

/**
 * Interface for communication between classes
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public interface IMIDIMessage
{
	public void onMIDIMessage(ArrayList<SimpleMIDIMessage> msg);
	public void onTrackFinished();
}
