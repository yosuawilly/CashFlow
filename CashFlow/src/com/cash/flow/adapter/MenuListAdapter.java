package com.cash.flow.adapter;

import com.cash.flow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<String> {
	
	private Context context;
	private String[] items;
	private int[]idIcons;

	public MenuListAdapter(Context context, String[] items) {
		this(context, items, null);
	}
	
	public MenuListAdapter(Context context, String[] items, int[]idIcons) {
		super(context, R.layout.custom_menu_row_component, items);
		this.context = context;
		this.items = items;
		this.idIcons = idIcons;
	}
	
	@Override
	public String getItem(int position) {
		return items[position];
	}
	
	@Override
	public int getCount() {
		return items.length;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.custom_menu_row_component, null);
		}
		
		String o = items[position];
		if(o != null) {
			TextView menuText = (TextView) v.findViewById(R.id.menuText);
			menuText.setText(o);
		}
		
		if(idIcons != null) {
			if(idIcons.length == items.length) {
				ImageView icon = (ImageView)v.findViewById(R.id.leftMenuIcon);
				icon.setBackgroundResource(idIcons[position]);
			}
		}
		
		return v;
	}

}
