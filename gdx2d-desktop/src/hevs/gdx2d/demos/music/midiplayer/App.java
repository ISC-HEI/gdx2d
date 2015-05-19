package hevs.gdx2d.demos.music.midiplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Input;

import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A MIDI sampler demo
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public class App extends PortableApplication implements IMIDIMessage
{
	private final String DATADIR = "data/music/midiplayer/pianonotes/";
	private final String FILEPATH = "data/music/midiplayer/brahms_lullaby.mid";
	private File f;
	private int filesqty;
	
	private ArrayList<SoundSample> notes;
	
	private MIDIHandler m;
	private final int OFFSET = 24;

	private boolean midiMessageArrived;
	private boolean running;

	public App(boolean onAndroid)
	{
		super(onAndroid);
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new App(false);
	}

	@Override
	public void onInit()
	{
		super.setTitle("MIDI demo, fom 2015");
		notes = new ArrayList<SoundSample>();
		midiMessageArrived = false;
		running = false;

		f = new File(DATADIR);
		filesqty = f.listFiles().length;
		for(int i = 0; i < filesqty; i++)
		{
			notes.add(new SoundSample(DATADIR + String.format("%03d", i) + ".wav"));
		}
	}

	@Override
	public void onGraphicRender(GdxGraphics g)
	{
		g.clear();
		g.drawSchoolLogoUpperRight();
		g.drawString(getWindowWidth()/2, getWindowHeight()/8, "Left click = start, right click = stop");
		if(midiMessageArrived)
		{
			g.drawStringCentered(getWindowHeight()/2, "MIDI message arrived");
		}
		midiMessageArrived = false;	
	}

	@Override
	public void onClick(int x, int y, int button)
	{
		if(button == Input.Buttons.LEFT && !running)
		{
			try
			{
				m = new MIDIHandler(FILEPATH);
				m.addMIDIListener(this);
				m.readMIDI();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			running = true;
		}
		if(button == Input.Buttons.RIGHT)
		{
			m.terminate();
			running = false;	
		}
	}
	
	/**
	 * Receive the MIDI message and play the corresponding sample
	 *
	 * @param msg msg Message list for a given MIDI tick
	 */
	@Override
	public void onMIDIMessage(ArrayList<SimpleMIDIMessage> msg)
	{
		
		// do something with the arrived messages
		for(Iterator<SimpleMIDIMessage> iterator = msg.iterator(); iterator.hasNext();)
		{
			SimpleMIDIMessage simpleMIDIMessage = (SimpleMIDIMessage)iterator.next();
			//System.out.println(simpleMIDIMessage.toString());
			if(simpleMIDIMessage.getCmd() == MIDIHandler.NOTE_ON)
			{
				midiMessageArrived = true;
				//MIDI notes start at octave -2
				notes.get(simpleMIDIMessage.getKey()-OFFSET).play();
			}
		}
	}
	
	@Override
	public void onTrackFinished()
	{
		System.out.println("Clean up before quit!");
		for(Iterator<SoundSample> iterator = notes.iterator(); iterator.hasNext();)
		{
			SoundSample soundSample = (SoundSample)iterator.next();
			soundSample.stop();
			soundSample.dispose();	
			
		}
		try
		{
			Thread.sleep(500);
		}
		catch(InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onDispose();
		exit();
	}
}
