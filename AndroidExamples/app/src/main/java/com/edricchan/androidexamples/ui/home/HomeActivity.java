package com.edricchan.androidexamples.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.edricchan.androidexamples.CommonActivity;
import com.edricchan.androidexamples.R;
import com.edricchan.androidexamples.ui.fragments.home.ComponentsFragment;
import com.edricchan.androidexamples.ui.fragments.home.LibsFragment;
import com.edricchan.androidexamples.ui.fragments.home.PatternsFragment;
import com.edricchan.androidexamples.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends CommonActivity {
	private static String TAG = HomeActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Fragment selectedFragment = null;
				switch (item.getItemId()) {
					case R.id.action_components:
						selectedFragment = ComponentsFragment.newInstance();
						break;
					case R.id.action_patterns:
						selectedFragment = PatternsFragment.newInstance();
						break;
					case R.id.action_libs:
						selectedFragment = LibsFragment.newInstance();
						break;
				}
				getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
				return true;
			}
		});
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, ComponentsFragment.newInstance()).commit();
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public int getMenuRes() {
		return R.menu.menu_home;
	}

	@Override
	public int getLayoutRes() {
		return R.layout.activity_home;
	}
}
