package hevs.gdx2d.demos.music.midiplayer;

/**
 * A simple MIDI message class
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public class SimpleMIDIMessage
{
	private long current_time;
	private int cmd;
	private int channel;
	private int key;
	private int note;
	private String noteName;
	private int octave;
	
	private static final String[] NOTE_NAMES =
	{ "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	
	public SimpleMIDIMessage(long t, int ch, int c, int k, int o)
	{
		current_time = t;
		channel = ch;
		cmd = c;
		key = k;
		octave = o;
		note = key % 12;
		noteName = NOTE_NAMES[note];
	}
	
	public SimpleMIDIMessage(int ch, int c, int k, int o)
	{
		channel = ch;
		cmd = c;
		key = k;
		octave = o;
		note = key % 12;
		noteName = NOTE_NAMES[note];
	}

	public long getCurrent_time()
	{
		return current_time;
	}

	public int getCmd()
	{
		return cmd;
	}

	public int getKey()
	{
		return key;
	}

	public int getOctave()
	{
		return octave;
	}
	
	public int getNote()
	{
		return note;
	}
	
	public String toString()
	{
		return "Current tick : "+current_time+", Channel : "+channel+", Command : "+cmd+", Note : "+key+" "+noteName+", Octave : "+octave;
	}	
}
