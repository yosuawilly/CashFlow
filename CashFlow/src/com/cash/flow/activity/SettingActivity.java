package com.cash.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowListActivity;
import com.cash.flow.activity.setting.ChangePasswordActivity;
import com.cash.flow.activity.setting.SetMarginActivity;
import com.cash.flow.adapter.MenuListAdapter;
import com.cash.flow.util.Constant;

public class SettingActivity extends BaseCashFlowListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		buildDefaultList(new String[]{
				"Change Password",
				"Set Margin",
				"Currency"
		}, new int[]{
				R.drawable.change_password_icon,
				R.drawable.coins,
				R.drawable.dollars
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MenuListAdapter adapter = (MenuListAdapter) l.getAdapter();
		String item = adapter.getItem(position);
		if("Change Password".equalsIgnoreCase(item)) {
			Intent intent = new Intent(this, ChangePasswordActivity.class);
			startActivityForResult(intent, Constant.START_ACTIVITY);
		} else if("Set Margin".equals(item)) {
			Intent intent = new Intent(this, SetMarginActivity.class);
			startActivityForResult(intent, Constant.START_ACTIVITY);
		} else if("Currency".equals(item)) {
			
		}
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.setting_layout_page;
	}

}
