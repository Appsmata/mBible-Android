package com.jackson_siro.mbible;

import android.support.v7.app.ActionBarActivity;

import com.jackson_siro.mbible.bibles.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class Bb_Loading extends ActionBarActivity {

    //private TextView mTextView;
    //private ListView mListView;
    //private static final String TAG = "SongList";
    private Handler customHandler = new Handler();
	
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	
	private TextView TitleLoad, Counting;
	private RelativeLayout Tracking, Proceeding;
	private ImageView Splashme;
	private long startTime = 0L;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_bible_load);
        
		TitleLoad = (TextView) findViewById(R.id.TitleLoad);
		Counting = (TextView) findViewById(R.id.countThis);
		
		Tracking =  (RelativeLayout) findViewById(R.id.Tracking);
		Proceeding = (RelativeLayout) findViewById(R.id.NextOne);
		Splashme = (ImageView) findViewById(R.id.splashme);

        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    localEditor.putBoolean("js_vsb_finished_loading", true);
	    localEditor.commit();
	    
		Cursor cursor = managedQuery(BibleProvider.CONTENT_URI, null, null,
        		new String[] {"Genesis"}, null);

        if (cursor == null) {
        	startTime = SystemClock.uptimeMillis();
        	customHandler.postDelayed(updateTimerThread, 3000);
		} else {
			//int count = cursor.getCount();
			startTime = SystemClock.uptimeMillis();
        	customHandler.postDelayed(updateTimerThread, 3000);
		}
		
        //Toast.makeText(this, count + " songs", Toast.LENGTH_SHORT).show();
        /*
        if (count < 700){        	
    	    
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        	
        } 	else if (count < 950){
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        	
        }	else if (count < 1900){
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        	
        }	else if (count < 2300){
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        	
        }	else if (count < 3000){
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        	        	
        }	else if (count < 3600){
        	startTime = SystemClock.uptimeMillis();
    		customHandler.postDelayed(updateTimerThread, 3000);
        } else {
        	
        	Tracking.setVisibility(View.GONE);
			Proceeding.setVisibility(View.VISIBLE);
			
        } */
    }
    
    private Runnable updateTimerThread = new Runnable() {

		public void run() {
			
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			
			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			
	        //String reach = count / 3600 * 100 +" %";
			//Counting.setText(count + " songs so far ...");
			customHandler.postDelayed(this, 3000);
			
			if (secs < 5){
	        	TitleLoad.setText("Now Loading: 5 %");
	        	Counting.setText("Let's Begin...");
	        	Splashme.setImageResource(R.drawable.splash_i);
	        	
	        } 	else if (secs < 10){
	        	TitleLoad.setText("Loading Songbook: 10 %");
	        	Counting.setText("Songs of Worship...");
	        	Splashme.setImageResource(R.drawable.splash_ii);
	        	
	        }	else if (secs < 30){
	        	TitleLoad.setText("Loading Songbook: 30 %");
	        	Counting.setText("Believers Songs...");
	        	Splashme.setImageResource(R.drawable.splash_iii);
	        	
	        }	else if (secs < 50){
	        	TitleLoad.setText("Loading Songbook: 50 %");
	        	Counting.setText("Redemption Songs...");
	        	Splashme.setImageResource(R.drawable.splash_iv);
	        	
	        }	else if (secs < 65){
	        	TitleLoad.setText("Patience pays: 65 %");
	        	Counting.setText("Chichewa Songs...");
	        	Splashme.setImageResource(R.drawable.splash_v);
	        	        	
	        }	else if (secs < 80){
	        	TitleLoad.setText("Almost Done Loading: 80 %");
	        	Counting.setText("Thanks for your Patience...");
	        	Splashme.setImageResource(R.drawable.splash_vi);
	        	
	        }	else if (secs < 95){
	        	TitleLoad.setText("Just Finalizing: 95 %");
	        	Counting.setText("Let's Finish...");
	        	Splashme.setImageResource(R.drawable.splash_vii);
	        	
	        } else if (secs > 100){
	        	timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);
	        	Splashme.setImageResource(R.drawable.splash_viii);
	        	
	        	//setContentView(new MainGamePanel(this));
	        	//TitleLoad.setText("Done Loading Bible: " + " | 100 %");
				//Counting.setText("Thanks for your patience.");
				
				SaveThis();
				
	        }
		}

	};
	
	public void SaveThis(){
		Tracking.setVisibility(View.GONE);
		Proceeding.setVisibility(View.VISIBLE);
	}
	
	public void StartSinging(View view)   {
		Intent intent = new Intent(this, Bible.class);
		startActivity(intent);		
    }
    
}
