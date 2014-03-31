package com.cash.flow.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowActivity;
import com.cash.flow.adapter.FragmentAdapter;
import com.cash.flow.customcomponent.CustomViewPager;

public class TransactionActivity extends BaseCashFlowActivity implements ActionBar.TabListener, OnPageChangeListener{
	
	private String[] tabs = { "Cash In", "Cash Out", "Summary" };
	
	private CustomViewPager viewPager;
	private FragmentAdapter fragmentAdapter;
	
	@Override
	public void initObject() {
		super.initObject();
		fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		initLayoutHeader();
		
		/*init ViewPager*/
		viewPager = (CustomViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(this);
		viewPager.setEnableSwipe(true);
		viewPager.setAdapter(fragmentAdapter);
		
		/*init actionbar*/
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
//		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
//		ViewGroup child = (ViewGroup) decor.getChildAt(0);
//		child.addView(v, 0);
		
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		ViewGroup child = (ViewGroup) decor.getChildAt(0);
		if(child instanceof FrameLayout || FrameLayout.class.isAssignableFrom(child.getClass()) ){
			while(true) {
				child = (ViewGroup) child.getChildAt(0);
				if( ! (child instanceof FrameLayout) || 
						!FrameLayout.class.isAssignableFrom(child.getClass()) ) {
					child.addView(v, 0);
					Log.i("child in", "added header child count "+child.getChildCount());
					if(child instanceof LinearLayout) Log.i("linear", "true");
					break;
				}
			}
		} else {
			if(android.os.Build.VERSION.SDK_INT == 19) {
				//child.addView(v, 0);
				ViewGroup cont = (ViewGroup) child.getChildAt(1);
				ViewGroup contextBar = (ViewGroup) cont.getChildAt(1);
//				ViewGroup tab = (ViewGroup) cont.getChildAt(2);
//				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tab.getLayoutParams());
//				params.setMargins(0, 30, 0, 0);
//				tab.setLayoutParams(params);
				contextBar.addView(v);
//				LinearLayout lin = (LinearLayout) ((ViewGroup)cont.getChildAt(0)).getChildAt(0);
//				LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
//				par.gravity = Gravity.CENTER;
//				lin.addView(v, par);
//				lin.setLayoutParams(par);
				//printChild(cont, 1);
			} else {
				child.addView(v, 0);
			}
		}
		
    	findViewById(R.id.layout_header).setVisibility(View.GONE);
    }
	
	public void printChild(ViewGroup child, int index) {
		if(child.getChildCount() > 0) {
			for(int i=0;i<child.getChildCount();i++) {
				try {
					ViewGroup ch = (ViewGroup) child.getChildAt(i);
					Log.i(String.valueOf(index), ch.toString());
					if(ch.getChildCount() > 0) printChild(ch, index+1);
				}catch(Exception e){
					View v = child.getChildAt(i);
					Log.i(String.valueOf(index), v.toString());
				}
			}
		}
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.transaction_layout_page;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		viewPager.setCurrentItem(position);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		getMyActionBar().setSelectedNavigationItem(position);
	}

}
