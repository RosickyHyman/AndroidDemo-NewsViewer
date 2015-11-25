package com.hyman.newsviewer.utils;

import android.content.Context;

public class CacheUtils {

	/*
	 * …Ë÷√ª∫¥Ê
	 * */
	public static void setCache(Context ctx,String key,String value){
		
		PrefUtils.setString(ctx, key, value);
	}
	
	/*
	 * ªÒ»°ª∫¥Ê
	 * */
	public static String getCache(Context ctx,String key){
		
		return PrefUtils.getString(ctx, key, null);
	}
}
