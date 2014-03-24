package com.cash.flow.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.view.View;
import android.widget.FrameLayout;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowActivity;

public class TransactionActivity extends BaseCashFlowActivity implements ActionBar.TabListener{
	
	private String[] tabs = { "Cash In", "Cash Out", "Summary" };
	
	@Override
	public void initDesign() {
		super.initDesign();
		initLayoutHeader();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
		actionBar.show();
	}
	
	protected void initLayoutHeader() {
		View v = getLayoutInflater().inflate(R.layout.layout_header, null);
    	((FrameLayout)findViewById(android.support.v7.appcompat.R.id.activity_header)).addView(v);
    	
    	findViewById(R.id.layout_header).setVisibility(View.GONE);
    }

	@Override
	public int getIdViewToInflate() {
		return R.layout.transaction_layout_page;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
