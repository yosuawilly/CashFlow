package com.cash.flow.adapter;

import java.util.List;

import com.cash.flow.R;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;
import com.cash.flow.util.NumberUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CashFlowListAdapter extends ArrayAdapter<CashFlow>{
	
	public CashFlowListAdapter(Context context) {
		super(context, R.layout.cash_flow_row_component);
	}

	public CashFlowListAdapter(Context context, List<CashFlow> objects) {
		super(context, R.layout.cash_flow_row_component, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cash_flow_row_component, null);
		}
		
		CashFlow cashFlow = getItem(position);
		
		((TextView) v.findViewById(R.id.dateTV)).setText(MyCalendar.parseLocaleDate(cashFlow.getTimestamp(), Constant.FORMAT_DATE_DDMMYYYY));
		((TextView) v.findViewById(R.id.cashInTV)).setText(
				cashFlow.getTypeCash().equals(CashType.CASH_IN) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-");
		((TextView) v.findViewById(R.id.cashOutTV)).setText(
				cashFlow.getTypeCash().equals(CashType.CASH_OUT) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-");
		((TextView) v.findViewById(R.id.descriptionTV)).setText(cashFlow.getDescription());
		((TextView) v.findViewById(R.id.balanceTV)).setText(NumberUtil.toCurr2(String.valueOf(cashFlow.getBalance())));
		
		return v;
	}

}
