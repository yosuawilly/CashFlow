package com.cash.flow.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.cash.flow.R;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.util.CashFlowUtil;
import com.cash.flow.util.NominalFormatter;
import com.cash.flow.util.NumberUtil;
import com.cash.flow.util.Utility;

@SuppressLint("ValidFragment")
public class CashOutFragment extends SherlockFragment implements OnClickListener{
	
	private Context context;
	private ViewGroup viewGroup;
	
	private EditText nominalEdit;
	private EditText descriptionEdit;
	
	public CashOutFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static CashOutFragment newInstance() {
		return new CashOutFragment();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = getActivity();
	}
	
	/*public CashOutFragment(Context context) {
		//this.context = context;
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(viewGroup==null) {
			viewGroup = (ViewGroup) inflater.inflate(R.layout.cash_layout_page, container, false);
		}
		
		return viewGroup;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		nominalEdit = (EditText) viewGroup.findViewById(R.id.nominalEdit);
		descriptionEdit = (EditText) viewGroup.findViewById(R.id.descriptionEdit);
		
		NominalFormatter.setTextNominalListener(nominalEdit);
		
		viewGroup.findViewById(R.id.button_save).setOnClickListener(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(viewGroup!=null) {
			ViewGroup container = (ViewGroup) viewGroup.getParent();
			if(container!=null) {
				container.removeView(viewGroup);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_save) {
			String nominal = NumberUtil.normalizeNumber(nominalEdit.getText().toString().trim());
			String description = descriptionEdit.getText().toString().trim();
			
			if(nominal.equals("") || description.equals("")) {
				Utility.showMessage(context, "Close", context.getString(R.string.message_allFieldRequired));
			} else {
				CashFlow cashFlow = new CashFlow();
				cashFlow.setNominal(Long.parseLong(nominal));
				cashFlow.setDescription(description);
				cashFlow.setTypeCash(CashType.CASH_OUT);
				
				if(!CashFlowUtil.isBalanceEnough(cashFlow)) {
					Utility.showMessage(context, "Close", context.getString(R.string.message_balanceNotEnough));
					return;
				}
				
				CashFlowUtil.saveCashFlow(context, cashFlow);
				
				nominalEdit.setText("");
				descriptionEdit.setText("");
				
				Utility.showMessage(context, context.getString(R.string.message_dataSaved));
			}
		}
	}

}
