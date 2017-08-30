package com.jackson_siro.mbible.adaptor;

import com.jackson_siro.mbible.*;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String>{
    	
		private final Activity context;
		private final String[] strArray1, strArray2, strArray3;
		
	public CustomList(Activity context,	String[] strArray1, String[] strArray2, String[] strArray3) {
		super(context, R.layout.bible_result, strArray1);
		this.context = context;
		this.strArray1 = strArray1;
		this.strArray2 = strArray2;
		this.strArray3 = strArray3;
	
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.bible_result, null, true);
		TextView bkText = (TextView) rowView.findViewById(R.id.bookname_i);
		TextView bkTexti = (TextView) rowView.findViewById(R.id.bookname_ii);
		TextView bkTextii = (TextView) rowView.findViewById(R.id.abbrev);
		bkText.setText(strArray1[position]);
		bkTexti.setText(strArray2[position]);
		bkTextii.setText(strArray3[position]);
	
		return rowView;
	
	}
}