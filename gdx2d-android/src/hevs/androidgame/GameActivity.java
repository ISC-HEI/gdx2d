package hevs.androidgame;

import hevs.gdx2d.demos.accelerometer.DemoAccelerometer;
import hevs.gdx2d.demos.physics.DemoPhysicsBalls;
import hevs.gdx2d.lib.Game2D;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * Sample application for Android using LibGDX HEVS wrapper.
 * 
 * @author Pierre-Andre Mudry (mui)
 */
public class GameActivity extends AndroidApplication {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Keep the screen on, without dimming
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// Force orientation portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		initialize(new Game2D(new DemoAccelerometer(true)), true);					
	}		
}
