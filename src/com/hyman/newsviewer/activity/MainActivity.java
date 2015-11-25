package com.hyman.newsviewer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.fragment.ContentFragment;
import com.hyman.newsviewer.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT = "fragment_content";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	/*
	 * 初始化页面，设置侧边栏，并给予其宽度
	 * */
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);
		SlidingMenu slidingMenu = getSlidingMenu();
		
		//拿到屏幕宽度
		WindowManager windowManager = getWindowManager();
		int width = windowManager.getDefaultDisplay().getWidth();
		//设置侧边栏宽度为屏幕宽度的3/7
		slidingMenu.setBehindOffset(width*200/320);
		//设置侧边栏全屏可以滑动
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		initFragment();
		
	}
	
	
	/*
	 * 初始化fragment，将数据填充给布局文件
	 * */

	private void initFragment() {
		
		FragmentManager fragement = getSupportFragmentManager();
		FragmentTransaction transaction = fragement.beginTransaction();
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);
		transaction.commit();
		
	}

	/*
	 * 获取侧边栏对象
	 * */
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fm= getSupportFragmentManager();
		LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
		return fragment;
	}
	
	/*
	 * 获取主页对象
	 * */
	public ContentFragment getContentFragment(){
		FragmentManager fm= getSupportFragmentManager();
		ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);
		return fragment;
		
	}
}
