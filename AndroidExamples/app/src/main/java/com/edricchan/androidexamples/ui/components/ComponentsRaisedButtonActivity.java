package com.edricchan.androidexamples.ui.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.edricchan.androidexamples.R;

public class ComponentsRaisedButtonActivity extends AppCompatActivity {
	public static void start(@NonNull Activity activity) {
		Intent intent = new Intent(activity, ComponentsRaisedButtonActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_components_raised_button);
	}
}
