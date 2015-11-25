package com.hyman.newsviewer.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.activity.MainActivity;
import com.hyman.newsviewer.base.impl.NewsCenterPager;
import com.hyman.newsviewer.bean.NewsData;
import com.hyman.newsviewer.bean.NewsData.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class LeftMenuFragment extends BaseFragment {

	private ListView lv_left;
	private ArrayList<NewsMenuData> mMenuList;
	private int mCurrentPosition;
	private MenuAdapter mAdapter;

	
	/*
	 * 初始化布局
	 * */
	@Override
	public View initViews() {

		View view = View.inflate(mActivity, R.layout.fragement_left_menu, null);
		lv_left = (ListView) view.findViewById(R.id.lv_leftfragment);
		return view;
	}

	/*
	 * 初始化数据
	 * */
	@Override
	public void initData() {
		lv_left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mCurrentPosition = position;
				mAdapter.notifyDataSetChanged();
				
				setCurrentMenuDetail(position);
				
				toggleSlidingMenu();
				
			}
		});
	
	}

	
	/*
	 * 隐藏显示侧边栏
	 * */
	protected void toggleSlidingMenu() {

		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();	
	}

	/*
	 * 设置当前菜单数据
	 * */
	protected void setCurrentMenuDetail(int position) {

		MainActivity mainUi = (MainActivity) mActivity;
		ContentFragment fragment = mainUi.getContentFragment();//获取主页面
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();//获取新闻中心菜单页
		newsCenterPager.setCurrentMenuDetail(position);//设置当前菜单详情页
			
	}

	/*
	 * 设置网络数据
	 * */
	public void setMenuData(NewsData data) {

		System.out.println("拿到数据"+data);
		
		mMenuList = data.data;
			
		mAdapter = new MenuAdapter();
		lv_left.setAdapter(mAdapter);

	}

	/*
	 * 创建适配器，添加数据
	 * */
	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view =View.inflate(mActivity, R.layout.item_menu_list, null);
			TextView tv_menu_title =(TextView) view.findViewById(R.id.tv_menu_title);
			NewsMenuData mNewsMenuData =getItem(position);
			tv_menu_title.setText(mNewsMenuData.title);	
			if (mCurrentPosition==position) {
				tv_menu_title.setEnabled(true);
			}else {
				tv_menu_title.setEnabled(false);
			}
			return view;
		}

	}
}
