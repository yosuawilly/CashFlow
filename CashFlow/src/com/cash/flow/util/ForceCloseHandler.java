package com.cash.flow.util;

import com.cash.flow.StartUpActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Process;

public class ForceCloseHandler implements Thread.UncaughtExceptionHandler{
	
	private Activity activity ;
	
	public ForceCloseHandler(Activity activity) {
		this.activity = activity ;
	}	

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		LogMe.activity("ForceCloseHandler - Thread.UncaughtExceptionHandler \n"+ex.getLocalizedMessage()+" \n");
		LogMe.write(ex);
		
		activity.startActivity(new Intent(activity, StartUpActivity.class));
		activity.finish();
		Process.killProcess(Process.myPid());
		System.exit(0);
	}
}
