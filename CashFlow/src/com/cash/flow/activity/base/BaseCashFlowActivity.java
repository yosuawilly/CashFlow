package com.cash.flow.activity.base;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.cash.flow.MyActivity;
import com.cash.flow.R;
import com.cash.flow.anotation.InitActivity;

@InitActivity(withActionBar=true)
public abstract class BaseCashFlowActivity extends BaseMyActionBarActivity{
	
    public MyActivity<BaseCashFlowActivity> myActivity = new MyActivity<BaseCashFlowActivity>(this, this.getClass());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	
	public ActionBar getMyActionBar() {
		return myActivity.getActionBar();
	}
	
//	protected ActionBar actionBar;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		setLayoutMode(true, MyLayout.LINEARLAYOUT);
//		super.onCreate(savedInstanceState);
//		GlobalVar.getInstance().getClassNames().add(this.getClass().getSimpleName());
//		Log.i("add", this.getClass().getSimpleName());
//	}
//	
//	@Override
//	public void initDesign() {
//		super.initDesign();
//		
//		actionBar = getSupportActionBar();
//    	actionBar.setDisplayShowTitleEnabled(false);
//    	actionBar.setDisplayShowHomeEnabled(false);
//    	actionBar.setHomeButtonEnabled(false);
//    	actionBar.hide();
//		
//		LinearLayout homeButton = (LinearLayout) findViewById(R.id.home_button);
//		LinearLayout transactionButton = (LinearLayout) findViewById(R.id.transaction_button);
//		LinearLayout settingButton = (LinearLayout) findViewById(R.id.setting_button);
//		LinearLayout logoutButton = (LinearLayout) findViewById(R.id.logout_button);
//		
//		homeButton.setOnClickListener(this);
//		transactionButton.setOnClickListener(this);
//		settingButton.setOnClickListener(this);
//		logoutButton.setOnClickListener(this);
//		
//		if(this instanceof MainMenuActivity) {
//			homeButton.setBackgroundResource(R.drawable.blue_gradient);
//		} else if(this instanceof TransactionActivity) {
//			transactionButton.setBackgroundResource(R.drawable.blue_gradient);
//		} else if(this instanceof SettingActivity) {
//			settingButton.setBackgroundResource(R.drawable.blue_gradient);
//		}
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode==Constant.REDIRECT_TO_HOME) {
//			if(! this.getClass().getSimpleName().equals(MainMenuActivity.class.getSimpleName())) {
//				setResult(Constant.REDIRECT_TO_HOME);
//				finish();
//			}
//		} else if(resultCode==Constant.REDIRECT_TO_TRANSACTION) {
//			if(! this.getClass().getSimpleName().equals(TransactionActivity.class.getSimpleName())) {
//				setResult(Constant.REDIRECT_TO_TRANSACTION);
//				finish();
//			}
//		} else if(resultCode==Constant.REDIRECT_TO_SETTING) {
//			if(! this.getClass().getSimpleName().equals(SettingActivity.class.getSimpleName())) {
//				setResult(Constant.REDIRECT_TO_SETTING);
//				finish();
//			}
//		} else if(resultCode==Constant.REDIRECT_TO_LOGIN) {
//			if(! this.getClass().equals(MainMenuActivity.class)) {
//				setResult(Constant.REDIRECT_TO_LOGIN);
//				finish();
//			} else {
//				Intent intent = new Intent(BaseCashFlowActivity.this, LoginActivity.class);
//				startActivity(intent);
//				finish();
//			}
//		}
//	}
//	
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.home_button:
//			if(this instanceof MainMenuActivity) return;
//			
//			setResult(Constant.REDIRECT_TO_HOME);
//			finish();
//			break;
//		case R.id.transaction_button:
//			if(this instanceof TransactionActivity) return;
//			
//			if(GlobalVar.getInstance().getClassNames().contains(TransactionActivity.class.getSimpleName())) {
//				setResult(Constant.REDIRECT_TO_TRANSACTION);
//				finish();
//			} else {
//				Intent intent = new Intent(this, TransactionActivity.class);
//				startActivityForResult(intent, Constant.START_ACTIVITY);
//			}
//			break;
//		case R.id.setting_button:
//			if(this instanceof SettingActivity) return;
//			
//			if(GlobalVar.getInstance().getClassNames().contains(SettingActivity.class.getSimpleName())) {
//				setResult(Constant.REDIRECT_TO_SETTING);
//				finish();
//			} else {
//				Intent intent = new Intent(this, SettingActivity.class);
//				startActivityForResult(intent, Constant.START_ACTIVITY);
//			}
//			break;
//		case R.id.logout_button:
//			Utility.showConfirmMessage(this, "Logout", "Apakah anda yakin akan logout?", new DialogListener() {
//				@Override
//				public void onDialogClose() {
////					Intent intent = new Intent(BaseCashFlowActivity.this, LoginActivity.class);
////					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
////					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					startActivity(intent);
//					if(BaseCashFlowActivity.this.getClass() == MainMenuActivity.class) {
//						Intent intent = new Intent(BaseCashFlowActivity.this, LoginActivity.class);
//						startActivity(intent);
//						finish();
//					} else {
//						setResult(Constant.REDIRECT_TO_LOGIN);
//						finish();
//					}
//				}
//			});
//			break;
//		default:
//			break;
//		}
//	}
//	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		GlobalVar.getInstance().getClassNames().remove(this.getClass().getSimpleName());
//		Log.i("remove", this.getClass().getSimpleName());
//	}
//
//	@Override
//	public int getLayoutId() {
//		return R.layout.base_page;
//	}
//
//	@Override
//	public int getIdViewToAppendFromInflate() {
//		return R.id.mainLinearLayout;
//	}

}
