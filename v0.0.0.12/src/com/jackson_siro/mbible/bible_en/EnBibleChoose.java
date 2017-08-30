package com.jackson_siro.mbible.bible_en;

import com.jackson_siro.mbible.adaptor.*;
import com.jackson_siro.mbible.bibles.*;

import java.util.ArrayList;
import java.util.List;

import com.jackson_siro.mbible.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class EnBibleChoose extends ActionBarActivity {

	GridView gridView;
	HolyBible selectedBook;
	SQLiteHelper db;
	String[] Chapters = new String[] { "1"};
	CharSequence selectedChapter = "1";
	SharedPreferences settings;
	SharedPreferences.Editor localEditor;
	
	String MB_SETTINGS, CUR_BOOK, CUR_CHAP;
	int bkchapters = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);
		gridView = (GridView) findViewById(R.id.gridView1);
		settings = getSharedPreferences(MB_SETTINGS, 1);
		localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    
		initializeViews(getIntent().getIntExtra("book", -1));
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				selectedChapter = ((TextView)v.findViewById(R.id.item_label)).getText();
		        localEditor.putString("js_mb_curr_chapter", selectedChapter.toString());
			    localEditor.commit();
			    
				Intent myintent = new Intent(EnBibleChoose.this, EnBibleView.class);
                myintent.putExtra("chapter", Integer.parseInt((String) selectedChapter));
                startActivity(myintent);
			}
		});
		
	}
	
	public void initializeViews(int book){
		db = new SQLiteHelper(getApplicationContext());
		selectedBook = db.readHolyBible(book);
		/*
		if (Integer.parseInt(selectedBook.getTestament()) == 1){
			setTitle(selectedBook.getEnglish() + "... Chapter?");
		} else {
			setTitle(selectedBook.getEnglish() + " | New Testament");
		}
		*/
		String CurrBook = selectedBook.getEnglish();
		bkchapters = (Integer.parseInt(selectedBook.getChapters())) + 1;
		
        localEditor.putString("js_mb_curr_book", CurrBook);
        localEditor.putString("js_mb_curr_chapter", "1");
	    localEditor.commit();
		setTitle(selectedBook.getEnglish() + "... Chapter?"); 
				
		List<String> listChapters = new ArrayList<String>();
		
		for (int i = 1; i < bkchapters; i++) {
			listChapters.add(Integer.toString(i));
		}
		
		Chapters = listChapters.toArray(new String[listChapters.size()]);		
		for (String string : Chapters) {	System.out.println(string);	}
		
		gridView.setAdapter(new CustomGridAdapter(this, Chapters));

	}
}