package com.cash.flow.util;

/**
 * @author Dwidasa

 */

import java.util.Calendar;

import com.cash.flow.receiver.AlarmReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtil {
	
	public static void AddAlarm(Context context, int requestCode, Calendar dueDate, int repeat, String pesan, boolean isSnooze) {
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        Calendar calNow = Calendar.getInstance();
        
        intent.putExtra(Constant.RECORD_ID, requestCode);
        intent.putExtra("REPEAT", repeat);
        intent.putExtra("PESAN", pesan);
        intent.putExtra(Constant.IS_SNOOZE, isSnooze);
        //PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent,  Intent.FLAG_ACTIVITY_NEW_TASK );
        PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent,  PendingIntent.FLAG_UPDATE_CURRENT );
        long interval = 0L;
        switch(repeat){
        case Constant.NO_REPEAT:
        	interval = 0L;
            break;
        case Constant.DAILY:
            interval = AlarmManager.INTERVAL_DAY; 
            break;
        case Constant.WEEKLY:
            interval = AlarmManager.INTERVAL_DAY*7;
            break;
        case Constant.MONTHLY:
            interval = AlarmManager.INTERVAL_DAY*30;
            break;
        case Constant.FIVE_MINUTE:
        	interval = (1000*60*5);
        	break;
        case Constant.TEN_MINUTE:
        	interval = (1000*60*10);
        	break;
        case Constant.FIVTEEN_MINUTE:
        	interval = (1000*60*15);
        	break;
        case Constant.ONE_HOUR:
        	interval = (1000*60*60);
        	break;
        default:
            break;
        }
        
        if(dueDate.compareTo(calNow) <= 0){
        	dueDate.add(Calendar.DATE, 7);
        }
        
        alarm.cancel(operation);
        //alarm.set(AlarmManager.RTC_WAKEUP, dueDate.getTimeInMillis(), operation);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, dueDate.getTimeInMillis(), interval, operation);
    }
	
	public static void cancelAlarm(Context context, int requestCode){
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        alarm.cancel(operation);
	}
	
	public static boolean isAlarmExist(Context context, int requestCode){
		Intent intent = new Intent(context, AlarmReceiver.class);
		boolean alarmExist = (PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null);
		return alarmExist;
	}
	
	/*public static int getDay(String day){
		if(day.equals(Constant.hari[0])){
			return Calendar.MONDAY;
		}else if(day.equals(Constant.hari[1])){
			return Calendar.TUESDAY;
		}else if(day.equals(Constant.hari[2])){
			return Calendar.WEDNESDAY;
		}else if(day.equals(Constant.hari[3])){
			return Calendar.THURSDAY;
		}else if(day.equals(Constant.hari[4])){
			return Calendar.FRIDAY;
		}else {
			return Calendar.SATURDAY;
		}
	}*/

}
