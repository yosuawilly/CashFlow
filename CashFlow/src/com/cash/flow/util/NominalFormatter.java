package com.cash.flow.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NominalFormatter implements TextWatcher{
	
	private EditText editText;
	private String currentNominal = "";
	
	public NominalFormatter(EditText editText) {
		this.editText = editText;
	}
	
	public static void setTextNominalListener(EditText editText) {
		editText.addTextChangedListener(new NominalFormatter(editText));
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(!s.toString().equals(""))
			if(!s.toString().equals(currentNominal)){
				editText.removeTextChangedListener(this);

			     String cleanString = s.toString().replaceAll("[$,.]", "");

			     //double parsed = Double.parseDouble(cleanString);
			     //String formated = NumberFormat.getCurrencyInstance().format((parsed/100));
			     String formated = NumberUtil.toCurr2(cleanString);
			     //String formated = NumberUtil.toCurrDigitGrouping(cleanString);

			     currentNominal = formated;
			     editText.setText(formated);
			     editText.setSelection(formated.length());

			     editText.addTextChangedListener(this);
			}
	}

}
