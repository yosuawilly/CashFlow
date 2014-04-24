package com.cash.flow.activity;

import com.cash.flow.R;
import com.cash.flow.activity.setting.ForgotPasswordActivity;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.model.User;
import com.cash.flow.util.Constant;
import com.cash.flow.util.Utility;

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
			String username = usernameEdit.getText().toString();
			String password = passwordEdit.getText().toString();
			
			if(username.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_usernameEmpty));
			} else if(password.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_passwordEmpty));
			} else {
				UserDao userDao = UserDao.getInstance(this);
				User user = userDao.findUser(username);
				if(user == null) {
					Utility.showMessage(this, "Close", getString(R.string.message_usernamePasswordWrong));
					return;
				}
				if(!user.getPassword().equals(password)) {
					Utility.showMessage(this, "Close", getString(R.string.message_usernamePasswordWrong));
					return;
				}
				
				GlobalVar.getInstance().setUser(user);
				Intent intent = new Intent(this, MainMenuActivity.class);
				startActivity(intent);
				finish();
			}
			
			break;
		case R.id.button_forgot:
			Intent intent = new Intent(this, ForgotPasswordActivity.class);
			startActivityForResult(intent, Constant.START_ACTIVITY);
			break;
		default:
			break;
		}
	}

}
