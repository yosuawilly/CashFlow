package com.cash.flow.activity.base;

import com.cash.flow.R;
import com.cash.flow.activity.LoginActivity;
import com.cash.flow.adapter.MenuListAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class BaseListActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}
	
	protected void buildDefaultList(String[]items, int[]idIcons) {
		ListView listView = getListView();
		listView.setDivider(this.getResources().getDrawable(R.drawable.menu_list_sparator));
		//listView.setDividerHeight(1);
		
		MenuListAdapter listAdapter = new MenuListAdapter(this, items, idIcons);
		setListAdapter(listAdapter);
		
		listView.setVerticalFadingEdgeEnabled(true);
		listView.setCacheColorHint(R.color.cache_color);
		//listView.setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
	}
	
    long pauseTime = System.currentTimeMillis();
	
	@Override
	protected void onPause() {
		super.onPause();
		pauseTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		if (System.currentTimeMillis()-pauseTime > 10 * 60 * 1000) {
//			Intent intent = new Intent(this, StartUpActivity.class);
//			GlobalVar.getInstance().clearAllObject();
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			overridePendingTransition(0, 0);
//			finish();
//		} else {
			if(getIntent().getBooleanExtra("EXIT", false)){
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
//		}
	}
	
}
