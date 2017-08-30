package com.jackson_siro.mbible.adaptor;

import com.jackson_siro.mbible.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomGridAdapter extends BaseAdapter {
	
	private Context context; 
	private final String[] gridValues;

	public CustomGridAdapter(Context context, String[] gridValues) {
		this.context = context;
		this.gridValues = gridValues;
	}
	
	@Override
	public int getCount() {
		return gridValues.length;
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;

		if (convertView == null) {
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.grid_item, null);			
			TextView textView = (TextView) gridView.findViewById(R.id.item_label);
			textView.setText(gridValues[position]);

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}
}
