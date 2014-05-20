package com.cash.flow.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowNoActionBarActivity;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.model.User;
import com.cash.flow.util.NominalFormatter;
import com.cash.flow.util.NumberUtil;
import com.cash.flow.util.Utility;

public class MainMenuActivity extends BaseCashFlowNoActionBarActivity implements OnClickListener{
	
	public static final String REFRESH_ACTION = "com.cash.flow.REFRESH_ACTION";
	
	private EditText firstFundEdit;
	
	private Handler mHandler = new Handler();
	private BroadcastReceiver cashFlowChange = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			mHandler.post(new RefreshEvent());
		}
		
	};
	
	private class RefreshEvent implements Runnable{

		@Override
		public void run() {
			User user = GlobalVar.getInstance().getUser();
			((TextView) findViewById(R.id.balanceTV))
			.setText(NumberUtil.toCurrDigitGrouping(String.valueOf(user.getBalance()), user.getCurrency()));
		}
		
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		firstFundEdit = (EditText) findViewById(R.id.firstFundEdit);
		
		User user = GlobalVar.getInstance().getUser();
		((TextView) findViewById(R.id.balanceTV))
		.setText(NumberUtil.toCurrDigitGrouping(String.valueOf(user.getBalance()), user.getCurrency()));
	}
	
	@Override
	public void initListener() {
		super.initListener();
		NominalFormatter.setTextNominalListener(firstFundEdit);
		findViewById(R.id.button_first_fund).setOnClickListener(this);
		
		registerReceiver(cashFlowChange, new IntentFilter(REFRESH_ACTION));
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.main_layout_page;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(cashFlowChange);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_first_fund) {
			String nominal = NumberUtil.normalizeNumber(firstFundEdit.getText().toString().trim());
			
			if(nominal.equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_nominalEmpty));
			} else {
				CashFlow cashFlow = new CashFlow();
				cashFlow.setNominal(Long.parseLong(nominal));
				cashFlow.setDescription(getString(R.string.default_description));
				cashFlow.setTypeCash(CashType.CASH_IN);
				
				Utility.saveCashFlow(this, cashFlow);
				
				firstFundEdit.setText("");
				
				Utility.showMessage(this, getString(R.string.message_dataSaved));
			}
		}
	}

}
