package com.cash.flow.activity.base;

import com.cash.flow.util.Functional;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public abstract class BaseMyActivity extends BaseActivity implements Functional{
	protected boolean inflateView = false;
	
	protected MyLayout myLayout = MyLayout.LINEARLAYOUT;
	
	public enum MyLayout {
		LINEARLAYOUT, RELATIVELAYOUT
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		
		initBundle();
		initObject();
		initDesign();
		initObjectToDesign();
		initListener();
	}
	
	public void setLayoutMode(boolean inflateView, MyLayout myLayout){
		this.inflateView = inflateView;
		this.myLayout = myLayout;
	}

	@Override
	public void initBundle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDesign() {
		if(inflateView) {
			if(myLayout == MyLayout.LINEARLAYOUT) {
				LinearLayout base = (LinearLayout) findViewById(getIdViewToAppendFromInflate());
				if(base != null){
					View toInflate = getLayoutInflater().inflate(getIdViewToInflate(), null);
					base.addView(toInflate);
				}
			} else if(myLayout == MyLayout.RELATIVELAYOUT) {
				RelativeLayout base = (RelativeLayout) findViewById(getIdViewToAppendFromInflate());
				if(base != null){
					View toInflate = getLayoutInflater().inflate(getIdViewToInflate(), null);
					base.addView(toInflate);
				}
			}
		}
	}

	@Override
	public void initObjectToDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract int getLayoutId();
	
	public abstract int getIdViewToAppendFromInflate();
	
	public abstract int getIdViewToInflate();

}
