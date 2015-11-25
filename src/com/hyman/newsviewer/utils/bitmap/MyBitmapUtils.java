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
			System.out.println("���ڴ����õ�������");
			return;
		}
		
		
		
		//�ӱ���������
		bitmap=mLocalCacheUtils.getBitmapFromLocal(url);
		if (bitmap!=null) {
			
			iv_pic.setImageBitmap(bitmap);
			System.out.println("�ӱ��ض�ȡ����");
			mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
			return;
			
		}
		
		//������������
		mNetCacheUtils.getBitmapFromNet(iv_pic,url);
	}

	
	
	
}
