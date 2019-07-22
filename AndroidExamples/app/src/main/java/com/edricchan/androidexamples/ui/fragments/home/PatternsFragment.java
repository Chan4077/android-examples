package com.edricchan.androidexamples.ui.fragments.home;

import com.edricchan.androidexamples.DemosListFragment;
import com.edricchan.androidexamples.R;

public class PatternsFragment extends DemosListFragment {
	@Override
	public int getDemosRawRes() {
		return R.raw.patterns;
	}

	public static PatternsFragment newInstance() {
		return new PatternsFragment();
	}
}
