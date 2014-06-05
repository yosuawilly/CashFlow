package com.cash.flow.adapter;

import com.cash.flow.fragment.CashInFragment;
import com.cash.flow.fragment.CashOutFragment;
import com.cash.flow.fragment.SummaryFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{
	
	//private Context context;

	private CashInFragment cashInFragment;
	private CashOutFragment cashOutFragment;
	private SummaryFragment summaryFragment;
	
	public FragmentAdapter(Context context, FragmentManager fm) {
		super(fm);
		//this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		switch (position) {
		case 0:
			if(cashInFragment==null) {
				cashInFragment = new CashInFragment();
			}
			
			fragment = cashInFragment;
			break;
		case 1:
			if(cashOutFragment==null) {
				cashOutFragment = new CashOutFragment();
			}
			
			fragment = cashOutFragment;
			break;
		case 2:
			if(summaryFragment==null) {
				summaryFragment = new SummaryFragment();
			}
			
			fragment = summaryFragment;
			break;
		default:
			break;
		}
		
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
