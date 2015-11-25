package com.hyman.newsviewer.bean;

import java.util.ArrayList;



public class PhotosData {

	public int retcode;
	public PhotosInfo data;
	
	public class PhotosInfo{
		public String title;
		public ArrayList<PhotoInfo> news;
		@Override
		public String toString() {
			return "PhotosInfo [title=" + title + ", news=" + news + "]";
		}
		
		
	}
	
	public class PhotoInfo {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "PhotoInfo [title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "PhotosData [retcode=" + retcode + ", data=" + data + "]";
	} 
	
	
}
