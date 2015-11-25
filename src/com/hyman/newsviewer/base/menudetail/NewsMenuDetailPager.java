package com.hyman.newsviewer.base.menudetail;

import java.util.ArrayList;

import android.R.raw;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.activity.MainActivity;
import com.hyman.newsviewer.base.BaseMenuDetailPager;
import com.hyman.newsviewer.base.TabDetailPager;
import com.hyman.newsviewer.bean.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

/*
 *�˵�����ҳ-���� 
 * */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

	private ViewPager mViewPager;

	private ArrayList<NewsTabData> mNewsTabData;

	private ArrayList<TabDetailPager> mPagerList;

	private TabPageIndicator mIndicator;

	private ImageButton btn_next;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsTabData> children) {
		super(activity);

		mNewsTabData = children;
	}

	/*
	 * ��ʼ��ҳ��
	 */
	@Override
	public View initView() {

		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

		mIndicator = (TabPageIndicator) view.findViewById(R.id.tpi_indicator);

		btn_next = (ImageButton) view.findViewById(R.id.btn_next);
		
		//��ViewPager��Indicator��ʱ,����������Ҫ���ø�Indicator������ViewPager
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				MainActivity mainUi =(MainActivity) mActivity;
				SlidingMenu slidingMenu = mainUi.getSlidingMenu();
				
				if (position==0) {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}	
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {	
			}
			@Override
			public void onPageScrollStateChanged(int state) {	
			}
		});
		return view;
	}

	/*
	 * ��ʼ������
	 */
	public void initData() {

		mPagerList = new ArrayList<TabDetailPager>();

		for (int i = 0; i < mNewsTabData.size(); i++) {

			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewsTabData.get(i));
			mPagerList.add(pager);
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
		mIndicator.setViewPager(mViewPager);

		/*
		 * ��ͷ����¼�����ʾ��һҳ��
		 */
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int currentItem = mViewPager.getCurrentItem();
				mViewPager.setCurrentItem(++currentItem);

			}
		});

	}

	/*
	 * ����������
	 */

	class MenuDetailAdapter extends PagerAdapter {

		// �˷���ΪTabPageIndicator����Ҫʵ�ֵķ���
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager detailPager = mPagerList.get(position);
			container.addView(detailPager.mRootView);
			detailPager.initData();
			return detailPager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	
	
}
