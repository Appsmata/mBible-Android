package com.jackson_siro.mbible;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Settings3 extends ActionBarActivity {
    
    private TextView Installed_on;
    private TextView Installed_descri;
    private LinearLayout Instruct;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_iii);
        SharedPreferences vSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Installed_descri = (TextView) findViewById(R.id.installed_desc);
        Installed_on = (TextView) findViewById(R.id.installed_on);
        Instruct = (LinearLayout) findViewById(R.id.vsb_instr);
        
        long used_time_l, rem_time_l, expires_l;
        int used_time_i, rem_time_i, expired_i;

        used_time_l = System.currentTimeMillis() - vSettings.getLong("js_vsb_first_data", 0);
        rem_time_l = vSettings.getLong("js_vsb_expire_data", 0) - System.currentTimeMillis();
        expires_l = System.currentTimeMillis() - vSettings.getLong("js_vsb_expire_data", 0);

        used_time_i = (int)(used_time_l / 1000);
        rem_time_i = (int)(rem_time_l / 1000);
        expired_i = (int)(expires_l / 1000);
        
        if (used_time_i < 60) Installed_on.setText("Installed: " + used_time_i + " seconds ago");
        else if (used_time_i < 3600) Installed_on.setText("Installed: " + used_time_i / 60 + " minutes ago");
        else if (used_time_i < 86400) Installed_on.setText("Installed: " + used_time_i / 3600 + " hours ago");
        else if (used_time_i < 604800) Installed_on.setText("Installed: " + used_time_i / 86400 + " days ago");
        else if (used_time_i < 2419200) Installed_on.setText("Installed: " + used_time_i / 604800 + " weeks ago");
        else if (used_time_i < 29030400) Installed_on.setText("Installed: " + used_time_i / 2419200 + " months ago");
        else if (used_time_i > 29030400) Installed_on.setText("Installed: " + used_time_i / 29030400 + " years ago");
          
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_is_paid", false))
        {
            setTitle("Evaluation Mode"); 
        	
        	HowToPayMessage();
            if (used_time_i < 440000)
            {
                if (rem_time_i < 60) Installed_descri.setText("Expires: " + rem_time_i + " seconds from Now");
                else if (rem_time_i < 3600) Installed_descri.setText("Expires: " + rem_time_i / 60 + " minutes from Now");
                else if (rem_time_i < 86400) Installed_descri.setText("Expires: " + rem_time_i / 3600 + " hours from Now");
                else if (rem_time_i < 440000) Installed_descri.setText("Expires: " + rem_time_i / 86400 + " days from Now");
            }
            else if (used_time_i > 440000)
            {
                if (expired_i < 60) Installed_descri.setText("Expired: " + expired_i + " seconds ago");
                else if (expired_i < 3600) Installed_descri.setText("Expired: " + expired_i / 60 + " minutes ago");
                else if (expired_i < 86400) Installed_descri.setText("Expired: " + expired_i / 3600 + " hours ago");
                else if (expired_i < 440000) Installed_descri.setText("Expired: " + expired_i / 86400 + " days ago");
            }
        }
        else if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("js_vsb_is_paid", false)) {
        {
            setTitle("Premium Mode");
            Installed_descri.setText("God bless you so much for staying with mBible for all that time. More updates coming soon, so stay put.");
            
            Instruct.setVisibility(View.GONE);
        }
            
    }
        
  }

    public void HowToPayMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Settings3.this);
    	builder.setTitle("Upgrade to Premium Now");
        builder.setMessage("God bless you. \n Upgrade your mBible to Premium by Paying "
          + "Kshs. 250 to the developer via: \n - M-PESA to 0711474787 \n -  AIRTELMONEY "
          + "to 0731973180 \n or Alternatively use you can use PAYPAL to: \n "
          + "smataweb@gmail.com. \n\n You may opt to pay directly to the developer's "
          + "Bank Account: \n BANK: Cooperative Bank of Kenya\n ACC.NO: 01108614130800 "
          + "\n NAME: Jackson Silla Siro \n\n Once done your app automatically unlocks if "
          + "you used MOBILE MONEY. Otherwise inform the developer on SMS or "
          + "WHATSAPP +254711474787 or email: smataweb@gmail.com.\n\n For help "
          + "SMS/WHATSAPP on +254711474787 or Email on smataweb@gmail.com. God bless you.");
        
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {					
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//YouRatedMeYes();
				
			}
		});
        
        builder.show();
	
	  }
	
}