package com.edricchan.androidexamples.ui.settings.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.edricchan.androidexamples.BuildConfig;
import com.edricchan.androidexamples.R;

public class SettingsFragment extends PreferenceFragmentCompat {
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.pref_settings, rootKey);

		Preference appSrcPref = findPreference("pref_about_app_src");
		/*appSrcPref.setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						startActivity(
								new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("https://github.com/EdricChan03/android-examples")
								)
						);
						return true;
					}
				}
		);*/
		appSrcPref.setIntent(new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://github.com/EdricChan03/android-examples")));

		Preference appVersionPref = findPreference("pref_about_app_version");
		appVersionPref.setSummary(BuildConfig.VERSION_NAME);

		Preference appInfoPref = findPreference("pref_about_app_info");
		appInfoPref.setIntent(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
				.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
	}
}
