package hevs.androiddemoselector;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import hevs.gdx2d.lib.interfaces.AndroidResolver;

/**
 * Android resolver implementation for libgdx applications.
 * <p/>
 * The resolver is attached to the UI thread to display Toast and Dialog.
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class GDXEventHandler implements AndroidResolver {

	private Activity mActivity;
	private AboutDialog mDialog;
	private Handler mHandler;

	public GDXEventHandler(Activity activity) {
		mActivity = activity;
		mDialog = new AboutDialog(activity);

		// Handler attached to the UI thread
		mHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void showAboutBox() {
		// Another alternative would be to use Activity.runOnUiThread()
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				mDialog.show();
			}
		});
	}

	@Override
	public void dismissAboutBox() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				mDialog.dismiss();
			}
		});
	}

	@Override
	public void showToast(final CharSequence text, final int duration) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				// Display Toast on the default position
				Toast.makeText(mActivity, text, duration).show();
			}
		});
	}
}
