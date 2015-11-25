package com.hyman.newsviewer.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyman.newsviewer.base.BasePager;

public class GovAffairsPager extends BasePager {

	
	public GovAffairsPager(Activity activity) {

			super(activity);
	
	}
	
	public void initData(){
		
		tvTitle.setText("政务");
		setSlidingMenuEnable(true);
		
		TextView tv = new TextView(mActivity);
		tv.setText("政务");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		flContent.addView(tv);
		
	}
	
	
}
