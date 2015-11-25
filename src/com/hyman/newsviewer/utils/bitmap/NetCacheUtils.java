package com.hyman.newsviewer.utils.bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils {

	private LocalCacheUtils mLocalCacheUtils;
	private MemoryCacheUtils mMemoryCacheUtils;
	

	public NetCacheUtils(LocalCacheUtils localCacheUtils,
			MemoryCacheUtils memoryCacheUtils) {
		mLocalCacheUtils = localCacheUtils;
		mMemoryCacheUtils = memoryCacheUtils;

	}

	public  void getBitmapFromNet(ImageView iv_pic, String url) {
		
		new BitmapTask().execute(iv_pic,url);

	}

	class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

		private ImageView iv_pic;
		private String url;

		// 子线程中执行1
		@Override
		protected Bitmap doInBackground(Object... params) {
			iv_pic = (ImageView) params[0];
			url = (String) params[1];
			iv_pic.setTag(url);

			return downLoad(url);
		}

		// 可以获取子线程执行的进度
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		// 子线程执行结束后执行
		@Override
		protected void onPostExecute(Bitmap result) {

			if (result != null) {
				String bindUrl = (String) iv_pic.getTag();
				if (bindUrl.equals(url)) {
					iv_pic.setImageBitmap(result);
					mLocalCacheUtils.setBitmapToLocal(url, result);
					mMemoryCacheUtils.setBitmapToMemory(url, result);
					System.out.println("从网络拿到数据啦。。。");

				}
			}

		}

	}

	public Bitmap downLoad(String url) {

		HttpURLConnection conn =null;
		InputStream is=null;
		try {
			conn= (HttpURLConnection) new URL(url).openConnection();
			
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode()==200) {
				
				is = conn.getInputStream();
				
				BitmapFactory.Options options =new BitmapFactory.Options();
				options.inSampleSize=2;
				options.inPreferredConfig=Bitmap.Config.RGB_565;
				Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
				
				return bitmap;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (conn!=null) {
				conn.disconnect();
				
			}if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

}
