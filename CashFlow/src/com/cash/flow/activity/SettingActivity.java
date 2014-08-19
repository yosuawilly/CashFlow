package com.cash.flow.activity;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowListActivity;
import com.cash.flow.activity.setting.ChangePasswordActivity;
import com.cash.flow.activity.setting.EditLastTransActivity;
import com.cash.flow.activity.setting.SetMarginActivity;
import com.cash.flow.adapter.MenuListAdapter;
import com.cash.flow.customcomponent.FileDialog;
import com.cash.flow.database.DatabaseExportImport;
import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.Currency;
import com.cash.flow.util.Constant;
import com.cash.flow.util.Utility;

public class SettingActivity extends BaseCashFlowListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		buildDefaultList(new String[]{
				"Change Password",
				"Set Margin",
				"Currency",
				"Edit Last Transaction",
				"Backup Database",
				"Restore Database"
		}, new int[]{
				R.drawable.change_password_icon,
				R.drawable.coins,
				R.drawable.dollars,
				R.drawable.edit,
				R.drawable.backup_db_icon,
				R.drawable.restore_db_icon
		});
	}
	
	@SuppressLint("SdCardPath") 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MenuListAdapter adapter = (MenuListAdapter) l.getAdapter();
		String item = adapter.getItem(position);
		if("Change Password".equalsIgnoreCase(item)) {
			Intent intent = new Intent(this, ChangePasswordActivity.class);
			startActivityForResult(intent, Constant.START_ACTIVITY);
		} else if("Set Margin".equals(item)) {
			Intent intent = new Intent(this, SetMarginActivity.class);
			startActivityForResult(intent, Constant.START_ACTIVITY);
		} else if("Currency".equals(item)) {
			final String[]items = Currency.getCurrency();
			Currency current = GlobalVar.getInstance().getUser().getCurrency();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Currency");
			builder.setSingleChoiceItems(items, current.ordinal(), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int position) {
					Currency select = Currency.valueOf(items[position]);
					GlobalVar.getInstance().getUser().setCurrency(select);
					
					UserDao userDao = UserDao.getInstance(SettingActivity.this);
					userDao.updateData(GlobalVar.getInstance().getUser());
					userDao.closeConnection();
					
					sendBroadcast(new Intent(MainMenuActivity.REFRESH_ACTION));
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
				}
			});
			builder.setCancelable(false);
			builder.create().show();
		} else if("Edit Last Transaction".equals(item)) {
			CashFlowDao cashFlowDao = CashFlowDao.getInstance(this);
			CashFlow lastCash = cashFlowDao.findLastTransaction();
			cashFlowDao.closeConnection();
			
			if(lastCash == null) {
				Utility.showMessage(this, getString(R.string.message_noLastTransaction));
				return;
			}
			
			Intent intent = new Intent(this, EditLastTransActivity.class);
			/*Bundle bundle = new Bundle();
			bundle.putSerializable("cashflow", lastCash);
			intent.putExtras(bundle);*/
			startActivityForResult(intent, Constant.START_ACTIVITY);
		} else if("Backup Database".equals(item)) {
			if (DatabaseExportImport.exportDb(this)) {
				Utility.showMessage(this, "Close", "Backup Success");
			} else Utility.showErrorMessage(this, "Database doesn't exist");
		} else if("Restore Database".equals(item)) {
			Intent intent = new Intent(getBaseContext(), FileDialog.class);
            intent.putExtra(FileDialog.START_PATH, "/sdcard");
            
            //can user select directories or not
            intent.putExtra(FileDialog.CAN_SELECT_DIR, false);
            
            //alternatively you can set file filter
            //intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "png" });
            
            startActivityForResult(intent, Constant.BROWSE_FILE);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == Constant.BROWSE_FILE) {
				//String filePath = data.getStringExtra(FileDialog.RESULT_PATH);
				File dbFile = (File) data.getSerializableExtra(FileDialog.RESULT_FILE);
				if (DatabaseExportImport.importIntoDb(this, dbFile)) {
					Utility.showMessage(this, "Close", "Restore Success");
					CashFlowDao cashFlowDao = CashFlowDao.getInstance(this);
					CashFlow cashFlow = cashFlowDao.findLastTransaction();
					if (cashFlow!=null) {
						UserDao userDao = UserDao.getInstance(this);
						GlobalVar.getInstance().getUser().setBalance(cashFlow.getBalance());
						userDao.updateData(GlobalVar.getInstance().getUser());
						
						userDao.closeConnection();
					}
					cashFlowDao.closeConnection();
					
					sendBroadcast(new Intent(MainMenuActivity.REFRESH_ACTION));
				} else Utility.showErrorMessage(this, "Restore Failed");
			}

		} else if (resultCode == Activity.RESULT_CANCELED) {
			Logger.getLogger(SettingActivity.class.getName()).log(Level.WARNING, "file not selected");
		}
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.setting_layout_page;
	}

}
