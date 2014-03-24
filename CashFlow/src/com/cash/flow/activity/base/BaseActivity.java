package com.cash.flow.activity.base;

import com.cash.flow.R;
import com.cash.flow.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
