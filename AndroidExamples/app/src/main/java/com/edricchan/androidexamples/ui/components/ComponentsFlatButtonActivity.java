package com.edricchan.androidexamples.ui.components;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.edricchan.androidexamples.ExampleActivity;
import com.edricchan.androidexamples.R;

public class ComponentsFlatButtonActivity extends ExampleActivity {
	public static void start(@NonNull Activity activity) {
		Intent intent = new Intent(activity, ComponentsFlatButtonActivity.class);
		activity.startActivity(intent);
	}

	@Override
	public int getLayoutRes() {
		return R.layout.activity_components_flat_button;
	}
}
