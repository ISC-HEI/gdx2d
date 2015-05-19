package hevs.gdx2d.demos.music.midiplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.*;

/**
 * MIDI handler class that contains the MIDI thread
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public class MIDIHandler
{
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	
	private long totalticks;
	private long tickCount;
	private int key;
	private int octave;
	private float bpm;
	private float msecRes;

	private File f;
	private ArrayList<IMIDIMessage> listeners;

	private Thread thread;
	private MidiEvent event;
	private MidiMessage message;
	private Sequence sequence;
	private ShortMessage sm;

	private long last_tick = 0;
	private long current_tick = 0;
	private boolean running;

	public MIDIHandler(String s)
	{
		key = 0;
		octave = 0;
		f = new File(s);
		listeners = new ArrayList<IMIDIMessage>();
	}
	
	public void readMIDI()
	{
		running = true;
		MIDIThread();
		thread.setName("MIDI THREAD");	
		thread.start();
	}

	/**
	 * MIDI Thread
	 */
	public void MIDIThread()
	{	
		thread = new Thread(new Runnable()
		{
			public void run()
			{
				while(running)
				{
					try
					{
						sequence = MidiSystem.getSequence(f);
					}
					catch(InvalidMidiDataException e)
					{
						e.printStackTrace();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					ArrayList<SimpleMIDIMessage> simpleMIDImessages = new ArrayList<SimpleMIDIMessage>();
					
					for(Track track : sequence.getTracks())
					{	
						if(!running)
						{
							break;
						}
						totalticks=track.ticks();
						for(int i = 0; i < track.size(); i++)
						{
							if(!running)
							{
								break;
							}
							tickCount++;
							event = track.get(i);
							message = event.getMessage();
							if(message instanceof ShortMessage)
							{
								sm = (ShortMessage)message;
								key = sm.getData1();
								octave = (key / 12) - 1;
								if(sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF)
								{
									last_tick = current_tick;
									current_tick = event.getTick();
									if(current_tick == last_tick)
									{
										simpleMIDImessages.add(new SimpleMIDIMessage(event.getTick(), sm.getChannel(), sm.getCommand(), key, octave));
									}
									else if(current_tick != last_tick)
									{
										simpleMIDImessages.clear();
										simpleMIDImessages.add(new SimpleMIDIMessage(event.getTick(), sm.getChannel(), sm.getCommand(), key, octave));
									}
								}
							}
							if(message instanceof MetaMessage)
							{
								MetaMessage mm = (MetaMessage)message;
								if(tickCount >=  totalticks)
								{						
									terminate();
									kill();
								}
								if(mm.getType() == 0x51)
								{
									bpm = 60000000f / (long)(mm.getMessage()[3] * Math.pow(2, 16) + (mm.getMessage()[4] & 0xff) * Math.pow(2, 8) + mm.getMessage()[5]);
									msecRes = (60000f) / (sequence.getResolution() * bpm);
								}
							}
							try
							{
								if((float)(current_tick - last_tick) < 0 || !running)
								{
									running = false;
									kill();
									break;
								}
								else
								{
									notifyListeners(simpleMIDImessages);
									Thread.sleep(Math.round((msecRes * (float)(current_tick - last_tick))));
								}
							}
							catch(InterruptedException ie)
							{
								terminate();
							}						 
						}
					}
				}
			}		
		});
	}
	
	public void terminate()
	{
		running = false;
	}

	public void notifyListeners(ArrayList<SimpleMIDIMessage> msg)
	{
		for(IMIDIMessage l : listeners)
		{
			l.onMIDIMessage(msg);
		}
	}
	
	public void kill()
	{
		System.out.println("Notify end of track");
		for(IMIDIMessage l : listeners)
		{
			l.onTrackFinished();
		}
	}

	public void addMIDIListener(IMIDIMessage im)
	{
		listeners.add(im);
	}

	public float getMIDITimeResolution()
	{
		return msecRes;
	}
}
