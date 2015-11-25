package com.hyman.newsviewer.utils;
import android.util.Log;

public class LogerUtils {

	public static int LOG_LEVEL=6;
	public static int VERBOS_CODE=1;
	public static int DEBUG_CODE=2;	
	public static int INFO_CODE=3;	
	public static int WARN_CODE=4;
	public static int ERROR_CODE=5;
		public static void logV(String tag ,String msg){
			if (LOG_LEVEL>VERBOS_CODE) {
				Log.v(tag, msg);
			}
		}
		public static void logD(String tag ,String msg){
			if (LOG_LEVEL>DEBUG_CODE) {
				Log.d(tag, msg);
			}
		}
		public static void logI(String tag ,String msg){
			if (LOG_LEVEL>INFO_CODE) {
				Log.i(tag, msg);
			}
		}
		public static void logW(String tag ,String msg){
			if (LOG_LEVEL>WARN_CODE) {
				Log.w(tag, msg);
			}
		}
		public static void logE(String tag ,String msg){
			if (LOG_LEVEL>ERROR_CODE) {
				Log.e(tag, msg);
			}
		}
		
	
}
