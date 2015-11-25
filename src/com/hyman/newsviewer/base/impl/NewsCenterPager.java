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
	 * ��ʼ������
	 * */
	public void initData() {

		tvTitle.setText("����");
		setSlidingMenuEnable(true);

		//��ȡ����,��Ϊ�գ�ֱ�ӽ���
		String cache = CacheUtils.getCache(mActivity, GlobalContants.CATEGORIES_URL);
		if (!TextUtils.isEmpty(cache)) {
			parserData(cache);
		}
		getDataFromServer();

	}

	/*
	 * ��ȡ��������
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
						
						//���û���
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
	 * ��������
	 * */
	protected void parserData(String result) {

		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
		
		System.out.println("���������"+mNewsData);
		
		MainActivity mainActivity =(MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
		leftMenuFragment.setMenuData(mNewsData);
		
		//׼���ĸ��˵�����ҳ
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity,ibphoto));
		mPagers.add(new InteractMenuDetailPager(mActivity));
		
		//����������Ĭ������Ϊ�˵�����ҳ-����
		setCurrentMenuDetail(0);

	}
	
	
	/*
	 * ���õ�ǰ����ҳ
	 * */
	public void setCurrentMenuDetail(int positon){
		
		BaseMenuDetailPager pager = mPagers.get(positon);
		flContent.removeAllViews();
		flContent.addView(pager.mRootView);
		
		//���ݲ�����ĵ���¼���̬��������ҳ����
		NewsMenuData menuData = mNewsData.data.get(positon);
		tvTitle.setText(menuData.title);
		//��ʼ����ǰҳ��ķ�������������
		pager.initData();
		
		//���л�����ͼҳ��ʱ�����л���ť��ʾ������״̬����
		if (pager instanceof PhotoMenuDetailPager) {
			ibphoto.setVisibility(View.VISIBLE);
		}else {
			ibphoto.setVisibility(View.GONE);
		}
	}
	
	
	
}
