package com.cash.flow.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.cash.flow.R;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.listener.DialogListener;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.DatePicker;

public class Utility {
	
	public static void showMessage(Context context, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Information").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static void showMessage(Context context, String textButton, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Information").setPositiveButton(textButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static void showMessage(Context context, String textButton, String pesan, final DialogListener listener){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Information").setPositiveButton(textButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
				listener.onDialogClose();
			}
		}).create().show();
	}
	
	public static void showConfirmMessage(Context context, String title, String pesan, final DialogListener listener){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle(title).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
				listener.onDialogClose();
			}
		}).setNegativeButton("No", null).create().show();
	}
	
	public static void showConfirmMessage(Context context, String title, String pesan, String positiveButton, 
			String negativeButton, final DialogListener listener){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle(title).setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
				listener.onDialogClose();
			}
		}).setNegativeButton(negativeButton, null).create().show();
	}
	
	public static void showErrorMessage(Context context, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Error").setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static boolean isSDCardExist(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static boolean cekValidResult(String result, Activity activity){
		String message="";
        String errorCode="";
        try{
        	if((result.indexOf("errorCode") < 0) && (result.indexOf("status") < 0)){
				return true;
			} else {
				JSONObject jsonObject = new JSONObject(result);
				if(jsonObject.has("errorCode"))
                {
					errorCode = jsonObject.getString("errorCode");
					message = (jsonObject.has("fullMessage"))?jsonObject.getString("fullMessage"):ResourceUtil.getBundle().getString(errorCode);
					showErrorMessage(activity, message);
					return false;
                } 
				else if(jsonObject.has("status"))
				{
					String status = jsonObject.getString("status");
					message = (jsonObject.has("fullMessage"))?jsonObject.getString("fullMessage"):ResourceUtil.getBundle().getString(errorCode);
					if("0".equals(status))
					{
						showErrorMessage(activity, message);
						return false;
					}
				}
			}
        } catch(JSONException e){
        	e.printStackTrace();
        	if(message.equals(null) || message=="")
            {
                 message = activity.getString(R.string.message_problemKomunikasiServer);
            }
        	showErrorMessage(activity, message);
        	return false;
        } catch (Exception e) {
        	e.printStackTrace();
        	if(message.equals(null) || message=="")
            {
                 message = activity.getString(R.string.message_problemKomunikasiServer);
            }
        	showErrorMessage(activity, message);
        	return false;
        }
        return true;
	}
	
	public static Drawable getDrawableFromAsset(Context context, String imageFileName){
		InputStream stream;
		try {
			stream = context.getAssets().open(imageFileName);
			Drawable d = Drawable.createFromStream(stream, null);
			return d;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String[]getAllFilesInAsset(Context context, String path){
		String [] files = null;
		AssetManager assetManager = context.getAssets();
		try {
			files = assetManager.list(path);
			if(files!=null){
				for(int i=0;i<files.length;i++){
					Log.i("file", files[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}
	
	/**
	 * On Android 3.0 and above, while using the ActionBar tabbed navigation style, the tabs sometimes appear above the action bar.
	 * This helper method allows you to control the 'hasEmbeddedTabs' behaviour.
	 * A value of true will put the tabs inside the ActionBar, a value of false will put it above or below the ActionBar.
	 *
	 * You should call this method while initialising your ActionBar tabs.
	 * Don't forget to also call this method during orientation changes (in the onConfigurationChanged() method).
	 *
	 * @param inActionBar
	 * @param inHasEmbeddedTabs
	 */
	public static void setHasEmbeddedTabs(Object inActionBar, final boolean inHasEmbeddedTabs)
	{
	        // get the ActionBar class
	        Class<?> actionBarClass = inActionBar.getClass();
	 
	        // if it is a Jelly Bean implementation (ActionBarImplJB), get the super class (ActionBarImplICS)
	        if ("android.support.v7.app.ActionBarImplJB".equals(actionBarClass.getName()))
	        {
	                actionBarClass = actionBarClass.getSuperclass();
	        }
	 
	        try
	        {
	                // try to get the mActionBar field, because the current ActionBar is probably just a wrapper Class
	                // if this fails, no worries, this will be an instance of the native ActionBar class or from the ActionBarImplBase class
	                final Field actionBarField = actionBarClass.getDeclaredField("mActionBar");
	                actionBarField.setAccessible(true);
	                inActionBar = actionBarField.get(inActionBar);
	                actionBarClass = inActionBar.getClass();
	        }
	        catch (IllegalAccessException e) {}
	        catch (IllegalArgumentException e) {}
	        catch (NoSuchFieldException e) {}
	 
	        try
	        {
	                // now call the method setHasEmbeddedTabs, this will put the tabs inside the ActionBar
	                // if this fails, you're on you own <img src="http://www.blogc.at/wp-includes/images/smilies/icon_wink.gif" alt=";-)" class="wp-smiley"> 
	                final Method method = actionBarClass.getDeclaredMethod("setHasEmbeddedTabs", new Class[] { Boolean.TYPE });
	                method.setAccessible(true);
	                method.invoke(inActionBar, new Object[]{ inHasEmbeddedTabs });
	        }
	        catch (NoSuchMethodException e)        {}
	        catch (InvocationTargetException e) {}
	        catch (IllegalAccessException e) {}
	        catch (IllegalArgumentException e) {}
	}
	
	@SuppressLint("NewApi") 
	public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener dateSetListener){
		showDatePicker(context, null, dateSetListener);
	}
	
	@SuppressLint("NewApi") 
	public static void showDatePicker(Context context, Date date, DatePickerDialog.OnDateSetListener dateSetListener){
		Calendar calendar = Calendar.getInstance();
		if(date != null) calendar.setTime(date);
		DatePickerDialog dialog = new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		if(android.os.Build.VERSION.SDK_INT >= 11){
			dialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
			dialog.getDatePicker().setCalendarViewShown(false);
		}
		
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
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
	}

}
