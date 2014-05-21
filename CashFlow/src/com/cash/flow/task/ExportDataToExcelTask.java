package com.cash.flow.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;
import android.content.Context;
import android.os.Environment;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportDataToExcelTask extends BaseTask{
	
	private List<CashFlow> cashFlows = new ArrayList<CashFlow>();
	
	private CellStyle cs = null;
	private CellStyle csBold = null;
	private CellStyle csTop = null;
	private CellStyle csRight = null;
	private CellStyle csBottom = null;
	private CellStyle csLeft = null;
	private CellStyle csTopLeft = null;
	private CellStyle csTopRight = null;
	private CellStyle csBottomLeft = null;
	private CellStyle csBottomRight = null;
	
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
					MyCalendar.parseLocaleDate(cashFlow.getTimestamp(), Constant.FORMAT_DATE_DDMMYYYY_HMS),
					//cashFlow.getTypeCash().equals(CashType.CASH_IN) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-",
					//cashFlow.getTypeCash().equals(CashType.CASH_OUT) ? NumberUtil.toCurr2(String.valueOf(cashFlow.getNominal())):"-",
					cashFlow.getTypeCash().equals(CashType.CASH_IN) ? String.valueOf(cashFlow.getNominal()):"-",
					cashFlow.getTypeCash().equals(CashType.CASH_OUT) ? String.valueOf(cashFlow.getNominal()):"-",
					cashFlow.getDescription(),
					String.valueOf(cashFlow.getBalance())		
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
		
		return true;
	}
	
	private void generateToExcel(String fileName) throws Exception{
		ArrayList<ArrayList<String>> arList = null;
		ArrayList<String> al = null;

		String inFilePath = Environment.getExternalStorageDirectory().toString() + "/CashFlow/temp/tempDB.csv";
		String outFilePath = Environment.getExternalStorageDirectory().toString() + "/CashFlow/ExportData/" + fileName + ".xls";
		String thisLine;

		FileInputStream fis = null;
		//DataInputStream myInput = null;
		BufferedReader reader = null;
		
		try {
			fis = new FileInputStream(inFilePath);
			//myInput = new DataInputStream(fis);
			reader = new BufferedReader(new InputStreamReader(fis));
			arList = new ArrayList<ArrayList<String>>();
			
			while ((thisLine = reader.readLine()) != null) {
				al = new ArrayList<String>();
				String strar[] = thisLine.split(",");
				for (int j = 0; j < strar.length; j++) {
					al.add(strar[j]);
				}
				arList.add(al);
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
			System.out.println("close stream");
			if(fis != null) fis.close();
			if(reader != null) reader.close();
			//if(myInput != null) myInput.close();
			
			throw e;
		} finally {
			fis.close();
			reader.close();
			//myInput.close();
		}

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			setCellStyles(hwb); //set Style
			HSSFSheet sheet = hwb.createSheet("Report");
			
			sheet.setColumnWidth(0, 6000); 
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 3500);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 3500);
			
			for (int k = 0; k < arList.size(); k++) {
				ArrayList<?> ardata = (ArrayList<?>) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					HSSFCell cell = row.createCell(p);
					String data = ardata.get(p).toString();
					data = data.replaceAll("\"", "");
					
					if(k > 0){
						//if(data.matches("^[1-9]+$")) {
						if(data.matches("^[0-9]*(\\.[0-9]+)?$")) {
							//System.out.println("masuk regex");
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							cell.setCellValue(Double.valueOf(data));
						} else cell.setCellValue(data);
					} else cell.setCellValue(data);
					
//					if (data.startsWith("=")) {
//						cell.setCellType(Cell.CELL_TYPE_STRING);
//						data = data.replaceAll("\"", "");
//						data = data.replaceAll("=", "");
//						cell.setCellValue(data);
//					} else if (data.startsWith("\"")) {
//						data = data.replaceAll("\"", "");
//						cell.setCellType(Cell.CELL_TYPE_STRING);
//						cell.setCellValue(data);
//					} else {
//						data = data.replaceAll("\"", "");
//						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(data);
//					}
					
					if(k==0) cell.setCellStyle(csBold);
					else {
						if(p==1 || p==2 || p==4) {
							/*if(!data.equals("-"))*/ cell.setCellStyle(csRight);
						}
					}
					
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
		
	}
	
	private void setCellStyles(Workbook wb) {

		// font size 10
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 10);

		// Simple style
		cs = wb.createCellStyle();
		cs.setFont(f);

		// Bold Fond
		Font bold = wb.createFont();
		bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		bold.setFontHeightInPoints((short) 10);

		// Bold style
		csBold = wb.createCellStyle();
		csBold.setBorderBottom(CellStyle.BORDER_THIN);
		csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBold.setFont(bold);
		csBold.setAlignment(CellStyle.ALIGN_CENTER);

		// Setup style for Top Border Line
		csTop = wb.createCellStyle();
		csTop.setBorderTop(CellStyle.BORDER_THIN);
		csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTop.setFont(f);

		// Setup style for Right Border Line
		csRight = wb.createCellStyle();
		//csRight.setBorderRight(CellStyle.BORDER_THIN);
		//csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csRight.setAlignment(CellStyle.ALIGN_RIGHT);
		csRight.setFont(f);
		csRight.setDataFormat(HSSFDataFormat.getBuiltinFormat(/*"#,##0"*/ "#,##0.00"));

		// Setup style for Bottom Border Line
		csBottom = wb.createCellStyle();
		csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottom.setFont(f);

		// Setup style for Left Border Line
		csLeft = wb.createCellStyle();
		csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csLeft.setFont(f);

		// Setup style for Top/Left corner cell Border Lines
		csTopLeft = wb.createCellStyle();
		csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setFont(f);

		// Setup style for Top/Right corner cell Border Lines
		csTopRight = wb.createCellStyle();
		csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setFont(f);

		// Setup style for Bottom/Left corner cell Border Lines
		csBottomLeft = wb.createCellStyle();
		csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setFont(f);

		// Setup style for Bottom/Right corner cell Border Lines
		csBottomRight = wb.createCellStyle();
		csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setFont(f);

	}

}
