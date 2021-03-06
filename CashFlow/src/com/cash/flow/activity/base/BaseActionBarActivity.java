package com.cash.flow.activity.base;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cash.flow.R;
import com.cash.flow.activity.LoginActivity;
import com.cash.flow.util.ForceCloseHandler;

import android.content.Intent;
import android.os.Bundle;

public class BaseActionBarActivity extends SherlockFragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
		Thread.setDefaultUncaughtExceptionHandler(new ForceCloseHandler(this));
		super.onCreate(savedInstanceState);
		overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}
	
    long pauseTime = System.currentTimeMillis();
	
	@Override
	protected void onPause() {
		super.onPause();
		pauseTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		if (System.currentTimeMillis()-pauseTime > 10 * 60 * 1000) {
//			Intent intent = new Intent(this, StartUpActivity.class);
//			GlobalVar.getInstance().clearAllObject();
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			overridePendingTransition(0, 0);
//			finish();
//		} else {
			if(getIntent().getBooleanExtra("EXIT", false)){
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
//		}
	}

}
