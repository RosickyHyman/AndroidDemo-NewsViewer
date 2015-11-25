package com.hyman.newsviewer.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {

	private int startX;
	private int startY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TopNewsViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 1.第一个页面需要父控件拦截
	 * 2.最后一个页面需要父控件拦截
	 * 3.上下滑需要父控件拦截
	 * false表示需要拦截，true表示不需要拦截
	 * */
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			
			int dX=endX-startX;
			int dY=endY-startY;
			
			if (Math.abs(dX)>Math.abs(dY)) {
				//左右滑动
				if (dX>0) {
					//向右滑动,且为第一个页面需要父控件拦截
					if (getCurrentItem()==0) {
						getParent().requestDisallowInterceptTouchEvent(false);	
					}
				}else {
					//向左滑，且为最后一个页面需要父控件拦截
					if (getCurrentItem()==getAdapter().getCount()-1) {
						
						getParent().requestDisallowInterceptTouchEvent(false);
					}					
				}
			}else {
				//上下滑动，需要父控件拦截
				getParent().requestDisallowInterceptTouchEvent(false);		
			}			
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	

}
