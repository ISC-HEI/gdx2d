package hevs.androiddemoselector;

import hevs.gdx2d.lib.interfaces.AndroidResolver;

import java.util.LinkedHashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Simple screen selector to select a demo to play.
 * 
 * @author Christopher Metrailler (mei)
 * @version 1.1
 */
public class DemoSelectorActivity extends ListActivity implements
		OnItemClickListener, OnClickListener {

	private AndroidResolver mResolver;

	// List of available demonstrations
	private final static LinkedHashMap<String, String> demoList;
	static {
		
		// TODO: add some others desktop demos (mei)
		
		demoList = new LinkedHashMap<String, String>();
		demoList.put("Very simple shapes", "simple.DemoSimpleShapes");
		demoList.put("Simple animation", "simple.DemoSimpleAnimation");
		demoList.put("Image drawing", "image_drawing.DemoSimpleImage");
		demoList.put("Image mirroring", "image_drawing.DemoMirrorImage");
		demoList.put("Image alpha", "image_drawing.DemoAlphaImage");
		demoList.put("Rotating image", "image_drawing.DemoRotatingImage");
		demoList.put("Music player", "music.DemoMusicPlay");
		demoList.put("Gestures", "gestures.DemoGesture");
		demoList.put("Scrolling", "scrolling.DemoScrolling");
		demoList.put("Animation tweening", "tween.tweenengine.DemoTween");
		demoList.put("Position interpolation", "tween.interpolatorengine.DemoPositionInterpolator");
		demoList.put("Complex shapes", "complex_shapes.DemoComplexShapes");
		demoList.put("Accelerometer", "accelerometer.DemoAccelerometer");
		demoList.put("Simple physics (dominoes)", "physics.DemoSimplePhysics");
		demoList.put("Physics soccer ball", "physics.DemoPhysicsBalls");
		demoList.put("Physics finger interaction", "physics.DemoPhysicsMouse");
		demoList.put("Physics collision detection",	"physics.collisions.DemoCollisionListener");
		demoList.put("Physics chains",	"physics.DemoChainPhysics");
		demoList.put("Particles", "physics.particle.DemoParticlePhysics");
		demoList.put("Lights", "lights.DemoLight");
		demoList.put("Lights (moving)", "lights.DemoRotateLight");
		
		// FIXME: not all shader are working on Android and resolution is too big (mei)
		demoList.put("Shaders collection", "shaders.DemoAllShaders");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);

		// ListView adapter
		String[] items = demoList.keySet().toArray(new String[0]);
		BaseAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		setListAdapter(adapter);

		// ListView item click
		getListView().setClickable(true);
		getListView().setOnItemClickListener(this);

		// GDXEventHandler
		mResolver = new GDXEventHandler(this);

		// Quit button
		Button btQuit = (Button) findViewById(R.id.btQuit);
		btQuit.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// Get the class name and create the Intent
		final String key = String.valueOf(getListAdapter().getItem(position));
		final Intent demoActivity = new Intent(this, GameActivity.class);
		demoActivity.putExtra("className", demoList.get(key));
		startActivity(demoActivity);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btQuit)
			finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.about:
			// Display the about dialog
			mResolver.showAboutBox();
			break;
		}
		return true;
	}

	@Override
	public void onStop() {
		super.onStop();
		mResolver.dismissAboutBox();
	}
}
