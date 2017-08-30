package com.jackson_siro.mbible;

import java.io.File;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.appcompat.*;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

public class Aaa extends Activity {
	NotificationCompat.Builder notification;
	PendingIntent pIntent;
	NotificationManager manager;
	Intent resultIntent;
	TaskStackBuilder stackBuilder;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		createDirIfNotExits("AppSmata/mBible");		
		
		if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_first_use", false)) {
			Toast.makeText(this, "Welcome to mBible", Toast.LENGTH_SHORT).show();
		    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		    localEditor.putBoolean("js_vsb_first_use", true);
		    localEditor.putBoolean("js_vsb_is_paid", true);
		    localEditor.putLong("js_vsb_first_data", System.currentTimeMillis());
		    localEditor.putLong("js_vsb_expire_data", System.currentTimeMillis() + 440000000);
		    
		    localEditor.commit();
		    Toast.makeText(this, "Getting Ready", Toast.LENGTH_SHORT).show();
		}
		
		startActivity(new Intent(Aaa.this, AaaAb.class));
		finish();
	}
	
	@SuppressWarnings("unused")
	private boolean isExternalStoragepresent(){
		
		boolean mExternalStorageAvailable= false;
		boolean mExternalStorageWritable= false;
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)){
			mExternalStorageAvailable = mExternalStorageWritable = true; 
			
		} else {
			if(!((mExternalStorageAvailable) && (mExternalStorageWritable))){
			} return (mExternalStorageAvailable) && (mExternalStorageWritable);
		}
		return mExternalStorageWritable;
		
	}
	
	public static boolean createDirIfNotExits (String path){		
		boolean ret =true;
		File file = new File(Environment.getExternalStorageDirectory(),path);
		if (!file.exists()){
			if (!file.mkdirs()){
				Log.e("AppSmata::", "Problem Creating AppSmata Folder");
				ret = false;
			}
		}
		return ret;
	}
		
}