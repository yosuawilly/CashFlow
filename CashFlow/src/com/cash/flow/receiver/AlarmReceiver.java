package com.cash.flow.receiver;

/**
 * @author Dwidasa

 */

import com.cash.flow.R;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.util.Constant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String pesan = bundle.getString("PESAN");
		int requestcode = bundle.getInt(Constant.RECORD_ID);
		boolean isSnooze = bundle.getBoolean(Constant.IS_SNOOZE);
		//Intent intent2 = new Intent(context, AlarmRingService.class);
		//context.startService(intent2);
		showNotification(context, intent, pesan, requestcode, isSnooze);
	}
	
	@SuppressWarnings("deprecation")
	public void showNotification(Context context, Intent intent, String pesan, int requestcode, boolean isSnooze){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.cashflow_icon, "Notification", System.currentTimeMillis());
		String notificationTitle = "Low Balance";
		String notificationText = pesan;
		Intent intent2 = new Intent(context, MainMenuActivity.class);
		intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent2.putExtra("pesan", pesan);
		intent2.putExtra(Constant.RECORD_ID, requestcode);
		intent2.putExtra(Constant.IS_SNOOZE, isSnooze);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, Intent.FLAG_ACTIVITY_NEW_TASK);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context.getApplicationContext(), notificationTitle, notificationText, pendingIntent);
		notificationManager.notify(0, notification);
	}

}
