package com.edricchan.androidexamples.ui.fragments.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edricchan.androidexamples.R;
import com.edricchan.androidexamples.adapter.ItemAdapter;
import com.edricchan.androidexamples.model.data.ChildItem;
import com.edricchan.androidexamples.model.data.ParentItem;
import com.edricchan.androidexamples.ui.data.IViewType;
import com.edricchan.androidexamples.ui.data.ItemDisplayable;
import com.edricchan.androidexamples.ui.data.TitleDisplayable;
import com.edricchan.androidexamples.utils.IOUtils;
import com.edricchan.androidexamples.utils.SharedUtils;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LibsFragment extends Fragment {
	private ItemAdapter mAdapter;
	private View rootView;
	private final String TAG = LibsFragment.class.getSimpleName();

	public static LibsFragment newInstance() {
		return new LibsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_libs_list, container, false);
		initRecycleView();
		loadData();
		return rootView;
	}

	private void initRecycleView() {
		mAdapter = new ItemAdapter();
		mAdapter.setListener(this::handleDocumentClick);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		RecyclerView recyclerView = rootView.findViewById(R.id.libsRecyclerView);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(mAdapter);
	}

	private void loadData() {
		Observable.create(loadDataFromAssets())
				.map(toDisplayableList())
				.subscribeOn(Schedulers.computation())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataList -> {
					mAdapter.setData(removeEmptyItems(dataList));
					mAdapter.notifyDataSetChanged();
				}, e -> Log.e(TAG, "Error when loading JSON data list", e));
	}

	@NonNull
	private List<? extends IViewType> removeEmptyItems(@NonNull List<IViewType> dataList) {
		List<IViewType> resultList = new ArrayList<>();
		for (IViewType type : dataList) {

			if (type instanceof TitleDisplayable && !TextUtils.isEmpty(((TitleDisplayable) type).getTitle())) {
				resultList.add(type);

			} else if (type instanceof ItemDisplayable &&
					!TextUtils.isEmpty(((ItemDisplayable) type).getTitle()) &&
					!TextUtils.isEmpty(((ItemDisplayable) type).getDescription())) {
				resultList.add(type);
			}
		}

		return resultList;
	}

	@NonNull
	private Observable.OnSubscribe<List<ParentItem>> loadDataFromAssets() {
		return subscriber -> {
			try {
				InputStream inputStream = getResources().openRawResource(R.raw.libs);
				String json = IOUtils.toString(inputStream);
				Gson gson = new Gson();
				ParentItem[] parentItemArr = gson.fromJson(json, ParentItem[].class);

				subscriber.onNext(Arrays.asList(parentItemArr));
				subscriber.onCompleted();
			} catch (Resources.NotFoundException e) {
				subscriber.onError(e);
			}
		};
	}

	@NonNull
	private Func1<List<ParentItem>, List<IViewType>> toDisplayableList() {
		return parentItemList -> {
			List<IViewType> typeList = new ArrayList<>();
			for (ParentItem parentItem : parentItemList) {
				typeList.add(new TitleDisplayable(parentItem.title));
				if (parentItem.itemsList == null) {
					continue;
				}

				for (ChildItem item : parentItem.itemsList) {
					typeList.add(toDisplayable(item));
				}
			}

			return typeList;
		};
	}

	@NonNull
	private ItemDisplayable toDisplayable(ChildItem item) {
		ItemDisplayable displayable = new ItemDisplayable();
		displayable.setId(item.id);
		displayable.setTitle(item.title);
		displayable.setDescription(item.description);
		return displayable;
	}

	private void handleDocumentClick(ItemDisplayable displayable) {
		SharedUtils.startExample(getActivity(), displayable, getActivity().findViewById(R.id.coordinatorLayoutContainer));
	}
}
