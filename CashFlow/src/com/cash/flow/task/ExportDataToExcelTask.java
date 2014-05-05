package com.cash.flow.task;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;
import com.cash.flow.util.NumberUtil;

import android.content.Context;
import android.os.Environment;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportDataToExcelTask extends BaseTask{
	
	private List<CashFlow> cashFlows = new ArrayList<CashFlow>();
	
	public ExportDataToExcelTask(Context context, TaskCompleteListener completeListener) {
		super(context, completeListener);
	}
	
	public ExportDataToExcelTask(Context context, TaskCompleteListener completeListener, String message) {
		super(context, completeListener, message);
	}

	public ExportDataToExcelTask(Context context, TaskCompleteListener completeListener, String message, Integer idCaller) {
		super(context, completeListener, message, idCaller);
	}
	
	public void setCashFlows(List<CashFlow> cashFlows) {
		this.cashFlows = cashFlows;
	}
	
	@Override
	protected Boolean doInBackground(Object... params) {
		String fileName = (String) params[0];
		File tempDir = new File(Environment.getExternalStorageDirectory(), "CashFlow/temp");
		File exportDir = new File(Environment.getExternalStorageDirectory(), "CashFlow/ExportData");
		if(!tempDir.exists()) tempDir.mkdirs();
		if(!exportDir.exists()) exportDir.mkdirs();
		
		File tempFile = new File(tempDir, "tempDB.csv");
		
		try {
			if (tempFile.createNewFile()){
			    System.out.println("File is created!");
			    System.out.println("myfile.csv "+tempFile.getAbsolutePath());
			}
			
			CSVWriter csvWrite = new CSVWriter(new FileWriter(tempFile));
			csvWrite.writeNext(new String[]{"Date","Cash In","Cash Out","Description","Balance"});
			
			for(CashFlow cashFlow : cashFlows) {
				csvWrite.writeNext(new String[]{
					MyCalendar.parseLocaleDate(cashFlow.getTimestamp(), Constant.FORMAT_DATE_DDMMYYYY),
					cashFlow.getTypeCash().equals(CashType.CASH_IN) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-",
					cashFlow.getTypeCash().equals(CashType.CASH_OUT) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-",
					cashFlow.getDescription(),
					NumberUtil.toCurr2(String.valueOf(cashFlow.getBalance()))		
				});
			}
			
			csvWrite.close();
			
			generateToExcel(fileName);
			
			tempFile.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage(e.getMessage());
			return false;
		}
		
		return super.doInBackground(params);
	}
	
	private void generateToExcel(String fileName) throws Exception{
		ArrayList arList = null;
		ArrayList al = null;

		String inFilePath = Environment.getExternalStorageDirectory()
				.toString() + "/CashFlow/temp/tempDB.csv";
		String outFilePath = Environment.getExternalStorageDirectory()
				.toString() + "/CashFlow/ExportData/" + fileName + ".xls";
		String thisLine;
		int count = 0;

		try {

			FileInputStream fis = new FileInputStream(inFilePath);
			DataInputStream myInput = new DataInputStream(fis);
			int i = 0;
			arList = new ArrayList();
			while ((thisLine = myInput.readLine()) != null) {
				al = new ArrayList();
				String strar[] = thisLine.split(",");
				for (int j = 0; j < strar.length; j++) {
					al.add(strar[j]);
				}
				arList.add(al);
				System.out.println();
				i++;
			}
		} catch (Exception e) {
			System.out.println("shit");
			e.printStackTrace();
		}

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList ardata = (ArrayList) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					HSSFCell cell = row.createCell((short) p);
					String data = ardata.get(p).toString();
					if (data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
					// */
					// cell.setCellValue(ardata.get(p).toString());
				}
				System.out.println();
			}
			FileOutputStream fileOut = new FileOutputStream(outFilePath);
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		// main method ends
	}

}
