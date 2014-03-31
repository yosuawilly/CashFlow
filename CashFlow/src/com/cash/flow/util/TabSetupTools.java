package com.cash.flow.util;

import com.cash.flow.R;
import com.cash.flow.activity.EmptyActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class TabSetupTools {
	
	private Activity activity;
	private int idTabHost;
	private String[]tabSpec;
	
	private TabHost tabHost;
	
	private LocalActivityManager mLocalActivityManager;
	
	public TabSetupTools(Activity activity, Bundle savedInstanceState, int idTabHost, String[]tabSpec) {
		this.activity = activity;
		this.idTabHost = idTabHost;
		this.tabSpec = tabSpec;
		
		mLocalActivityManager = new LocalActivityManager(activity, false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
	}
	
	public void generateTabs() {
		tabHost = (TabHost) activity.findViewById(idTabHost);
		tabHost.setup(mLocalActivityManager);
		//tabHost.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);
		//tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
		//tabHost.getTabWidget().setDividerPadding(3);
		
		//TabHost.TabSpec spec;
		Intent intent;
		
		for(String specStr : tabSpec) {
			intent = new Intent().setClass(activity, EmptyActivity.class);
			setupTab(intent, specStr, 0);
		}

	}
	
	private void setupTab(final Intent intent, final String tabSpecStr, final int backgroundResource) {
		View tabview = createTabView(tabHost.getContext(), backgroundResource, tabSpecStr);
		TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabSpecStr).setIndicator(tabview).setContent(intent);
        tabHost.addTab(tabSpec);
	}

	private View createTabView(final Context context, final int backgroundResource, String tabSpec) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_component, null);
		TextView tabsText = (TextView) view.findViewById(R.id.tabLabel);
		tabsText.setText(tabSpec);
		if(!tabSpec.equals(this.tabSpec[this.tabSpec.length-1])) {
			view.findViewById(R.id.tabSplitter).setVisibility(View.VISIBLE);
		}
//		ImageView image = (ImageView) view.findViewById(R.id.tabsImages);
//		image.setBackgroundResource(backgroundResource);
		return view;
	}


}
