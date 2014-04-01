package com.cash.flow.customcomponent;

import com.cash.flow.R;
import com.cash.flow.util.Utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomKeyBoard extends LinearLayout  implements OnClickListener{
	
	private Context context;
	EditText editText;
	
	String text = "";
	private int length = 100;
	
	public CustomKeyBoard(Context context) {
		super(context);
		initViews(context, null);
	}
	
	public CustomKeyBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	public CustomKeyBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context, attrs);
	}
	
	private void initViews(Context context, AttributeSet attrs) {
		if(attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomKeyBoard);
			
			final int n = a.getIndexCount();
			for(int i=0; i<n; i++) {
				int attr = a.getIndex(i);
				
				switch (attr) {
				case R.styleable.CustomKeyBoard_maxLength:
					int length = a.getInt(attr, -1);
					if(length > -1) this.length = length;
					break;
				default:
					break;
				}
			}
			
			a.recycle();
		}
		
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.keyboard_layout, this);
		
		findViewById(R.id.backspace_key).setOnClickListener(this);
		findViewById(R.id.one_key).setOnClickListener(this);
		findViewById(R.id.two_key).setOnClickListener(this);
		findViewById(R.id.three_key).setOnClickListener(this);
		findViewById(R.id.four_key).setOnClickListener(this);
		findViewById(R.id.five_key).setOnClickListener(this);
		findViewById(R.id.six_key).setOnClickListener(this);
		findViewById(R.id.seven_key).setOnClickListener(this);
		findViewById(R.id.eight_key).setOnClickListener(this);
		findViewById(R.id.nine_key).setOnClickListener(this);
		findViewById(R.id.zero_key).setOnClickListener(this);
	}
	
	public void initCustomKeyboard(EditText editText) {
		this.editText = editText;
		this.editText.setInputType(InputType.TYPE_NULL);
		//this.length = length;
	}

	@Override
	public void onClick(View v) {
		if(this.editText != null) {
			if (v.getId()==R.id.backspace_key) {
				
				if (text.length()!=0) {
					text = text.substring(0, text.length()-1);
					this.editText.setText(text);
				}
			}
			if (text.length()<length) {
				if (v.getId()==R.id.one_key) {
					text += ((TextView)v.findViewById(R.id.one_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.two_key) {
					text += ((TextView)v.findViewById(R.id.two_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.three_key) {
					text += ((TextView)v.findViewById(R.id.three_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.four_key) {
					text += ((TextView)v.findViewById(R.id.four_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.five_key) {
					text += ((TextView)v.findViewById(R.id.five_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.six_key) {
					text += ((TextView)v.findViewById(R.id.six_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.seven_key) {
					text += ((TextView)v.findViewById(R.id.seven_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.eight_key) {
					text += ((TextView)v.findViewById(R.id.eight_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.nine_key) {
					text += ((TextView)v.findViewById(R.id.nine_text)).getText().toString();
					this.editText.setText(text);
				}else if (v.getId()==R.id.zero_key) {
					text += ((TextView)v.findViewById(R.id.zero_text)).getText().toString();
					this.editText.setText(text);
				}
			}
			
			editText.setSelection(editText.length());
		} else {
			Utility.showErrorMessage(context, "EditText not initialize");
		}
	}

}
