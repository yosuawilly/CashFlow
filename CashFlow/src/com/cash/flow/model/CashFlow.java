package com.cash.flow.model;

import java.io.Serializable;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.cash.flow.database.dao.CashFlowDao;
import com.cash.flow.util.MyCalendar;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_cash_flow", daoClass=CashFlowDao.class)
public class CashFlow implements Serializable, Parcelable{
	
	private static final long serialVersionUID = -289037265889693689L;
	
	public static final String NOMINAL_COLUMN = "nominal";
	public static final String DESCRIPTION_COLUMN = "description";
	public static final String TIMESTAMP_COLUMN = "timestamp";
	public static final String TYPECASH_COLUMN = "typeCash";
	public static final String BALANCE_COLUMN = "balance";
	public static final String[]ALL_COLUMN_KEYS = {
		TIMESTAMP_COLUMN,
		TYPECASH_COLUMN,
		NOMINAL_COLUMN,
		DESCRIPTION_COLUMN,
		BALANCE_COLUMN
	};
	
	//@DatabaseField(generatedId=true)
	//private int id;

	@DatabaseField(dataType=DataType.LONG)
	private long nominal;
	
	@DatabaseField(dataType=DataType.STRING)
	private String description;
	
	@DatabaseField(id=true, dataType=DataType.DATE_LONG) //for compare with query between
	private Date timestamp;
	
	@DatabaseField(dataType=DataType.ENUM_STRING)
	private CashType typeCash;
	
	@DatabaseField(dataType=DataType.LONG)
	private long balance;
	
	public static enum CashType {
		CASH_IN ("Cash In"),
		CASH_OUT ("Cash Out");
		
		private final String name;
		
		private CashType(String name) {
			this.name = name;
		}
		
		public boolean equalsName(String otherName){
	        return (otherName == null) ? false : name.equals(otherName);
	    }
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	public CashFlow() {
		this.timestamp = new Date();
	}
	
//	public int getId() {
//		return id;
//	}
//	
//	public void setId(int id) {
//		this.id = id;
//	}

	public long getNominal() {
		return nominal;
	}

	public void setNominal(long nominal) {
		this.nominal = nominal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public CashType getTypeCash() {
		return typeCash;
	}

	public void setTypeCash(CashType typeCash) {
		this.typeCash = typeCash;
	}
	
	public long getBalance() {
		return balance;
	}
	
	public void setBalance(long balance) {
		this.balance = balance;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public CashFlow(Parcel in) {
		this.nominal = in.readLong();
		this.description = in.readString();
		this.timestamp = MyCalendar.toDate(in.readString());
		this.typeCash = CashType.values()[in.readInt()];
		this.balance = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(nominal);
		dest.writeString(description);
		dest.writeString(MyCalendar.toString(timestamp));
		dest.writeInt(typeCash.ordinal());
		dest.writeLong(balance);
	}
	
	public static final Parcelable.Creator<CashFlow> CREATOR = new Parcelable.Creator<CashFlow>() {

		@Override
		public CashFlow createFromParcel(Parcel source) {
			return new CashFlow(source);
		}

		@Override
		public CashFlow[] newArray(int size) {
			return new CashFlow[size];
		}
	};

}
