package com.jackson_siro.mbible.bible_en;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jackson_siro.mbible.*;
import com.jackson_siro.mbible.adaptor.*;
import com.jackson_siro.mbible.bibles.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class EnBibleNew extends ListFragment {    
	String[] BookID, BookEng, BookSwa, Abbrevs;
	
	List<HolyBible> mylist, mylistid, mylisteng, mylistswa, mylistabb;
	ArrayList<HashMap<String, String>> old_testament;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	SQLiteHelper db = new SQLiteHelper(getActivity());
    	mylist = db.getBibleTestament("2");
    	
    	List<String> listId = new ArrayList<String>();
		List<String> listEnglish = new ArrayList<String>();
		List<String> listAbbrevs = new ArrayList<String>();
		List<String> listSwahili = new ArrayList<String>();
		
		for (int i = 0; i < mylist.size(); i++) {
			listId.add(i, Integer.toString(mylist.get(i).getId()));
			listEnglish.add(i, mylist.get(i).getEnglish());
			listAbbrevs.add(i, mylist.get(i).getAbbrevs());
			listSwahili.add(i, mylist.get(i).getSwahili());
		}
		
		BookID = listId.toArray(new String[listId.size()]);		
		for (String string : BookID) {	System.out.println(string);	}
		
		BookEng = listEnglish.toArray(new String[listEnglish.size()]);		
		for (String stringi : BookEng) {	System.out.println(stringi);	}
		
		BookSwa = listSwahili.toArray(new String[listSwahili.size()]);		
		for (String stringii : BookSwa) {	System.out.println(stringii);	}

		Abbrevs = listAbbrevs.toArray(new String[listAbbrevs.size()]);		
		for (String stringiii : Abbrevs) {	System.out.println(stringiii);	}

    	CustomList mListView = new CustomList(getActivity(), BookEng, BookSwa, Abbrevs);        
		setListAdapter(mListView);	
		
        return super.onCreateView(inflater, container, savedInstanceState);

    }  
    
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                
                Intent myintent = new Intent(getActivity().getApplicationContext(), EnBibleChoose.class);
                myintent.putExtra("book", Integer.parseInt(BookID[+ position]));
                startActivity(myintent);
            }
            
        });
    }

}