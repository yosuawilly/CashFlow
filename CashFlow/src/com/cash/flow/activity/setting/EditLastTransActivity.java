package com.cash.flow.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;
import com.cash.flow.listener.DialogListener;
import com.cash.flow.model.CashFlow;
import com.cash.flow.util.CashFlowUtil;
import com.cash.flow.util.NominalFormatter;
import com.cash.flow.util.NumberUtil;
import com.cash.flow.util.Utility;

public class EditLastTransActivity extends BaseMyActivity implements OnClickListener{
	
	private CashFlow lastCash;
	private EditText nominalEdit, descriptionEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initBundle() {
		super.initBundle();
		if(getIntent().getExtras() != null) {
			lastCash = (CashFlow) getIntent().getSerializableExtra("cashflow");
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		findViewById(R.id.mainFooterLayout).setVisibility(View.GONE);
		Button backButton = (Button) findViewById(R.id.back_button);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(this);
		
		nominalEdit = (EditText) findViewById(R.id.nominalEdit);
		descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
		
		NominalFormatter.setTextNominalListener(nominalEdit);
		
		if(lastCash != null) {
			((TextView) findViewById(R.id.typeCashFlowTV)).setText(lastCash.getTypeCash().toString());
			nominalEdit.setText(String.valueOf(lastCash.getNominal()));
			descriptionEdit.setText(lastCash.getDescription());
		}
		
		findViewById(R.id.button_save).setOnClickListener(this);
	}

	@Override
	public int getLayoutId() {
		return R.layout.base_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		return R.id.mainLinearLayout;
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.edit_last_trans_layout_page;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.back_button) {
			onBackPressed();
		} else if(v.getId() == R.id.button_save) {
			if(lastCash != null) {
				String nominal = NumberUtil.normalizeNumber(nominalEdit.getText().toString().trim());
				String description = descriptionEdit.getText().toString().trim();
				
				if(nominal.equals("") || description.equals("")) {
					Utility.showMessage(this, "Close", getString(R.string.message_allFieldRequired));
				} else {
					lastCash.setNominal(Long.valueOf(nominal));
					lastCash.setDescription(description);
					
					CashFlowUtil.updateCashFlow(this, lastCash);
					
					Utility.showMessage(this, "Close", getString(R.string.message_dataSaved), new DialogListener() {
						@Override
						public void onDialogClose() {
							onBackPressed();
						}
					});
				}
			}
		}
	}

}
