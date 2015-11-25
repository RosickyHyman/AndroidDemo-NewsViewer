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
	 * 1.��һ��ҳ����Ҫ���ؼ�����
	 * 2.���һ��ҳ����Ҫ���ؼ�����
	 * 3.���»���Ҫ���ؼ�����
	 * false��ʾ��Ҫ���أ�true��ʾ����Ҫ����
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
				//���һ���
				if (dX>0) {
					//���һ���,��Ϊ��һ��ҳ����Ҫ���ؼ�����
					if (getCurrentItem()==0) {
						getParent().requestDisallowInterceptTouchEvent(false);	
					}
				}else {
					//���󻬣���Ϊ���һ��ҳ����Ҫ���ؼ�����
					if (getCurrentItem()==getAdapter().getCount()-1) {
						
						getParent().requestDisallowInterceptTouchEvent(false);
					}					
				}
			}else {
				//���»�������Ҫ���ؼ�����
				getParent().requestDisallowInterceptTouchEvent(false);		
			}			
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	

}
