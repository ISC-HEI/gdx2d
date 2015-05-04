package hevs.androiddemoselector;

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
import hevs.gdx2d.lib.gui.SelectedDemos;
import hevs.gdx2d.lib.gui.SelectedDemos.DemoCategory;
import hevs.gdx2d.lib.gui.SelectedDemos.DemoDescriptor;
import hevs.gdx2d.lib.interfaces.AndroidResolver;

import java.util.LinkedHashMap;

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
		demoList = new LinkedHashMap<String, String>();

		for (DemoCategory cat : SelectedDemos.list)
		{
			for (DemoDescriptor demo : cat.descs)
			{
				demoList.put(demo.name, demo.clazz.getName());
			}
		}
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
