package com.hyman.newsviewer.utils.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.hyman.newsviewer.utils.MD5Encoder;

public class LocalCacheUtils {

	public static final String CACHE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/newsviewer";

	public void setBitmapToLocal(String url, Bitmap result) {

		
		try {
			String fileName = MD5Encoder.encode(url);
			File file =new File(CACHE_PATH, fileName);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			
			result.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Bitmap getBitmapFromLocal(String url) {
		
		try {
			String fileName = MD5Encoder.encode(url);
			
			File file = new File(CACHE_PATH, fileName);
			if (file.exists()) {
				
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				return bitmap;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
