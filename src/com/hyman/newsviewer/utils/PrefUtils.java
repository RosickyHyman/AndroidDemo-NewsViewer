package com.hyman.newsviewer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

	
	public static final String mPrefs_name="config";
	
	public static void setString(Context ctx,String key, String value){
		SharedPreferences mPrefs = ctx.getSharedPreferences(mPrefs_name, Context.MODE_PRIVATE);
		mPrefs.edit().putString(key, value).commit();
	}
	
	public static String getString(Context ctx,String key,String defaultValue){
		SharedPreferences mPrefs = ctx.getSharedPreferences(mPrefs_name, Context.MODE_PRIVATE);

		return mPrefs.getString(key, defaultValue);	
	}
	
	
}
