package com.jackson_siro.mbible.adaptor;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.jackson_siro.mbible.*;

public class TabsPager extends PagerAdapter {
	String tabs[]={"SLIDE1", "SLIDE2", "SLIDE3",};
	
	Activity activity;
	public TabsPager(Activity activity){
		this.activity=activity;
	}
    @Override
    public int getCount() {
        return tabs.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
    	View view=null;
    	view = activity.getLayoutInflater().inflate(R.layout.bb_demo_page,container, false);
    	
        container.addView(view);
        TextView title =  (TextView) view.findViewById(R.id.item_title);
        TextView content =  (TextView) view.findViewById(R.id.item_content);
        Button singnow =  (Button) view.findViewById(R.id.proceed);
        
        if (tabs[position] == "SLIDE1") {
        	title.setText(R.string.tutorial_one_title);
        	content.setText(R.string.tutorial_one_content); 
        	singnow.setVisibility(View.GONE);
        }        
		else if (tabs[position] == "SLIDE2") {
			title.setText(R.string.tutorial_two_title);
        	content.setText(R.string.tutorial_two_content); 
        	singnow.setVisibility(View.GONE);
		}
		else if (tabs[position] == "SLIDE3") {
			title.setText(R.string.tutorial_three_title);
        	content.setText(R.string.tutorial_three_content); 
        	singnow.setVisibility(View.GONE);
		}
				
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}