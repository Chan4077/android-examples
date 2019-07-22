package com.edricchan.androidexamples.ui.fragments.home;

import com.edricchan.androidexamples.DemosListFragment;
import com.edricchan.androidexamples.R;

public class LibsFragment extends DemosListFragment {
	@Override
	public int getDemosRawRes() {
		return R.raw.libs;
	}

	public static LibsFragment newInstance() {
		return new LibsFragment();
	}
}
