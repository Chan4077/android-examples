package com.edricchan.androidexamples.ui.fragments.home;

import com.edricchan.androidexamples.DemosListFragment;
import com.edricchan.androidexamples.R;

public class ComponentsFragment extends DemosListFragment {
	@Override
	public int getDemosRawRes() {
		return R.raw.components;
	}

	public static ComponentsFragment newInstance() {
		return new ComponentsFragment();
	}
}
