package com.jackson_siro.mbible;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AaaAb extends Activity {
	private long ms=0;
	private long splashTime=5000;
	private boolean splashActive = true;
	private boolean paused=false;
	RelativeLayout MySong;
	
	private TextView mytext;
	private ImageView myimage;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_b);
	    
		mytext = (TextView) findViewById(R.id.text);
	    myimage = (ImageView) findViewById(R.id.image);
	      
	    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
	    myimage.startAnimation(animation1);
	  
	    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
	    mytext.startAnimation(animation2);
	  	
	    if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_finished_loading", false)) 
	    {
			Thread mythread = new Thread() {
				public void run() {
					try {
						while (splashActive && ms < splashTime) {
							if(!paused) ms=ms+100;
							sleep(100);
						}
					} catch(Exception e) {}
					finally {
						startActivity(new Intent(AaaAb.this, AaaAbc.class));
						finish();
					}
				}
			};
			mythread.start();
			
		 } else  {
			 Thread mythread = new Thread() {
	  				public void run() {
	  					try {
	  						while (splashActive && ms < splashTime) {
	  							if(!paused) ms=ms+100;
	  							sleep(100);
	  						}
	  					} catch(Exception e) {}
	  					finally {
	  						//startActivity(new Intent(AaaAb.this, AaaAbc.class));
							startActivity(new Intent(AaaAb.this, Bible.class));
							finish();  						 
	  					}
	  				}
	  			};
	  			mythread.start();  			
		 } 
		
	}
	
}