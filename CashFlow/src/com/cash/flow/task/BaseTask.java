package com.cash.flow.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class BaseTask extends AsyncTask<Object, String, Boolean>{
	
	private Context context;
	private TaskCompleteListener completeListener;
	private ProgressDialog dialog;
	private String message = "Loading process ...";
	private Integer idCaller = null;
	private String errorMessage = "";
	
	public BaseTask(Context context, TaskCompleteListener completeListener) {
		this.context = context;
		this.completeListener = completeListener;
	}
	
	public BaseTask(Context context, TaskCompleteListener completeListener, String message) {
		this(context, completeListener);
		this.message = message;
	}
	
	public BaseTask(Context context, TaskCompleteListener completeListener, String message, Integer idCaller) {
		this(context, completeListener, message);
		this.idCaller = idCaller;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context,    
                "Please wait...", message, true, true, 
                new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            }
        );
		dialog.setCanceledOnTouchOutside(false);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if (dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (IllegalArgumentException e) {				
				//do nothing
				e.printStackTrace();
			}
		}
		
		completeListener.onTaskComplete(idCaller, result, errorMessage);
	}
	
}
