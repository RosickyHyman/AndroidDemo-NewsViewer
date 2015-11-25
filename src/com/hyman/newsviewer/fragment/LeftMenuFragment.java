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
	 * ��ʼ������
	 * */
	@Override
	public View initViews() {

		View view = View.inflate(mActivity, R.layout.fragement_left_menu, null);
		lv_left = (ListView) view.findViewById(R.id.lv_leftfragment);
		return view;
	}

	/*
	 * ��ʼ������
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
	 * ������ʾ�����
	 * */
	protected void toggleSlidingMenu() {

		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();	
	}

	/*
	 * ���õ�ǰ�˵�����
	 * */
	protected void setCurrentMenuDetail(int position) {

		MainActivity mainUi = (MainActivity) mActivity;
		ContentFragment fragment = mainUi.getContentFragment();//��ȡ��ҳ��
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();//��ȡ�������Ĳ˵�ҳ
		newsCenterPager.setCurrentMenuDetail(position);//���õ�ǰ�˵�����ҳ
			
	}

	/*
	 * ������������
	 * */
	public void setMenuData(NewsData data) {

		System.out.println("�õ�����"+data);
		
		mMenuList = data.data;
			
		mAdapter = new MenuAdapter();
		lv_left.setAdapter(mAdapter);

	}

	/*
	 * �������������������
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
