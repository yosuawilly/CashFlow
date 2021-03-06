package com.cash.flow.activity;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseCashFlowActivity;
import com.cash.flow.adapter.FragmentAdapter;
import com.cash.flow.customcomponent.CustomViewPager;
import com.cash.flow.listener.DialogListener;
import com.cash.flow.listener.SummaryFragmentListener;
import com.cash.flow.model.CashFlow;
import com.cash.flow.task.ExportDataToExcelTask;
import com.cash.flow.task.TaskCompleteListener;
import com.cash.flow.util.Constant;
import com.cash.flow.util.TabSetupTools;
import com.cash.flow.util.TabSetupTools.OnTabChanged;
import com.cash.flow.util.Utility;

public class TransactionActivity extends BaseCashFlowActivity implements OnPageChangeListener, OnTabChanged, 
OnClickListener, TaskCompleteListener{
	
	private final int EXPORT_DATA = 11;
	
	private String[] tabs = { "Cash In", "Cash Out", "Summary" };
	
	private CustomViewPager viewPager;
	private FragmentAdapter fragmentAdapter;
	private TabSetupTools tabSetupTools;
	
	private boolean changeFromPager = false;
	private boolean changeFromTab = false;
	
	private Button exportButton;
	private String fileName = "";
	
	public SummaryFragmentListener summaryFragmentListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tabSetupTools = new TabSetupTools(this, this, savedInstanceState, R.id.tabhost, tabs);
		tabSetupTools.generateTabs();
	}
	
	@Override
	public void initObject() {
		super.initObject();
		fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		//initLayoutHeader();
		
		/*init ViewPager*/
		viewPager = (CustomViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(this);
		viewPager.setEnableSwipe(true);
		viewPager.setAdapter(fragmentAdapter);
		
		exportButton = (Button) findViewById(R.id.next_button);
		exportButton.setText("Export");
		exportButton.setOnClickListener(this);
		
		/*init actionbar*/
		/*getMyActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tab_name : tabs) {
			getMyActionBar().addTab(getMyActionBar().newTab().setText(tab_name)
                    .setTabListener(this));
        }
		getMyActionBar().show();*/
		
	}
	
	protected void initLayoutHeader() {
		LinearLayout v = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_header, null);
    	//((FrameLayout)findViewById(com.actionbarsherlock.R.id.activity_header)).addView(v);
//		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
//		ViewGroup child = (ViewGroup) decor.getChildAt(0);
//		child.addView(v, 0);
		
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		ViewGroup child = (ViewGroup) decor.getChildAt(0);
		if(child instanceof FrameLayout || FrameLayout.class.isAssignableFrom(child.getClass()) ){
			while(true) {
				child = (ViewGroup) child.getChildAt(0);
				if( ! (child instanceof FrameLayout) || 
						!FrameLayout.class.isAssignableFrom(child.getClass()) ) {
					child.addView(v, 0);
					Log.i("child in", "added header child count "+child.getChildCount());
					if(child instanceof LinearLayout) Log.i("linear", "true");
					break;
				}
			}
		} else {
			if(android.os.Build.VERSION.SDK_INT == 19) {
				//child.addView(v, 0);
				ViewGroup cont = (ViewGroup) child.getChildAt(1);
				ViewGroup contextBar = (ViewGroup) cont.getChildAt(1);
//				ViewGroup tab = (ViewGroup) cont.getChildAt(2);
//				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tab.getLayoutParams());
//				params.setMargins(0, 30, 0, 0);
//				tab.setLayoutParams(params);
				contextBar.addView(v);
//				LinearLayout lin = (LinearLayout) ((ViewGroup)cont.getChildAt(0)).getChildAt(0);
//				LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
//				par.gravity = Gravity.CENTER;
//				lin.addView(v, par);
//				lin.setLayoutParams(par);
				//printChild(cont, 1);
			} else {
				child.addView(v, 0);
			}
		}
		
    	findViewById(R.id.layout_header).setVisibility(View.GONE);
    }
	
	public void printChild(ViewGroup child, int index) {
		if(child.getChildCount() > 0) {
			for(int i=0;i<child.getChildCount();i++) {
				try {
					ViewGroup ch = (ViewGroup) child.getChildAt(i);
					Log.i(String.valueOf(index), ch.toString());
					if(ch.getChildCount() > 0) printChild(ch, index+1);
				}catch(Exception e){
					View v = child.getChildAt(i);
					Log.i(String.valueOf(index), v.toString());
				}
			}
		}
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.transaction_layout_page;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		if(changeFromTab) {
			changeFromTab = false;
			return;
		}
		
		changeFromPager = true;
		tabSetupTools.setSelectedTab(position);
		//getMyActionBar().setSelectedNavigationItem(position);
		if(position == 2) {
			exportButton.setVisibility(View.VISIBLE);
		} else {
			exportButton.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onTabChanged(String tabId) {
		if(changeFromPager) {
			changeFromPager = false;
			return;
		}
		
		changeFromTab = true;
		if(tabId.equals(tabs[0])) {
			viewPager.setCurrentItem(0);
			exportButton.setVisibility(View.INVISIBLE);
		} else if(tabId.equals(tabs[1])) {
			viewPager.setCurrentItem(1);
			exportButton.setVisibility(View.INVISIBLE);
		} else if(tabId.equals(tabs[2])) {
			viewPager.setCurrentItem(2);
			exportButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == exportButton.getId()) {
			if(summaryFragmentListener.getCashFlows().size() == 0) {
				Utility.showMessage(this, "Close", getString(R.string.message_noDataExport));
				
				return;
			}
			
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.input_file_name_layout);
			dialog.setTitle("Export Report");
			
			final EditText fileNameEdit = (EditText) dialog.findViewById(R.id.fileNameEdit);
			
			dialog.findViewById(R.id.buttonExport).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					fileName = fileNameEdit.getText().toString().trim();
					if(fileName.equals("")) {
						Utility.showMessage(TransactionActivity.this, TransactionActivity.this.getString(R.string.message_fileNameRequired));
					} else {
						if(fileName.contains(".xls")) {
							fileName = fileName.substring(0, fileName.indexOf(".xls"));
						}
						
						File exportFile = new File(Environment.getExternalStorageDirectory(), "CashFlow/ExportData/"+fileName+".xls");
						if(exportFile.exists()) {
							Utility.showConfirmMessage(TransactionActivity.this, "File Exist", 
									getString(R.string.message_fileExist), new DialogListener() {
								
								@Override
								public void onDialogClose() {
									exportReport();
									dialog.dismiss();
								}
							});
						} else {
							exportReport();
							dialog.dismiss();
						}
					}
				}
			});
			
			dialog.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			dialog.show();
			
		}
	}
	
	private void exportReport() {
		List<CashFlow> cashFlows = summaryFragmentListener.getCashFlows();
		
		ExportDataToExcelTask dataToExcelTask = new ExportDataToExcelTask(TransactionActivity.this, TransactionActivity.this, 
				"Exporting data ...", EXPORT_DATA);
		dataToExcelTask.setCashFlows(cashFlows);
		dataToExcelTask.execute(fileName);
	}

	@Override
	public void onTaskComplete(Integer idCaller, boolean sukses, String errorMessage) {
		if(!sukses) {
			Utility.showErrorMessage(this, errorMessage);
		} else {
			//Utility.showMessage(this, getString(R.string.message_reportSuksesGenerated));
			final File exportDir = new File(Environment.getExternalStorageDirectory(), "CashFlow/ExportData/"+fileName+".xls");
			String message = MessageFormat.format(getString(R.string.message_reportSuksesGenerated), exportDir.getAbsolutePath());
			Utility.showConfirmMessage(this, "Export Success", message, 
					"Show", "No", new DialogListener() {
						
						@Override
						public void onDialogClose() {
							Intent intent = new Intent(Intent.ACTION_VIEW);
							//intent.setDataAndType(Uri.fromFile(exportDir), "application/xls");
							//intent.setData(Uri.fromFile(exportDir));
							intent.setDataAndType(Uri.fromFile(exportDir), MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"));
							startActivityForResult(Intent.createChooser(intent, "Open File"), Constant.START_ACTIVITY);
						}
					});
		}
	}

}
