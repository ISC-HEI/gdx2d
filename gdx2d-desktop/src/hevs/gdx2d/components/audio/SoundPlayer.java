package hevs.gdx2d.components.audio;

import hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

/**
 * A class to play <em>short</em> samples within gdx2d. The music sample
 * must be short because it is completely loaded into memory.
 * 
 * @author Nils Chatton (chn)
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class SoundPlayer implements Disposable{
	protected Sound s;
	protected float volume = 1.0f;
		
	/**
	 * Constructor
	 * @param file the file to be loaded, using internal path representation
	 */
	public SoundPlayer(String file){
		s = Gdx.audio.newSound(Gdx.files.internal(file));		
		Utils.callCheck("hevs.gdx2d.lib.Game2D", "create");
	}
	
	/**
	 * Changes volume of the song played
	 * @param v Should be between 0 and 1
	 */
	public void setVolume(float v){
		if(v>1.0f||v<0.0f){
			new UnsupportedOperationException("Volume must be set in a range between 0 and 1");
		}
		else{
			volume = v;
		}
	}
	
	/**
	 * Starts playing the song
	 */
	public void play(){
		s.play(volume);
	}
	
	/**
	 * Stops playing the song
	 */
	public void stop(){
		s.stop();		
	}
	
	/**
	 * Plays the song in loop (repeat forever)
	 */
	public void loop(){
		s.loop();
	}
	
	/**
	 * Release resources when done working with them
	 */
	@Override
	public void dispose() {
		s.dispose();		
	}
	
	@Override
	protected void finalize() throws Throwable {	
		super.finalize();
		dispose();
	}
}
