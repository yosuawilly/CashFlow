package com.cash.flow.util;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

import com.cash.flow.R;
import com.cash.flow.listener.DialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

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
		.setTitle(title).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
				listener.onDialogClose();
			}
		}).setNegativeButton("Tidak", null).create().show();
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

}
