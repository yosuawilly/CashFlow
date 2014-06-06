package com.cash.flow.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cash.flow.R;
import com.cash.flow.activity.TransactionActivity;
import com.cash.flow.adapter.CashFlowListAdapter;
import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.listener.SummaryFragmentListener;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.User;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;
import com.cash.flow.util.Utility;

@SuppressLint("ValidFragment")
public class SummaryFragment extends SherlockFragment implements OnClickListener, SummaryFragmentListener{
	
	private static final String BUNDLE_CASHFLOW = "BUNDLE_CASHFLOW";
	private static final String BUNDLE_GLOBALVAR = "BUNDLE_GLOBALVAR";
	
	private Context context;
	private ViewGroup viewGroup;
	
	private Spinner spinnerType, spinnerMonth;
	
	private ArrayAdapter<String> typeAdapter, monthAdapter;
	
	private LinearLayout weeklyLayout, monthlyLayout, onlyMonthLayout;
	
	private TextView toDateTV, fromDateTV;
	
	private EditText yearEdit;
	
	private Date fromDate, toDate;
	
	List<CashFlow> cashFlows = new ArrayList<CashFlow>();
	private ListView listCashFlow;
	private CashFlowListAdapter listAdapter;
	
	public SummaryFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static SummaryFragment newInstance() {
		return new SummaryFragment();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = getActivity();
		((TransactionActivity)context).summaryFragmentListener = this;
	}
	
	/*public SummaryFragment(Context context) {
		this.context = context;
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null) {
			cashFlows = savedInstanceState.getParcelableArrayList(BUNDLE_CASHFLOW);
			if(GlobalVar.getInstance().getUser()==null) {
				GlobalVar.getInstance().setUser((User)savedInstanceState.getSerializable(BUNDLE_GLOBALVAR));
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ArrayList<CashFlow> cashFlows = new ArrayList<CashFlow>();
		cashFlows.addAll(this.cashFlows);
		outState.putParcelableArrayList(BUNDLE_CASHFLOW, cashFlows);
		outState.putSerializable(BUNDLE_GLOBALVAR, GlobalVar.getInstance().getUser());
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
		this.fromDate = MyCalendar.getPlusDate(new Date(), -7);
		this.toDate = new Date();
		fromDateTV.setText(MyCalendar.parseLocaleDate(fromDate, Constant.FORMAT_DATE_DDMMMMYYYY));
		toDateTV.setText(MyCalendar.parseLocaleDate(toDate, Constant.FORMAT_DATE_DDMMMMYYYY));
		
		yearEdit = (EditText) monthlyLayout.findViewById(R.id.yearEdit);
		
		typeAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_component, 
				getResources().getStringArray(R.array.report_type));
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerType.setAdapter(typeAdapter);
		
		monthAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_component, 
				getResources().getStringArray(R.array.month));
		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMonth.setAdapter(monthAdapter);
		
		listCashFlow = (ListView) viewGroup.findViewById(R.id.listCashFlow);
		
		initializeListener();
		
		//init listCashFlow
		listAdapter = new CashFlowListAdapter(context, cashFlows);
		listCashFlow.setAdapter(listAdapter);
		listAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
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
		viewGroup.findViewById(R.id.buttonShowCashFlow).setOnClickListener(this);
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
	
	private void buildListCashFlow(Date fromDate, Date toDate) {
		CashFlowDao.clearAllInternalObjectCaches();
		CashFlowDao cashFlowDao = CashFlowDao.getInstance(context);
		cashFlowDao.clearObjectCache();
		//List<CashFlow> cashFlows = cashFlowDao.findByDate(fromDate, toDate);
		cashFlows = cashFlowDao.findByDate(fromDate, toDate);
		
		Log.i("lengthData", String.valueOf(cashFlows.size()));
		
		listAdapter = new CashFlowListAdapter(context, cashFlows);
		listCashFlow.setAdapter(listAdapter);
		listAdapter.notifyDataSetChanged();
		
		cashFlowDao.closeConnection();
	}
	
	@Override
	public List<CashFlow> getCashFlows() {
		return cashFlows;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pickFromDate:
			Utility.showDatePicker(context, fromDate, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker dp, int year, int month, int day) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, day);
					fromDate = calendar.getTime();
					fromDateTV.setText(MyCalendar.parseLocaleDate(year, month, day, Constant.FORMAT_DATE_DDMMMMYYYY));
				}
			});
			break;
		case R.id.pickToDate:
            Utility.showDatePicker(context, toDate, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker dp, int year, int month, int day) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, day);
					toDate = calendar.getTime();
					toDateTV.setText(MyCalendar.parseLocaleDate(year, month, day, Constant.FORMAT_DATE_DDMMMMYYYY));
				}
			});
			break;
		case R.id.buttonShowCashFlow:
			String yearStr = "";
			Calendar from = null, toCal = null;
			Date to = null;
			
			switch (spinnerType.getSelectedItemPosition()) {
			case 0: //weekly
				if(!MyCalendar.isOneWeek(fromDate, toDate)) {
					Utility.showMessage(context, "Close", getString(R.string.message_rangeOneWeek));
					
					break;
				}
				
				toDate = MyCalendar.resetDate(toDate);
				buildListCashFlow(fromDate, toDate);
				break;
			case 1: //monthly
				yearStr = yearEdit.getText().toString().trim();
				if(yearStr.equals("")) {
					Utility.showMessage(context, "Close", context.getString(R.string.message_yearEmpty));
					return;
				}
				
				int month = spinnerMonth.getSelectedItemPosition();
				from = Calendar.getInstance();
				from.set(Integer.parseInt(yearStr), month, 1, 0, 0, 0);
				from.set(Calendar.MILLISECOND, 0);
				
				toCal = new GregorianCalendar(Integer.parseInt(yearStr), month+1, 0);
				to = MyCalendar.getEndOfDay(toCal.getTime());
				
				Log.i("from", from.getTime().toString());
				Log.i("to", to.toString());
				
				buildListCashFlow(from.getTime(), to);
				
				break;
			case 2: //yearly
				yearStr = yearEdit.getText().toString().trim();
				if(yearStr.equals("")) {
					Utility.showMessage(context, "Close", context.getString(R.string.message_yearEmpty));
					return;
				}
				
				from = Calendar.getInstance();
				from.set(Integer.parseInt(yearStr), 0, 1, 0, 0, 0);
				from.set(Calendar.MILLISECOND, 0);
				
				toCal = new GregorianCalendar(Integer.parseInt(yearStr), 12, 0);
				to = MyCalendar.getEndOfDay(toCal.getTime());
				
				Log.i("from", from.getTime().toString());
				Log.i("to", to.toString());
				
				buildListCashFlow(from.getTime(), to);
				
				break;
			default:
				break;
			}
			
			break;
		default:
			break;
		}
	}
	
}
