package com.edricchan.androidexamples.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import com.edricchan.androidexamples.R;
import com.edricchan.androidexamples.ui.components.ComponentsFlatButtonActivity;
import com.edricchan.androidexamples.ui.components.ComponentsRaisedButtonActivity;
import com.edricchan.androidexamples.ui.data.ItemDisplayable;
import com.edricchan.androidexamples.ui.data.ItemID;
import com.google.android.material.snackbar.Snackbar;

public class SharedUtils {
	private final static String TAG = SharedUtils.class.getSimpleName();

	public static void initAppDarkTheme(Activity activity) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String selectedDarkTheme = preferences.getString("pref_dark_theme",
				activity.getString(R.string.pref_dark_theme_default));
		Log.d(TAG, "Current value of selected dark theme: " + selectedDarkTheme);
		switch (selectedDarkTheme) {
			case "always_on":
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
				break;
			case "auto_battery_saver":
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
				break;
			case "follow_system":
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
				break;
			case "always_off":
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
				break;
			default:
				Log.w(TAG, "Unknown night mode selected. Defaulting to AppCompatDelegate.MODE_NIGHT_UNSPECIFIED...");
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
				break;
		}
	}

	public static void initAppTheme(Activity activity) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String selectedTheme = preferences.getString("pref_theme", activity.getString(R.string.pref_theme_default));
		Log.d(TAG, "Current value of selected theme: " + selectedTheme);
		switch (selectedTheme) {
			case "bridge":
				// Use the Bridge version of Theme.MaterialComponents
				activity.setTheme(R.style.Theme_App);
				break;
			case "material_components":
				activity.setTheme(R.style.Theme_App_MaterialComponents);
				break;
			case "material_components_daynight":
				activity.setTheme(R.style.Theme_App_MaterialComponents_DayNight);
				break;
			default:
				Log.w(TAG, "Unknown theme selected. Defaulting to Theme.App.MaterialComponents.DayNight theme...");
				activity.setTheme(R.style.Theme_App_MaterialComponents_DayNight);
				break;
		}
		// Recreate the activity
//		activity.recreate();
	}

	/**
	 * Replaces a view with an initialised fragment.
	 * <br>Note: This method checks if there's already a fragment in the view.
	 * <br>(Based on StudyBuddy's SharedUtils#replaceFragment)
	 *
	 * @param activity       The activity.
	 * @param fragment       The fragment to replace the view with. (Needs to be initialised with a `new` constructor).
	 * @param viewId         The ID of the view.
	 * @param tag            The tag to assign to the fragment.
	 * @param addToBackStack Whether to add the fragment to the back stack.
	 * @return True if the fragment was replaced, false if there's already an existing fragment.
	 */
	public static boolean replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int viewId, String tag, boolean addToBackStack) {
		// Check if fragment already has been replaced
		if (activity.getSupportFragmentManager().findFragmentByTag(tag) != fragment
				&& activity.getSupportFragmentManager().findFragmentById(viewId) != fragment) {
			FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
			transaction.replace(
					viewId,
					fragment,
					tag
			);
			if (addToBackStack) {
				transaction.addToBackStack(null);
			}
			transaction.commit();
			// Indicate that the fragment replacement has been done.
			return true;
		}
		// Return false if there's already an existing fragment.
		return false;
	}

	/**
	 * Replaces a view with an initialised fragment.
	 * <br>Note: This method checks if there's already a fragment in the view.
	 * <br>(Based on StudyBuddy's SharedUtils#replaceFragment)
	 *
	 * @param activity       The activity.
	 * @param fragment       The fragment to replace the view with. (Needs to be initialised with a `new` constructor).
	 * @param viewId         The ID of the view.
	 * @param addToBackStack Whether to add the fragment to the back stack.
	 * @return True if the fragment was replaced, false if there's already an existing fragment.
	 */
	public static boolean replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int viewId, boolean addToBackStack) {
		if (activity.getSupportFragmentManager().findFragmentById(viewId) != fragment) {
			FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
			transaction.replace(
					viewId,
					fragment
			);
			if (addToBackStack) {
				transaction.addToBackStack(null);
			}
			transaction.commit();
			// Indicate that the fragment replacement has been done.
			return true;
		}
		// Return false if there's already an existing fragment.
		return false;
	}

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
				Snackbar.make(snackbarView, String.format("Demo for \"%1$s\" is not available", demo), Snackbar.LENGTH_LONG)
						.setAnchorView(R.id.bottomNavigation)
						.show();
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
