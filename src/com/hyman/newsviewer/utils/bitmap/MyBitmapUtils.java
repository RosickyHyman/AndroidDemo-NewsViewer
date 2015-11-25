package com.hyman.newsviewer.utils.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {

	
	NetCacheUtils mNetCacheUtils;
	LocalCacheUtils mLocalCacheUtils;
	MemoryCacheUtils mMemoryCacheUtils;

	public MyBitmapUtils(Activity mActivity) {
		mMemoryCacheUtils=new MemoryCacheUtils();
		mLocalCacheUtils=new LocalCacheUtils();
		mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
	}

	public void display(ImageView iv_pic, String url) {
		
		Bitmap bitmap =null;
		
		bitmap=mMemoryCacheUtils.getBitmapFromMemory(url);
		if (bitmap!=null) {
			iv_pic.setImageBitmap(bitmap);
			System.out.println("从内存中拿到数据啦");
			return;
		}
		
		
		
		//从本地拿数据
		bitmap=mLocalCacheUtils.getBitmapFromLocal(url);
		if (bitmap!=null) {
			
			iv_pic.setImageBitmap(bitmap);
			System.out.println("从本地读取数据");
			mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
			return;
			
		}
		
		//从网络拿数据
		mNetCacheUtils.getBitmapFromNet(iv_pic,url);
	}

	
	
	
}
