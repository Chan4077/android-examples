package com.edricchan.androidexamples.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
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
				getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
				return true;
			}
		});
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, ComponentsFragment.newInstance()).commit();
	}
}
