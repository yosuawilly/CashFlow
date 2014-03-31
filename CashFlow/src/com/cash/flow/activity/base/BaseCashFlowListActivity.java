package com.cash.flow.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.cash.flow.MyActivity;
import com.cash.flow.R;
import com.cash.flow.anotation.InitActivity;

@InitActivity(withActionBar=false)
public abstract class BaseCashFlowListActivity extends BaseMyListActivity{
	
	public MyActivity<BaseCashFlowListActivity> myActivity = new MyActivity<BaseCashFlowListActivity>(this, this.getClass());;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		myActivity.onCreate();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myActivity.onDestroy();
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		myActivity.initDesign();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		myActivity.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public int getLayoutId() {
		return R.layout.base_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		return R.id.mainLinearLayout;
	}

}
