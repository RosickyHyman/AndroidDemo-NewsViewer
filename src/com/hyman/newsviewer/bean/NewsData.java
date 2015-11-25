package com.hyman.newsviewer.bean;

import java.util.ArrayList;

public class NewsData {
	
	public int retcode;
	public ArrayList<NewsMenuData> data;
	
	//���������
	public class NewsMenuData{
		public String id;
		public String title;
		public int type;
		public String url;
		
		public ArrayList<NewsTabData> children;

		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}

	}
	//����ҳ����11����ҳ������
	public class NewsTabData{
		public String id;
		public String title;
		public int type;
		public String url;
		
		@Override
		public String toString() {
			return "NewsTabData [title=" + title + "]";
		}
		
		
	}
	@Override
	public String toString() {
		return "NewsData [data=" + data + "]";
	}
	
	
	
	
}