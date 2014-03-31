package com.cash.flow.activity;

import com.cash.flow.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText usernameEdit, passwordEdit;
	private Button buttonLogin, buttonForgot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		
		initializeDesign();
	}
	
	private void initializeDesign() {
		usernameEdit = (EditText) findViewById(R.id.usernameEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);
		
		buttonLogin = (Button) findViewById(R.id.button_login);
		buttonForgot = (Button) findViewById(R.id.button_forgot);
		
		buttonLogin.setOnClickListener(this);
		buttonForgot.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_login:
			Intent intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.button_forgot:
			
			break;
		default:
			break;
		}
	}

}