package com.hyman.newsviewer.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyman.newsviewer.base.BaseMenuDetailPager;



/*
 *菜单详情页-互动
 * */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

	public InteractMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		
		TextView tv = new TextView(mActivity);
		tv.setText("新闻详情页-互动");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
	}

}
