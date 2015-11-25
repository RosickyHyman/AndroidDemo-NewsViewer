package com.hyman.newsviewer.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BasePager {

	public Activity mActivity;
	public View mRootView;
	public TextView tvTitle;
	public FrameLayout flContent;
	public ImageButton ibMenu;
	public ImageButton ibphoto;

	public BasePager(Activity activity) {
		mActivity = activity;
		initView();
		
	}

	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		tvTitle = (TextView) mRootView.findViewById(R.id.tv_base_title);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_base_content);
		ibMenu = (ImageButton) mRootView.findViewById(R.id.ib_base_menu);
		ibphoto = (ImageButton) mRootView.findViewById(R.id.ib_base_photo);
		
		ibMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				toggleSlidingMenu();
			}
		});

	}
	
	/*
	 * 隐藏显示侧边栏
	 * */
	protected void toggleSlidingMenu() {

		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();	
	}

	
	
	public void initData() {

	}
	
	
	/*
	 * 设置SlidingMenu可用不可用
	 * */
	public void setSlidingMenuEnable(boolean enable){
		
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	
	}

}
