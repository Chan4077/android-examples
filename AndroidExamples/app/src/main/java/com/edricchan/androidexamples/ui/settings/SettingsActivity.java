package com.edricchan.androidexamples.ui.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.edricchan.androidexamples.CommonActivity;
import com.edricchan.androidexamples.ui.settings.fragment.SettingsFragment;
import com.edricchan.androidexamples.utils.SharedUtils;

public class SettingsActivity extends CommonActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			SharedUtils.replaceFragment(this, new SettingsFragment(), android.R.id.content, false);
		}
	}

	@Override
	public boolean getDisplayHomeAsUpEnabled() {
		return true;
	}

	public boolean onSupportNavigateUp() {
		if (getSupportFragmentManager().popBackStackImmediate()) {
			return true;
		}
		return super.onSupportNavigateUp();
	}

	public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
		// Instantiate the new Fragment
		Bundle args = pref.getExtras();
		Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
				getClassLoader(),
				pref.getFragment());
		fragment.setArguments(args);
		fragment.setTargetFragment(caller, 0);
		setTitle(pref.getTitle());
		// Replace the existing Fragment with the new Fragment
		SharedUtils.replaceFragment(this, fragment, android.R.id.content, true);
		return true;
	}
}
