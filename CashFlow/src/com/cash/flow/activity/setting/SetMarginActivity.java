package com.cash.flow.activity.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;
import com.cash.flow.customcomponent.CustomKeyBoard;

public class SetMarginActivity extends BaseMyActivity implements OnClickListener{
	
	private EditText marginEdit;
	private CustomKeyBoard customKeyBoard;
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.back_button)).setOnClickListener(this);
		
		marginEdit = (EditText) findViewById(R.id.marginEdit);
		customKeyBoard = (CustomKeyBoard) findViewById(R.id.customKeyboard);
		
		customKeyBoard.initCustomKeyboard(marginEdit);
	}

	@Override
	public int getLayoutId() {
		return R.layout.set_margin_layout_page;
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
