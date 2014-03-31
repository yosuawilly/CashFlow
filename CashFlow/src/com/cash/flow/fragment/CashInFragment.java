package com.cash.flow.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.cash.flow.R;

@SuppressLint("ValidFragment")
public class CashInFragment extends SherlockFragment{
	
	private Context context;
	private ViewGroup viewGroup;
	
	public CashInFragment(Context context) {
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(viewGroup==null) {
			viewGroup = (ViewGroup) inflater.inflate(R.layout.cash_layout_page, container, false);
		}
		
		return viewGroup;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(viewGroup!=null) {
			ViewGroup container = (ViewGroup) viewGroup.getParent();
			if(container!=null) {
				container.removeView(viewGroup);
			}
		}
	}

}
