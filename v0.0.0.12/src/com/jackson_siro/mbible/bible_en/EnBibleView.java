package com.jackson_siro.mbible.bible_en;

import com.jackson_siro.mbible.*;
import com.jackson_siro.mbible.bibles.*;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("deprecation")
public class EnBibleView extends ActionBarActivity {

    private ListView mListView;
    String MB_SETTINGS, CurrBook, Curr_Chap;
	SharedPreferences settings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bible_read);
        settings = getSharedPreferences(MB_SETTINGS, 12);
        
        mListView = (ListView) findViewById(R.id.verses);

        CurrBook = PreferenceManager.getDefaultSharedPreferences(this).getString("js_mb_curr_book", "Genesis");
        Curr_Chap = PreferenceManager.getDefaultSharedPreferences(this).getString("js_mb_curr_chapter", "1");
        
        setTitle(CurrBook + " " + Curr_Chap);
        showResults(CurrBook + " " + Curr_Chap + ":");
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // handles a click on a search suggestion; launches activity to show word
            //Intent wordIntent = new Intent(this, EnBibleChoose.class);
            //wordIntent.setData(intent.getData());
            //startActivity(wordIntent);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    private void showResults(String query) {

        @SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(BibleDbProv.CONTENT_URI, null, null, new String[] {query}, null);

        if (cursor == null) {
            // There are no results
            //mTextView.setText(getString(R.string.no_results, new Object[] {query}));
        } else {
            
            String[] from = new String[] { BibleDbase.BIBLE_CONTENT };
            int[] to = new int[] { R.id.versetxt };

            SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.bible_reader, cursor, from, to);
            mListView.setAdapter(words);
            
            mListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                }
            });
        }
    }

	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search: 
                onSearchRequested();
                return true;
            case R.id.favours:
                startActivity(new Intent(this, Favourites.class));
                return true;
            case R.id.helpdesk:
                startActivity(new Intent(this, HelpDesk.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            default:
                return false;
        }
    }
    
}