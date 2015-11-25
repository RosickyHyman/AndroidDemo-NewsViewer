package com.hyman.newsviewer.utils;

import android.content.Context;


public class DensityUtils {

	
	/*
	 * dipתPX
	 * */
	public static int dp2px(Context ctx,float dp){
		
		float density = ctx.getResources().getDisplayMetrics().density;
	
		int px=(int) (density*dp+0.5f);//+0.5f��Ϊ����������
		return px;
	}
	/*
	 * PXתdip
	 */
	public static float px2dp(Context ctx,int px) {
		
		float density = ctx.getResources().getDisplayMetrics().density;

		float dp=px/density;
		return dp;
		
	}
	
}
