package com.hyman.newsviewer.utils.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCacheUtils {
	
	
	private LruCache<String, Bitmap> mMemoryCache;

	public MemoryCacheUtils(){
		
		long maxSize = Runtime.getRuntime().freeMemory()/8;
		mMemoryCache = new LruCache<String, Bitmap>((int) maxSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				
				int byteCount=value.getRowBytes()*value.getHeight();
				
				return byteCount;
			}
		};
		
	}

	public void setBitmapToMemory(String url, Bitmap result) {
		
		mMemoryCache.put(url, result);
		
	}

	public Bitmap getBitmapFromMemory(String url) {

		return mMemoryCache.get(url);
	}

}
