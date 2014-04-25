package com.cash.flow.activity.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.util.Utility;

public class ChangePasswordActivity extends BaseMyActivity implements OnClickListener{
	
	private EditText oldPasswordEdit, passwordEdit, passwordEditRetype;
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		oldPasswordEdit = (EditText) findViewById(R.id.oldPasswordEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);
		passwordEditRetype = (EditText) findViewById(R.id.passwordEditRetype);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		
		findViewById(R.id.back_button).setOnClickListener(this);
		findViewById(R.id.button_password).setOnClickListener(this);
	}

	@Override
	public int getLayoutId() {
		return R.layout.change_password_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIdViewToInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.back_button) {
			onBackPressed();
		} else if(v.getId() == R.id.button_password) {
			String oldPassword = oldPasswordEdit.getText().toString();
			String newPassword = passwordEdit.getText().toString();
			String retypePassword = passwordEditRetype.getText().toString();
			
			if(oldPassword.trim().equals("") || newPassword.trim().equals("") || retypePassword.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_allFieldRequired));
			} else {
				if(!oldPassword.equals(GlobalVar.getInstance().getUser().getPassword())) {
					Utility.showMessage(this, "Close", getString(R.string.message_oldPasswrodWrong));
					return;
				}
				if(!newPassword.equals(retypePassword)) {
					Utility.showMessage(this, "Close", getString(R.string.message_passwordMismatch));
					return;
				}
				
				GlobalVar.getInstance().getUser().setPassword(newPassword);
				UserDao userDao = UserDao.getInstance(this);
				userDao.updateData(GlobalVar.getInstance().getUser());
				userDao.closeConnection();
				
				onBackPressed();
			}
		}
	}

}
