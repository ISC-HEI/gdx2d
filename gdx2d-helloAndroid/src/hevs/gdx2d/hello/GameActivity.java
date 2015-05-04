package hevs.gdx2d.hello;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import hevs.gdx2d.lib.Game2D;

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

		// Load the HelloWorld demo from the "hdx2d-helloDesktop" project
		initialize(new Game2D(new HelloWorld()));
	}
}
