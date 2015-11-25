package com.hyman.newsviewer.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyman.newsviewer.base.BasePager;

public class SettingPager extends BasePager {

	
	public SettingPager(Activity activity) {

			super(activity);
	
	}
	
	public void initData(){
		
		tvTitle.setText("…Ë÷√");
		ibMenu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView tv = new TextView(mActivity);
		tv.setText("…Ë÷√");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		flContent.addView(tv);
		
	}
	
	
}
