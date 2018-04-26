package com.edricchan.androidexamples.ui.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.edricchan.androidexamples.R;
import com.edricchan.androidexamples.ui.fragments.home.ComponentsFragment;
import com.edricchan.androidexamples.ui.fragments.home.PatternsFragment;
import com.edricchan.androidexamples.ui.fragments.home.LibsFragment;

public class HomeActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
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
				getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
				return true;
			}
		});
		getFragmentManager().beginTransaction().add(R.id.fragmentContainer, ComponentsFragment.newInstance()).commit();
	}
}
