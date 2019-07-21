package com.edricchan.androidexamples;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.edricchan.androidexamples.utils.SharedUtils;

/**
 * Represents a generic activity that all other activities can extend from.<br>
 * This generic activity provides the following features:
 * <ul>
 * <li>Adds the ability to specify a layout resource to be used in
 * {@link android.app.Activity#setContentView(int)} (See {@link CommonActivity#getLayoutRes()})</li>
 * <li>Adds the ability to specify a menu resource to be inflated in
 * {@link android.app.Activity#onCreateOptionsMenu(Menu)} (See {@link CommonActivity#getMenuRes()}</li>
 * <li>Adds the ability to specify whether the home button should be displayed
 * as up instead. (See {@link CommonActivity#getDisplayHomeAsUpEnabled()})</li>
 * <li>Automatically handles the home button in
 * {@link android.app.Activity#onOptionsItemSelected(MenuItem)} for you!
 * (See {@link CommonActivity#getDisplayHomeAsUpEnabled()}</li>
 * <li>Adds the ability to specify if the child activity is an example activity (See {@link CommonActivity#isExampleActivity()})</li>
 * <li>Auto-initialises the setting of the theme/dark theme (See {@link SharedUtils#initAppTheme(Activity)} and
 * {@link SharedUtils#initAppDarkTheme(Activity)})</li>
 * <li>And more potential features in the future!</li>
 * </ul>
 */
public abstract class CommonActivity extends AppCompatActivity {
	/**
	 * Represents the default resource value.
	 */
	public static int RES_NONE = 0;

	/**
	 * The layout resource to be set on {@link android.app.Activity#setContentView(int)}.<br>
	 * This should be overridden in classes that extend this class.
	 *
	 * @return A valid layout resource reference.
	 */
	@LayoutRes
	public int getLayoutRes() {
		return RES_NONE;
	}

	/**
	 * The menu resource to be inflated in {@link android.app.Activity#onCreateOptionsMenu(Menu)}.<br>
	 * This should be overridden in classes that extend this class.
	 *
	 * @return A valid menu resource reference.
	 */
	@MenuRes
	public int getMenuRes() {
		return RES_NONE;
	}

	/**
	 * Whether the home button should be displayed as an up button.<br>
	 * Note: Setting this to true will automatically handle the home button for you in
	 * {@link android.app.Activity#onOptionsItemSelected(MenuItem)}.
	 *
	 * @return True if this should be enabled, false otherwise (default)
	 */
	public boolean getDisplayHomeAsUpEnabled() {
		return false;
	}

	/**
	 * Whether the activity is an example.
	 *
	 * @return True if the activity should be an example, false otherwise
	 */
	public boolean isExampleActivity() {
		return false;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean applyThemeToExamples = preferences.getBoolean("pref_apply_theme_examples", true);
		// If the activity is an example activity and the Apply theme to examples switch is toggled on,
		// we should allow theming. Otherwise, if the switch is off, we shouldn't allow theming unless
		// the activity isn't an example activity.
		boolean shouldApplyTheme = false;
		if (isExampleActivity()) {
			if (applyThemeToExamples) {
				shouldApplyTheme = true;
			}
		} else {
			shouldApplyTheme = true;
		}
		if (savedInstanceState == null && shouldApplyTheme) {
			SharedUtils.initAppTheme(this);
			SharedUtils.initAppDarkTheme(this);
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(getDisplayHomeAsUpEnabled());

		if (getLayoutRes() != RES_NONE) {
			setContentView(getLayoutRes());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (getMenuRes() != RES_NONE) {
			getMenuInflater().inflate(getMenuRes(), menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (getDisplayHomeAsUpEnabled() && item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
