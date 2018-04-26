package com.edricchan.androidexamples.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.edricchan.androidexamples.ui.components.ComponentsFlatButtonActivity;
import com.edricchan.androidexamples.ui.components.ComponentsRaisedButtonActivity;
import com.edricchan.androidexamples.ui.data.ItemDisplayable;
import com.edricchan.androidexamples.ui.data.ItemID;

public class SharedUtils {
	/**
	 * Starts an example
	 *
	 * @param activity The activity
	 * @param id       The id of the example
	 * @param demo     The demo's text to be shown if the demo isn't available
	 */
	public static void startExample(Activity activity, int id, String demo, View snackbarView) {
		switch (id) {
			case ItemID.COMPONENTS_RAISED_BUTTON:
				ComponentsRaisedButtonActivity.start(activity);
				break;
			case ItemID.COMPONENTS_FLAT_BUTTON:
				ComponentsFlatButtonActivity.start(activity);
				break;
			default:
				Snackbar.make(snackbarView, String.format("Demo for \"%1$s\" is not available", demo), Snackbar.LENGTH_LONG).show();
				break;
		}
	}

	public static void startExample(Activity activity, ItemDisplayable displayable) {
		startExample(activity, displayable.getId(), displayable.getTitle(), activity.findViewById(android.R.id.content));
	}

	public static void startExample(Activity activity, ItemDisplayable displayable, View snackbarView) {
		startExample(activity, displayable.getId(), displayable.getTitle(), snackbarView);
	}
}
