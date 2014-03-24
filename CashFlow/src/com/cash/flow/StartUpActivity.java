package com.cash.flow;

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
        
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}