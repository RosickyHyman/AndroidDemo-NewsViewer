package com.hyman.newsviewer.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyman.newsviewer.base.BasePager;

public class HomePager extends BasePager {

	
	public HomePager(Activity activity) {

			super(activity);
	
	}
	
	public void initData(){
		
		tvTitle.setText("Ê×Ò³");
		ibMenu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView tv = new TextView(mActivity);
		tv.setText("Ê×Ò³");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		flContent.addView(tv);
		
	}
	
	
}
