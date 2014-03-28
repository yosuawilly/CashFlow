package com.cash.flow.activity;

import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowActivity;

public class TransactionActivity extends BaseCashFlowActivity implements ActionBar.TabListener{
	
	private String[] tabs = { "Cash In", "Cash Out", "Summary" };
	
	@Override
	public void initDesign() {
		super.initDesign();
		initLayoutHeader();
		
		getMyActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tab_name : tabs) {
			getMyActionBar().addTab(getMyActionBar().newTab().setText(tab_name)
                    .setTabListener(this));
        }
		getMyActionBar().show();
		
	}
	
	protected void initLayoutHeader() {
		LinearLayout v = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_header, null);
    	//((FrameLayout)findViewById(com.actionbarsherlock.R.id.activity_header)).addView(v);
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		ViewGroup child = (ViewGroup) decor.getChildAt(0);
		child.addView(v, 0);
		
    	findViewById(R.id.layout_header).setVisibility(View.GONE);
    }

	@Override
	public int getIdViewToInflate() {
		return R.layout.transaction_layout_page;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
