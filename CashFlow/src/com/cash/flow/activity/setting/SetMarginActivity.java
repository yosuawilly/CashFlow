package com.cash.flow.activity.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;
import com.cash.flow.customcomponent.CustomKeyBoard;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.util.NominalFormatter;
import com.cash.flow.util.NumberUtil;

public class SetMarginActivity extends BaseMyActivity implements OnClickListener{
	
	private EditText marginEdit;
	private CustomKeyBoard customKeyBoard;
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.back_button)).setOnClickListener(this);
		
		marginEdit = (EditText) findViewById(R.id.marginEdit);
		customKeyBoard = (CustomKeyBoard) findViewById(R.id.customKeyboard);
		
		customKeyBoard.initCustomKeyboard(marginEdit, String.valueOf(GlobalVar.getInstance().getUser().getMargin()));
		NominalFormatter.setTextNominalListener(marginEdit);
	}
	
	@Override
	public void initObjectToDesign() {
		super.initObjectToDesign();
		
		String margin = NumberUtil.toCurr2(String.valueOf(GlobalVar.getInstance().getUser().getMargin()));
		marginEdit.setText(margin);
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
			String margin = NumberUtil.normalizeNumber(marginEdit.getText().toString().trim());
			if(!margin.equals("")) {
				GlobalVar.getInstance().getUser().setMargin(Long.valueOf(margin));
				
				UserDao userDao = UserDao.getInstance(this);
				userDao.updateData(GlobalVar.getInstance().getUser());
				
				userDao.closeConnection();
			}
			
			onBackPressed();
		}
	}

}
