package com.cash.flow.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.model.CashFlow;
import com.cash.flow.model.CashFlow.CashType;
import com.cash.flow.util.Constant;
import com.cash.flow.util.MyCalendar;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

public class DatabaseExportImport {
	
	public static final String TAG = DatabaseExportImport.class.getName();
	 
    /** Directory that files are to be read from and written to **/
    protected static final File DATABASE_DIRECTORY = new File(Environment.getExternalStorageDirectory(), Constant.BACKUP_DATABASE_FOLDER);
    protected static final String CASHFLOW_TABLE_NAME = "t_cash_flow";
 
    /** File path of Db to be imported **/
    //protected static final File IMPORT_FILE = new File(DATABASE_DIRECTORY,"MyDb.db");
 
    /*public static final String PACKAGE_NAME = "com.example.app";
    public static final String DATABASE_NAME = "example.db";
    public static final String DATABASE_TABLE = "entryTable";*/
 
    /** Contains: /data/data/com.example.app/databases/example.db **/
    /*private static final File DATA_DIRECTORY_DATABASE = new File(Environment.getDataDirectory() + "/data/" + PACKAGE_NAME +
            "/databases/" + DATABASE_NAME );*/
 
    /** Saves the application database to the
     * export directory under MyDb.db **/
    public static boolean exportDb(Context context){
        if( ! SdIsPresent() ) return false;
        
        ContextWrapper contextWrapper = new ContextWrapper(context);
 
        //File dbFile = DATA_DIRECTORY_DATABASE;
        File dbFile = contextWrapper.getDatabasePath(Constant.DB_NAME);
        String filename = "Backup_"+MyCalendar.toString(new Date(), Constant.FORMAT_DATE_DDMMYYYY_2);
 
        File exportDir = DATABASE_DIRECTORY;
        File file = new File(exportDir, filename);
 
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
 
        try {
        	if(file.exists()) file.delete();
            file.createNewFile();
            copyFile(dbFile, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
 
    /** Replaces current database with the IMPORT_FILE if
     * import database is valid and of the correct type **/
    public static boolean restoreDb(Context context, File importFile){
        if( ! SdIsPresent() ) return false;
        
        ContextWrapper contextWrapper = new ContextWrapper(context);
 
        //File exportFile = DATA_DIRECTORY_DATABASE;
        File exportFile = contextWrapper.getDatabasePath(Constant.DB_NAME);
        System.out.println(exportFile.getAbsolutePath());
        //File importFile = IMPORT_FILE;
 
        if( ! checkDbIsValid(importFile) ) return false;
 
        if (!importFile.exists()) {
            Log.d(TAG, "File does not exist");
            return false;
        }
 
        try {
        	if(exportFile.exists()) exportFile.delete();
            exportFile.createNewFile();
            copyFile(importFile, exportFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
 
    /** Imports the file at IMPORT_FILE **/
    public static boolean importIntoDb(Context ctx, File importFile){
        if( ! SdIsPresent() ) return false;
        
        new ContextWrapper(ctx).deleteDatabase(Constant.DB_NAME);
 
        //File importFile = IMPORT_FILE;
 
        if( ! checkDbIsValid(importFile) ) return false;
 
        try{
            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase
                    (importFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
 
            Cursor cursor = sqlDb.query(true, CASHFLOW_TABLE_NAME,
                    null, null, null, null, null, null, null
            );
 
            CashFlowDao cashFlowDao = CashFlowDao.getInstance(ctx);
 
            // Adds all items in cursor to current database
            cursor.moveToPosition(-1);
            while(cursor.moveToNext()){
                CashFlow cashFlow = new CashFlow();
                cashFlow.setTimestamp(new Date(cursor.getLong(cursor.getColumnIndex(CashFlow.TIMESTAMP_COLUMN))));
                cashFlow.setTypeCash(CashType.valueOf(cursor.getString(cursor.getColumnIndex(CashFlow.TYPECASH_COLUMN))));
                cashFlow.setNominal(cursor.getLong(cursor.getColumnIndex(CashFlow.NOMINAL_COLUMN)));
                cashFlow.setDescription(cursor.getString(cursor.getColumnIndex(CashFlow.DESCRIPTION_COLUMN)));
                cashFlow.setBalance(cursor.getLong(cursor.getColumnIndex(CashFlow.BALANCE_COLUMN)));
                
                cashFlowDao.createData(cashFlow);
            }
 
            sqlDb.close();
            cursor.close();
            cashFlowDao.closeConnection();
        } catch( Exception e ){
            e.printStackTrace();
            return false;
        }
 
        return true;
    }
 
    /** Given an SQLite database file, this checks if the file
     * is a valid SQLite database and that it contains all the
     * columns represented by DbAdapter.ALL_COLUMN_KEYS **/
    protected static boolean checkDbIsValid( File db ){
        try{
            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase
                (db.getPath(), null, SQLiteDatabase.OPEN_READONLY);
 
            Cursor cursor = sqlDb.query(true, CASHFLOW_TABLE_NAME,
                    null, null, null, null, null, null, null
            );
 
            // ALL_COLUMN_KEYS should be an array of keys of essential columns.
            // Throws exception if any column is missing
            for( String s : CashFlow.ALL_COLUMN_KEYS ){
                cursor.getColumnIndexOrThrow(s);
            }
 
            sqlDb.close();
            cursor.close();
        } catch( IllegalArgumentException e ) {
            Log.d(TAG, "Database valid but not the right type");
            e.printStackTrace();
            return false;
        } catch( SQLiteException e ) {
            Log.d(TAG, "Database file is invalid.");
            e.printStackTrace();
            return false;
        } catch( Exception e){
            Log.d(TAG, "checkDbIsValid encountered an exception");
            e.printStackTrace();
            return false;
        }
 
        return true;
    }
 
    @SuppressWarnings("resource")
	private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
 
    /** Returns whether an SD card is present and writable **/
    public static boolean SdIsPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    
}
