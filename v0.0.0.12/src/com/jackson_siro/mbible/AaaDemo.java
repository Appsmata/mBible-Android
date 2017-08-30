package com.jackson_siro.mbible;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class AaaDemo extends FragmentActivity{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_sliding);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bb_Sliding fragment = new Bb_Sliding();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
        
    }
        
    public void SingNow(View view)   {
    	if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_finished_loading", false)) {
        	startActivity(new Intent(this, Bb_Loading.class));
		} else {
	    	startActivity(new Intent(this, Bible.class));
		}
    }
}
