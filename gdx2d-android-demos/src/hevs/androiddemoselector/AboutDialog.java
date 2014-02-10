package hevs.androiddemoselector;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

/**
 * About dialog Activity. Display some HTML texts and links.
 * 
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
public class AboutDialog extends Dialog {

	private Spanned mInfoText, mLegalText;

	public AboutDialog(Context context) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

		// Default dialog settings
		setTitle(context.getString(R.string.about_title));
		setCanceledOnTouchOutside(true);
		setCancelable(true);

		mInfoText = Html.fromHtml(context.getString(R.string.about_info));
		mLegalText = Html.fromHtml(context.getString(R.string.about_legal));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		TextView tv;

		// Info text
		tv = (TextView) findViewById(R.id.info_text);
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setLinkTextColor(Color.LTGRAY);
		tv.setText(mInfoText);
		Linkify.addLinks(tv, Linkify.ALL);

		// Legal text
		tv = (TextView) findViewById(R.id.legal_text);
		tv.setText(mLegalText);
	}
}