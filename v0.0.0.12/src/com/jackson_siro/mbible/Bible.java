package com.jackson_siro.mbible;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jackson_siro.mbible.bible_en.*;
import com.jackson_siro.mbible.bible_sw.*;
import com.jackson_siro.mbible.bibles.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.text.TextUtils;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Bible extends ActionBarActivity implements TabListener {
    private static final String TAG = "mBible";
	SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    SharedPreferences.Editor localEditor;
    public SQLiteHelper db = new SQLiteHelper(this);
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_book);
        localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(2);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(this.mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
        
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_rate_me", false))
        {            
            rateMePlease();            
        }

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_bible_info", false))
        {            
            new Thread(new Runnable() {
                public void run() {
                    try {
                    	addBibleInfo();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
    
    public void addBibleInfo() throws IOException  {
    	localEditor.putBoolean("js_vsb_bible_info", true);
	    localEditor.commit();
	    
    	Log.d(TAG, "Loading texts...");
    	String data = "";
    	InputStream is = this.getResources().openRawResource(R.raw.mdetails);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	if (is!=null){
    		try{
    			while ((data = reader.readLine()) != null){
    				String[] strings = TextUtils.split(data, "%");
                    if (strings.length < 2) continue;
                    db.addtoHolyBible(new HolyBible(strings[0].trim(), strings[1].trim(), strings[2].trim(),
                    		strings[3].trim(), strings[4].trim(), strings[5].trim()));
    			}
    		} finally {
                reader.close();
            }
    	}
    	
    	Log.d(TAG, "DONE loading texts.");
    }
    
    public void rateMePlease() {
    	 SharedPreferences vSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());		

         long used_time_l, rate_time_l;
         int used_time_i, rate_time_i;

         used_time_l = System.currentTimeMillis() - vSettings.getLong("js_vsb_first_data", 0);
         rate_time_l = vSettings.getLong("js_vsb_rated_me_not", 0) - System.currentTimeMillis();
         
         used_time_i = (int)(used_time_l / 1000);
         rate_time_i = (int)(rate_time_l / 1000);
         if (used_time_i > 18000)
         {            
        	 AlertDialog.Builder builder = new AlertDialog.Builder(Bible.this);
		    	builder.setTitle(R.string.just_a_min);
		        builder.setMessage(R.string.rate_me_please);
		        builder.setNegativeButton(R.string.rate_later, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						YouRatedMeNot();	
					}
				});
		        
		        builder.setPositiveButton(R.string.rate_now, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						YouRatedMeYes();
						Toast.makeText(getApplicationContext(), R.string.blessed, Toast.LENGTH_LONG).show();						
					}
				});
		        
		        builder.show();
         }
	  }
	
	public void YouRatedMeNot(){
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    localEditor.putBoolean("js_vsb_rate_me", false);
	    localEditor.putLong("js_vsb_rated_me_not", System.currentTimeMillis());
	    localEditor.commit();
     }
	
	public void YouRatedMeYes(){
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    localEditor.putBoolean("js_vsb_rate_me", true);
	    localEditor.putLong("js_vsb_rated_me_not", System.currentTimeMillis());
	    localEditor.commit();	    
	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jackson_siro.mbible")));
     }
	
    @Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
    
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int position) {
			Bundle data = new Bundle();
			switch(position){
			
			case 0:
				EnBibleOld bible1 = new EnBibleOld();				
				data.putInt("current_page", position+1);
				bible1.setArguments(data);
				return bible1;
				
			case 1:
				EnBibleNew bible2 = new EnBibleNew();
				data.putInt("current_page", position+1);
				bible2.setArguments(data);
				return bible2;	

			}
		
		return null;
		}

		
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			
			}
			return null;
		}
	}
    
    public static class PlaceholderFragment extends Fragment {
		
		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.sb_fragment, container, false);
			return rootView;
			
		}
	}


}
