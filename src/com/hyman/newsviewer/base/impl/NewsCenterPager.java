package com.hyman.newsviewer.base.impl;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyman.newsviewer.activity.MainActivity;
import com.hyman.newsviewer.base.BaseMenuDetailPager;
import com.hyman.newsviewer.base.BasePager;
import com.hyman.newsviewer.base.menudetail.InteractMenuDetailPager;
import com.hyman.newsviewer.base.menudetail.NewsMenuDetailPager;
import com.hyman.newsviewer.base.menudetail.PhotoMenuDetailPager;
import com.hyman.newsviewer.base.menudetail.TopicMenuDetailPager;
import com.hyman.newsviewer.bean.NewsData;
import com.hyman.newsviewer.bean.NewsData.NewsMenuData;
import com.hyman.newsviewer.fragment.LeftMenuFragment;
import com.hyman.newsviewer.global.GlobalContants;
import com.hyman.newsviewer.utils.CacheUtils;
import com.hyman.newsviewer.utils.LogerUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsCenterPager extends BasePager {

	private NewsData mNewsData;
	private ArrayList<BaseMenuDetailPager> mPagers;

	public NewsCenterPager(Activity activity) {

		super(activity);

	}

	/*
	 * 初始化数据
	 * */
	public void initData() {

		tvTitle.setText("新闻");
		setSlidingMenuEnable(true);

		//获取缓存,不为空，直接解析
		String cache = CacheUtils.getCache(mActivity, GlobalContants.CATEGORIES_URL);
		if (!TextUtils.isEmpty(cache)) {
			parserData(cache);
		}
		getDataFromServer();

	}

	/*
	 * 获取网络数据
	 * */
	private void getDataFromServer() {

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo responseInfo) {

						String result = (String) responseInfo.result;

						LogerUtils.logI("server_result", result);
						
						parserData(result);
						
						//设置缓存
						CacheUtils.setCache(mActivity, GlobalContants.CATEGORIES_URL, result);
						
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						error.printStackTrace();

					}
				});

	}

	/*
	 * 解析数据
	 * */
	protected void parserData(String result) {

		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
		
		System.out.println("解析结果："+mNewsData);
		
		MainActivity mainActivity =(MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
		leftMenuFragment.setMenuData(mNewsData);
		
		//准备四个菜单详情页
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity,ibphoto));
		mPagers.add(new InteractMenuDetailPager(mActivity));
		
		//将新闻中心默认设置为菜单详情页-新闻
		setCurrentMenuDetail(0);

	}
	
	
	/*
	 * 设置当前详情页
	 * */
	public void setCurrentMenuDetail(int positon){
		
		BaseMenuDetailPager pager = mPagers.get(positon);
		flContent.removeAllViews();
		flContent.addView(pager.mRootView);
		
		//根据侧边栏的点击事件动态设置详情页标题
		NewsMenuData menuData = mNewsData.data.get(positon);
		tvTitle.setText(menuData.title);
		//初始化当前页面的方法来设置数据
		pager.initData();
		
		//当切换到组图页面时，对切换按钮显示，其余状态隐藏
		if (pager instanceof PhotoMenuDetailPager) {
			ibphoto.setVisibility(View.VISIBLE);
		}else {
			ibphoto.setVisibility(View.GONE);
		}
	}
	
	
	
}
