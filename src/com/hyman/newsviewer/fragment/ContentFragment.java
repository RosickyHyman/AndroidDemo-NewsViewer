package com.hyman.newsviewer.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.base.BasePager;
import com.hyman.newsviewer.base.impl.GovAffairsPager;
import com.hyman.newsviewer.base.impl.HomePager;
import com.hyman.newsviewer.base.impl.NewsCenterPager;
import com.hyman.newsviewer.base.impl.SettingPager;
import com.hyman.newsviewer.base.impl.SmartServicePager;

public class ContentFragment extends BaseFragment {

	private RadioGroup rg_group;
	private ArrayList<BasePager> mPagerList;
	private ViewPager mViewPager;

	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.fragement_content, null);
		rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_content_show);
		
		return view;
	}

	@Override
	public void initData() {
		rg_group.check(R.id.rb_content_home);//Ĭ�Ϲ�ѡ��ҳ
		
		mPagerList = new ArrayList<BasePager>();
		
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new GovAffairsPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new SettingPager(mActivity));
		
		mViewPager.setAdapter(new ContentAdapter());
		
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_content_home:
					mViewPager.setCurrentItem(0, false);//ȥ���л�ҳ�涯��
					break;
				case R.id.rb_content_news:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_content_gov:
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_content_smart:
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_content_setting:
					mViewPager.setCurrentItem(4, false);
					break;

				default:
					break;
				}	
				
			}
		});
		
		/*
		 * ҳ�������ֻ���ص�ǰҳ�棬����������ҳ��
		 * */
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mPagerList.get(position).initData();//��ȡ��ǰ��ѡ�е�ҳ�棬��ʼ������
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		mPagerList.get(0).initData();//��ʼ������
		
	}
	
	/*
	 * ����������
	 * */
	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			BasePager basePager = mPagerList.get(position);
			container.addView(basePager.mRootView);
			//basePager.initData();//���ڴ˴���Ԥ������������ҳ�棬�����home��settingҳ��Ԥ����
			return basePager.mRootView;
		}
		
		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
			
		}
	}
	
	
	/*
	 * ���NewsCenterpagers����
	 * */
		public NewsCenterPager getNewsCenterPager(){
			NewsCenterPager newsCenterPager = (NewsCenterPager) mPagerList.get(1);
			return newsCenterPager;
			
			
		}
}
