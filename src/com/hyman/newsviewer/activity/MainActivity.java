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
	 * ��ʼ��ҳ�棬���ò����������������
	 * */
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);
		SlidingMenu slidingMenu = getSlidingMenu();
		
		//�õ���Ļ���
		WindowManager windowManager = getWindowManager();
		int width = windowManager.getDefaultDisplay().getWidth();
		//���ò�������Ϊ��Ļ��ȵ�3/7
		slidingMenu.setBehindOffset(width*200/320);
		//���ò����ȫ�����Ի���
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		initFragment();
		
	}
	
	
	/*
	 * ��ʼ��fragment�����������������ļ�
	 * */

	private void initFragment() {
		
		FragmentManager fragement = getSupportFragmentManager();
		FragmentTransaction transaction = fragement.beginTransaction();
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);
		transaction.commit();
		
	}

	/*
	 * ��ȡ���������
	 * */
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fm= getSupportFragmentManager();
		LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
		return fragment;
	}
	
	/*
	 * ��ȡ��ҳ����
	 * */
	public ContentFragment getContentFragment(){
		FragmentManager fm= getSupportFragmentManager();
		ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);
		return fragment;
		
	}
}
