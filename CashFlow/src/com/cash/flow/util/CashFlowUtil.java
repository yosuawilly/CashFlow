package com.cash.flow.util;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cash.flow.R;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.User;
import com.cash.flow.model.CashFlow.CashType;

public class CashFlowUtil {
	
	public static void saveCashFlow(Context context, CashFlow cashFlow) {
		if(cashFlow.getTypeCash().equals(CashType.CASH_IN)) {
			GlobalVar.getInstance().getUser().setBalance(GlobalVar.getInstance().getUser().getBalance() + cashFlow.getNominal());
		} else {
			GlobalVar.getInstance().getUser().setBalance(GlobalVar.getInstance().getUser().getBalance() - cashFlow.getNominal());
		}
		
		cashFlow.setBalance(GlobalVar.getInstance().getUser().getBalance());
		
		CashFlowDao cashFlowDao = CashFlowDao.getInstance(context);
		cashFlowDao.createData(cashFlow);
		
		UserDao userDao = UserDao.getInstance(context);
		userDao.updateData(GlobalVar.getInstance().getUser());
		
		cashFlowDao.closeConnection();
		userDao.closeConnection();
		
		context.sendBroadcast(new Intent(MainMenuActivity.REFRESH_ACTION));
		
		cekLowBalance(context); // check balance is low
	}
	
	public static void updateCashFlow(Context context, CashFlow cashFlow) {
		CashFlowDao cashFlowDao = CashFlowDao.getInstance(context);
		UserDao userDao = UserDao.getInstance(context);
		
		CashFlow cashBefore = cashFlowDao.findCashFlowBefore(cashFlow);
		
		if(cashBefore != null) {
			if(cashFlow.getTypeCash().equals(CashType.CASH_IN)) {
				cashFlow.setBalance(cashBefore.getBalance() + cashFlow.getNominal());
			} else {
				cashFlow.setBalance(cashBefore.getBalance() - cashFlow.getNominal());
			}
			
			GlobalVar.getInstance().getUser().setBalance(cashFlow.getBalance());
			
		} else {
			cashFlow.setBalance(cashFlow.getNominal());
			GlobalVar.getInstance().getUser().setBalance(cashFlow.getBalance());
		}
		
		cashFlowDao.updateData(cashFlow);
		userDao.updateData(GlobalVar.getInstance().getUser());
		
		cashFlowDao.closeConnection();
		userDao.closeConnection();
		
		context.sendBroadcast(new Intent(MainMenuActivity.REFRESH_ACTION));
		
		cekLowBalance(context); // check balance is low
	}
	
	public static boolean isBalanceEnough(CashFlow cashFlow) {
		return GlobalVar.getInstance().getUser().getBalance() >= cashFlow.getNominal();
	}
	
	public static boolean isLowBalance() {
		return GlobalVar.getInstance().getUser().getBalance() < GlobalVar.getInstance().getUser().getMargin();
	}
	
	public static void cekLowBalance(Context context) {
		User user = GlobalVar.getInstance().getUser();
		if(user.getBalance() < user.getMargin()) {
			if(!AlarmUtil.isAlarmExist(context, Constant.LOW_BALANCE_ALARM)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, 3);
				Log.i("date", calendar.getTime().toString());
				AlarmUtil.AddAlarm(context, Constant.LOW_BALANCE_ALARM, calendar, 
						Constant.ONE_HOUR, context.getString(R.string.message_lowBalance), false);
				Log.i("add", "alarm");
			}
		} else {
			if(AlarmUtil.isAlarmExist(context, Constant.LOW_BALANCE_ALARM)) {
				AlarmUtil.cancelAlarm(context, Constant.LOW_BALANCE_ALARM);
				Log.i("cancel", "alarm");
			}
		}
	}
	
}
