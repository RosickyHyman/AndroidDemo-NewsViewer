package com.hyman.newsviewer.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyman.newsviewer.base.BaseMenuDetailPager;



/*
 *�˵�����ҳ-����
 * */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

	public InteractMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		
		TextView tv = new TextView(mActivity);
		tv.setText("��������ҳ-����");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
	}

}
