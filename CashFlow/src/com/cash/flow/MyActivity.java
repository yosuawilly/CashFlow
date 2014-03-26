package com.cash.flow;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cash.flow.activity.LoginActivity;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.activity.SettingActivity;
import com.cash.flow.activity.TransactionActivity;
import com.cash.flow.anotation.InitActivity;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.listener.DialogListener;
import com.cash.flow.util.Constant;
import com.cash.flow.util.Functional;
import com.cash.flow.util.Utility;

public class MyActivity<T extends Activity> implements Functional, OnClickListener{
	
	private ActionBar actionBar;
	
	private T activity;
	private InitActivity initActivity;
	
	public MyActivity(T activity, Class<?> clazz) {
		this.activity = activity;
		this.initActivity = clazz.getSuperclass().getAnnotation(InitActivity.class);
	}
	
	public void onCreate() {
		GlobalVar.getInstance().getClassNames().add(activity.getClass().getSimpleName());
		Log.i("add", activity.getClass().getSimpleName());
	}
	
	public void onDestroy() {
		GlobalVar.getInstance().getClassNames().remove(activity.getClass().getSimpleName());
		Log.i("remove", activity.getClass().getSimpleName());
	}
	
	public ActionBar getActionBar() {
		return actionBar;
	}

	@Override
	public void initBundle() {
		
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initDesign() {
		if(initActivity.withActionBar()) {
			actionBar = ((SherlockFragmentActivity)activity).getSupportActionBar();
	    	actionBar.setDisplayShowTitleEnabled(false);
	    	actionBar.setDisplayShowHomeEnabled(false);
	    	actionBar.setHomeButtonEnabled(false);
	    	actionBar.hide();
		}
		
		LinearLayout homeButton = (LinearLayout) activity.findViewById(R.id.home_button);
		LinearLayout transactionButton = (LinearLayout) activity.findViewById(R.id.transaction_button);
		LinearLayout settingButton = (LinearLayout) activity.findViewById(R.id.setting_button);
		LinearLayout logoutButton = (LinearLayout) activity.findViewById(R.id.logout_button);
		
		homeButton.setOnClickListener(this);
		transactionButton.setOnClickListener(this);
		settingButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		
		if(activity instanceof MainMenuActivity) {
			homeButton.setBackgroundResource(R.drawable.blue_gradient);
		} else if(activity instanceof TransactionActivity) {
			transactionButton.setBackgroundResource(R.drawable.blue_gradient);
		} else if(activity instanceof SettingActivity) {
			settingButton.setBackgroundResource(R.drawable.blue_gradient);
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Constant.REDIRECT_TO_HOME) {
			if(! activity.getClass().getSimpleName().equals(MainMenuActivity.class.getSimpleName())) {
				activity.setResult(Constant.REDIRECT_TO_HOME);
				activity.finish();
			}
		} else if(resultCode==Constant.REDIRECT_TO_TRANSACTION) {
			if(! activity.getClass().getSimpleName().equals(TransactionActivity.class.getSimpleName())) {
				activity.setResult(Constant.REDIRECT_TO_TRANSACTION);
				activity.finish();
			}
		} else if(resultCode==Constant.REDIRECT_TO_SETTING) {
			if(! activity.getClass().getSimpleName().equals(SettingActivity.class.getSimpleName())) {
				activity.setResult(Constant.REDIRECT_TO_SETTING);
				activity.finish();
			}
		} else if(resultCode==Constant.REDIRECT_TO_LOGIN) {
			if(! activity.getClass().equals(MainMenuActivity.class)) {
				activity.setResult(Constant.REDIRECT_TO_LOGIN);
				activity.finish();
			} else {
				Intent intent = new Intent(activity, LoginActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_button:
			if(activity instanceof MainMenuActivity) return;
			
			activity.setResult(Constant.REDIRECT_TO_HOME);
			activity.finish();
			break;
		case R.id.transaction_button:
			if(activity instanceof TransactionActivity) return;
			
			if(GlobalVar.getInstance().getClassNames().contains(TransactionActivity.class.getSimpleName())) {
				activity.setResult(Constant.REDIRECT_TO_TRANSACTION);
				activity.finish();
			} else {
				Intent intent = new Intent(activity, TransactionActivity.class);
				activity.startActivityForResult(intent, Constant.START_ACTIVITY);
			}
			break;
		case R.id.setting_button:
			if(activity instanceof SettingActivity) return;
			
			if(GlobalVar.getInstance().getClassNames().contains(SettingActivity.class.getSimpleName())) {
				activity.setResult(Constant.REDIRECT_TO_SETTING);
				activity.finish();
			} else {
				Intent intent = new Intent(activity, SettingActivity.class);
				activity.startActivityForResult(intent, Constant.START_ACTIVITY);
			}
			break;
		case R.id.logout_button:
			Utility.showConfirmMessage(activity, "Logout", "Apakah anda yakin akan logout?", new DialogListener() {
				@Override
				public void onDialogClose() {
					if(activity.getClass() == MainMenuActivity.class) {
						Intent intent = new Intent(activity, LoginActivity.class);
						activity.startActivity(intent);
						activity.finish();
					} else {
						activity.setResult(Constant.REDIRECT_TO_LOGIN);
						activity.finish();
					}
				}
			});
			break;
		default:
			break;
		}
	}

}
