package com.cash.flow.activity.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;

public class ChangePasswordActivity extends BaseMyActivity implements OnClickListener{
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.back_button)).setOnClickListener(this);
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
		}
	}

}
