package com.cash.flow.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cash.flow.R;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;
import com.cash.flow.util.Utility;

@SuppressLint("ValidFragment")
public class SummaryFragment extends SherlockFragment implements OnClickListener{
	
	private Context context;
	private ViewGroup viewGroup;
	
	private Spinner spinnerType, spinnerMonth;
	
	private ArrayAdapter<String> typeAdapter, monthAdapter;
	
	private LinearLayout weeklyLayout, monthlyLayout, onlyMonthLayout;
	
	private TextView toDateTV, fromDateTV;
	
	public SummaryFragment(Context context) {
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(viewGroup==null) {
			viewGroup = (ViewGroup) inflater.inflate(R.layout.summary_layout_page, container, false);
		}
		
		return viewGroup;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		spinnerType = (Spinner) viewGroup.findViewById(R.id.spinnerType);
		spinnerMonth = (Spinner) viewGroup.findViewById(R.id.spinnerMonth);
		
		weeklyLayout = (LinearLayout) viewGroup.findViewById(R.id.weeklyLayout);
		monthlyLayout = (LinearLayout) viewGroup.findViewById(R.id.monthlyLayout);
		onlyMonthLayout = (LinearLayout) viewGroup.findViewById(R.id.onlyMonthLayout);
		
		fromDateTV = (TextView) weeklyLayout.findViewById(R.id.fromDateTV);
		toDateTV = (TextView) weeklyLayout.findViewById(R.id.toDateTV);
		
		typeAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_component, 
				getResources().getStringArray(R.array.report_type));
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerType.setAdapter(typeAdapter);
		
		monthAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_component, 
				getResources().getStringArray(R.array.month));
		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMonth.setAdapter(monthAdapter);
		
		initializeListener();
	}
	
	private void initializeListener() {
		spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				if(position == 0) {
					weeklyLayout.setVisibility(View.VISIBLE);
					monthlyLayout.setVisibility(View.GONE);
				} else if(position == 1) {
					monthlyLayout.setVisibility(View.VISIBLE);
					weeklyLayout.setVisibility(View.GONE);
					onlyMonthLayout.setVisibility(View.VISIBLE);
				} else if(position == 2) {
					monthlyLayout.setVisibility(View.VISIBLE);
					weeklyLayout.setVisibility(View.GONE);
					onlyMonthLayout.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				// TODO Auto-generated method stub
				
			}
		});
		
		viewGroup.findViewById(R.id.pickFromDate).setOnClickListener(this);
		viewGroup.findViewById(R.id.pickToDate).setOnClickListener(this);
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
		switch (v.getId()) {
		case R.id.pickFromDate:
			Utility.ShowDatePicker(context, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker dp, int year, int month, int day) {
					fromDateTV.setText(MyCalendar.parseLocaleDate(year, month, day, Constant.FORMAT_DATE_DDMMMMYYYY));
				}
			});
			break;
		case R.id.pickToDate:
            Utility.ShowDatePicker(context, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker dp, int year, int month, int day) {
					toDateTV.setText(MyCalendar.parseLocaleDate(year, month, day, Constant.FORMAT_DATE_DDMMMMYYYY));
				}
			});
			break;
		default:
			break;
		}
	}
	
}
