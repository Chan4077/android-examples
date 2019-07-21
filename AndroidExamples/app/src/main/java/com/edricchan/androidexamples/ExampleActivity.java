package com.edricchan.androidexamples;

/**
 * Represents an example activity.
 */
public abstract class ExampleActivity extends CommonActivity {

	@Override
	public boolean isExampleActivity() {
		return true;
	}

	@Override
	public boolean getDisplayHomeAsUpEnabled() {
		return true;
	}
}
