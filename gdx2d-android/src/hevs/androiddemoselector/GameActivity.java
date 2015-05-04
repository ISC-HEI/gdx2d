package hevs.androiddemoselector;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.PortableApplication;

import java.lang.reflect.Constructor;

/**
 * Sample application for Android using LibGDX HEVS wrapper.
 *
 * @author Pierre-Andre Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.1
 */
public class GameActivity extends AndroidApplication {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Keep the screen on, without dimming
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Load extra String parameter
		String className = getIntent().getStringExtra("className");
		if (className == null)
			className = "simple.DemoSimpleAnimation"; // default demo

		try {
			// Get the class we want
			Class<?> clazz = Class.forName(className);
			// Extract its constructor using reflection
			Constructor<?> constructor = clazz.getConstructor(boolean.class);

			// Call the class constructor with a boolean parameter (we know it exists)
			PortableApplication instance = (PortableApplication) constructor.newInstance(true); // on Android

			// Required for triggering Android events from Libgdx
			instance.setAndroidResolver(new GDXEventHandler(this));

			// Just for physics, disable screen rotation
			if (className.contains("DemoPhysicsComplete"))
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			// Start the LibGDX application
			initialize(new Game2D(instance));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
