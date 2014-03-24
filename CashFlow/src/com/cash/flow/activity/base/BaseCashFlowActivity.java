package com.cash.flow.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cash.flow.R;
import com.cash.flow.activity.LoginActivity;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.activity.SettingActivity;
import com.cash.flow.activity.TransactionActivity;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.listener.DialogListener;
import com.cash.flow.util.Constant;
import com.cash.flow.util.Utility;

public abstract class BaseCashFlowActivity extends BaseMyActionBarActivity implements OnClickListener{
	
	protected ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
		GlobalVar.getInstance().getClassNames().add(this.getClass().getSimpleName());
		Log.i("add", this.getClass().getSimpleName());
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		actionBar = getSupportActionBar();
    	actionBar.setDisplayShowTitleEnabled(false);
    	actionBar.setDisplayShowHomeEnabled(false);
    	actionBar.setHomeButtonEnabled(false);
    	supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
    	actionBar.hide();
		
		LinearLayout homeButton = (LinearLayout) findViewById(R.id.home_button);
		LinearLayout transactionButton = (LinearLayout) findViewById(R.id.transaction_button);
		LinearLayout settingButton = (LinearLayout) findViewById(R.id.setting_button);
		LinearLayout logoutButton = (LinearLayout) findViewById(R.id.logout_button);
		
		homeButton.setOnClickListener(this);
		transactionButton.setOnClickListener(this);
		settingButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		
		if(this instanceof MainMenuActivity) {
			homeButton.setBackgroundResource(R.drawable.blue_gradient);
		} else if(this instanceof TransactionActivity) {
			transactionButton.setBackgroundResource(R.drawable.blue_gradient);
		} else if(this instanceof SettingActivity) {
			settingButton.setBackgroundResource(R.drawable.blue_gradient);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Constant.REDIRECT_TO_HOME) {
			if(! this.getClass().getSimpleName().equals(MainMenuActivity.class.getSimpleName())) {
				setResult(Constant.REDIRECT_TO_HOME);
				finish();
			}
		} else if(resultCode==Constant.REDIRECT_TO_TRANSACTION) {
			if(! this.getClass().getSimpleName().equals(TransactionActivity.class.getSimpleName())) {
				setResult(Constant.REDIRECT_TO_TRANSACTION);
				finish();
			}
		} else if(resultCode==Constant.REDIRECT_TO_SETTING) {
			if(! this.getClass().getSimpleName().equals(SettingActivity.class.getSimpleName())) {
				setResult(Constant.REDIRECT_TO_SETTING);
				finish();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_button:
			if(this instanceof MainMenuActivity) return;
			
			setResult(Constant.REDIRECT_TO_HOME);
			finish();
			break;
		case R.id.transaction_button:
			if(this instanceof TransactionActivity) return;
			
			if(GlobalVar.getInstance().getClassNames().contains(TransactionActivity.class.getSimpleName())) {
				setResult(Constant.REDIRECT_TO_TRANSACTION);
				finish();
			} else {
				Intent intent = new Intent(this, TransactionActivity.class);
				startActivityForResult(intent, Constant.START_ACTIVITY);
			}
			break;
		case R.id.setting_button:
			if(this instanceof SettingActivity) return;
			
			if(GlobalVar.getInstance().getClassNames().contains(SettingActivity.class.getSimpleName())) {
				setResult(Constant.REDIRECT_TO_SETTING);
				finish();
			} else {
				Intent intent = new Intent(this, SettingActivity.class);
				startActivityForResult(intent, Constant.START_ACTIVITY);
			}
			break;
		case R.id.logout_button:
			Utility.showConfirmMessage(this, "Logout", "Apakah anda yakin akan logout?", new DialogListener() {
				@Override
				public void onDialogClose() {
					Intent intent = new Intent(BaseCashFlowActivity.this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}
			});
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		GlobalVar.getInstance().getClassNames().remove(this.getClass().getSimpleName());
		Log.i("remove", this.getClass().getSimpleName());
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
