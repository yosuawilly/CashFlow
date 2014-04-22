package com.cash.flow;

import com.cash.flow.activity.ActivationActivity;
import com.cash.flow.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartUpActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);
        
        Thread timer = new Thread(){
			
			@Override
			public void run() {
				try{
					int time = 0;
					while(time < 1500){
						sleep(100);
						time += 100;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				} finally {
					startActivity(new Intent(StartUpActivity.this, ActivationActivity.class));
					finish();
				}
			}
			
		};
		
		timer.start();
        
        //startActivity(new Intent(this, LoginActivity.class));
        //finish();
    }
}