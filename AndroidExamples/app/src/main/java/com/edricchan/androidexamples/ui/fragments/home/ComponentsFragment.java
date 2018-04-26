package com.edricchan.androidexamples.ui.fragments.home;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edricchan.androidexamples.R;
import com.edricchan.androidexamples.adapter.ItemAdapter;
import com.edricchan.androidexamples.model.data.ChildItem;
import com.edricchan.androidexamples.model.data.ParentItem;
import com.edricchan.androidexamples.ui.components.ComponentsFlatButtonActivity;
import com.edricchan.androidexamples.ui.data.IViewType;
import com.edricchan.androidexamples.ui.data.ItemDisplayable;
import com.edricchan.androidexamples.ui.data.ItemID;
import com.edricchan.androidexamples.ui.data.TitleDisplayable;
import com.edricchan.androidexamples.utils.IOUtils;
import com.edricchan.androidexamples.utils.SharedUtils;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ComponentsFragment extends Fragment {
	private ItemAdapter mAdapter;
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_components_list, container, false);
		initRecycleView();
		loadData();
		return rootView;
	}
	public static ComponentsFragment newInstance() {
		return new ComponentsFragment();
	}
	private void initRecycleView() {
		mAdapter = new ItemAdapter();
		mAdapter.setListener(new ItemAdapter.Listener() {
			@Override
			public void onDocumentClicked(ItemDisplayable displayable) {
				handleDocumentClick(displayable);
			}
		});

		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.componentsRecyclerView);
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
				.subscribe(new Action1<List<IViewType>>() {
					@Override
					public void call(List<IViewType> dataList) {
						mAdapter.setData(removeEmptyItems(dataList));
						mAdapter.notifyDataSetChanged();
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable e) {
						Log.d("ERROR", "Error during loading json data list", e);
					}
				});
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
		return new Observable.OnSubscribe<List<ParentItem>>() {
			@Override
			public void call(Subscriber<? super List<ParentItem>> subscriber) {
				try {
					InputStream inputStream = getResources().openRawResource(R.raw.components);
					String json = IOUtils.toString(inputStream);
					Gson gson = new Gson();
					ParentItem[] parentItemArr = gson.fromJson(json, ParentItem[].class);

					subscriber.onNext(Arrays.asList(parentItemArr));
					subscriber.onCompleted();
				} catch (Resources.NotFoundException e) {
					subscriber.onError(e);
				}
			}
		};
	}

	@NonNull
	private Func1<List<ParentItem>, List<IViewType>> toDisplayableList() {
		return new Func1<List<ParentItem>, List<IViewType>>() {
			@Override
			public List<IViewType> call(List<ParentItem> parentItemList) {
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
			}
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
		SharedUtils.startExample(getActivity(), displayable, getActivity().findViewById(R.id.homeContent));
		/*
		switch (documentId) {

			//buttons
			case ItemID.COMPONENTS_RAISED_BUTTON:
//				RaisedButtonActivity.start(this);
				break;
			case ItemID.COMPONENTS_FLAT_BUTTON:
				ComponentsFlatButtonActivity.start(getActivity());
				break;
			case ItemID.COMPONENTS_FLOATING_ACTION_BUTTON:
//				FloatingButtonActivity.start(this);
				break;
			case ItemID.COMPONENTS_BUTTONS_ONCLICK:

				break;
			//chips
			case ItemID.COMPONENTS_CHIP:
				break;

			//dialog
			case ItemID.COMPONENTS_ALERT_DIALOGS:
//				AlertDialogActivity.start(this);
				break;
			case ItemID.COMPONENTS_CONFIRMATION_DIALOGS:
//				ConfirmationDialogActivity.start(this);
				break;
			case ItemID.COMPONENTS_FULL_SCREEN_DIALOGS:
				break;

			//menu
			case ItemID.COMPONENTS_MENU:
//				MenuActivity.start(this);
				break;
			case ItemID.COMPONENTS_MENU_STYLED:
//				StyledMenuActivity.start(this);
				break;
			case ItemID.COMPONENTS_CONTEXT_MENU:

				break;

			//pickers
			case ItemID.COMPONENTS_DATE_PICKER:
//				DatePickerActivity.start(this);
				break;
			case ItemID.COMPONENTS_TIME_PICKER:
//				TimePickerActivity.start(this);
				break;

			//progress
			case ItemID.COMPONENTS_CIRCULAR_PROGRESS:
//				CircularProgressActivity.start(this);
				break;
			case ItemID.COMPONENTS_LINEAR_PROGRESS:
//				LinearProgressActivity.start(this);
				break;

			//selection controls
			case ItemID.COMPONENTS_CHECK_BOX:
//				CheckBoxActivity.start(this);
				break;
			case ItemID.COMPONENTS_RADIO_BUTTON:
//				RadioButtonActivity.start(this);
				break;
			case ItemID.COMPONENTS_SWITCH:
//				SwitchActivity.start(this);
				break;

			//sliders
			case ItemID.COMPONENTS_SLIDER:
				break;

			//message alerts
			case ItemID.COMPONENTS_SNACK_BAR:
//				SnackBarActivity.start(this);
				break;
			case ItemID.COMPONENTS_TOAST:
//				ToastActivity.start(this);
				break;

			//tabs
			case ItemID.COMPONENTS_TABS_TEXT_ONLY:
//				TabActivity.start(this);
				break;
			case ItemID.COMPONENTS_TABS_ICON_ONLY:
//				TabIconActivity.start(this);
				break;
			case ItemID.COMPONENTS_TABS_ICON_AND_TEXT:
//				TabMultiActivity.start(this);
				break;
			case ItemID.COMPONENTS_TABS_STYLED:
//				TabStyledActivity.start(this);
				break;

			//text field
			case ItemID.COMPONENTS_INPUT_FIELD:
//				InputActivity.start(this);
				break;
			case ItemID.COMPONENTS_INPUT_FIELD_SINGLE_LINE:
//				InputSingleLineActivity.start(this);
				break;
			case ItemID.COMPONENTS_INPUT_FIELD_MULTI_LINE:
//				InputMultiLineActivity.start(this);
				break;
			case ItemID.COMPONENTS_INPUT_FIELD_FULL_WIDTH:
//				InputFullWidthActivity.start(this);
				break;
			case ItemID.COMPONENTS_INPUT_FIELD_FLOATING_LABEL:
//				InputFloatingLabelActivity.start(this);
				break;
			case ItemID.COMPONENTS_INPUT_FIELD_ERROR_LABEL:
//				InputErrorLabelActivity.start(this);
				break;

			//toolbars
			case ItemID.COMPONENTS_TOOLBAR_DEFAULT:
//				DefaultToolbarActivity.start(this);
				break;
			case ItemID.COMPONENTS_TOOLBAR_STYLED:
//				StyledToolbarActivity.start(this);
				break;
			case ItemID.COMPONENTS_TOOLBAR_ICONS:
//				IconsToolbarActivity.start(this);
				break;
			case ItemID.COMPONENTS_TOOLBAR_BACK:
//				BackToolbarActivity.start(this);
				break;
			case ItemID.COMPONENTS_TOOLBAR_MORE_MENU:
//				BlankToolbarActivity.start(this);
				break;

			//other
			case ItemID.COMPONENTS_RATING_BAR:
//				RatingBarActivity.start(this);
				break;
		}
		*/
	}
}
