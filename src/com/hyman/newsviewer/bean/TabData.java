package com.hyman.newsviewer.bean;

import java.util.ArrayList;


public class TabData {
	
	public int retcode;
	public TabDetail data;
	
	public class TabDetail{
		public String more;
		public String title;
		public ArrayList<TabNewsData> news;
		public ArrayList<TopNewsData> topnews;
		@Override
		public String toString() {
			return "TabDetail [title=" + title + ", news=" + news + "]";
		}
		
	}
	public class TabNewsData{
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TabNewsData [title=" + title + "]";
		}
		
	}
	public class TopNewsData{
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TopNewsData [topimage=" + topimage + ", title=" + title
					+ "]";
		}
		
		
		
	}
	@Override
	public String toString() {
		return "TabData [data=" + data + "]";
	}


}
