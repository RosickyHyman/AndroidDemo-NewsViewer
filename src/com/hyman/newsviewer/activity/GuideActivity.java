package com.hyman.newsviewer.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.hyman.newsviewer.R;
import com.hyman.newsviewer.utils.DensityUtils;

public class GuideActivity extends Activity {

	private int mPointWidth;
	private static final int[] mImage = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	private View view_red_point;
	private Button btn_guide_start;
	private ViewPager vp_guide_show;
	private LinearLayout ll_point_group;
	private GuideAdapter adapter;
	private SharedPreferences mPrefs;
	private List<ImageView> imageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		mPrefs = getSharedPreferences("config", MODE_PRIVATE);
	}

	/*
	 * 初始化页面
	 */
	private void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		vp_guide_show = (ViewPager) findViewById(R.id.vp_guide_show);
		btn_guide_start = (Button) findViewById(R.id.btn_guide_start);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		view_red_point = findViewById(R.id.view_red_point);

		btn_guide_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mPrefs.edit().putBoolean("isGuide", true).commit();
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});

		adapter = new GuideAdapter();
		vp_guide_show.setAdapter(adapter);
		vp_guide_show.setOnPageChangeListener(new GuidePageListener());

		// 初始化背景图
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < mImage.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImage[i]);
			imageList.add(image);
		}

		// 初始化小灰点
		for (int i = 0; i < mImage.length; i++) {

			View point = new View(this);
			// 设置原点背景
			point.setBackgroundResource(R.drawable.shape_point_gray);
			// 设置原点大小
			
			int dp2px = DensityUtils.dp2px(this, 10);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					dp2px, dp2px);
			if (i > 0) {
				// 设置原点边距
				params.leftMargin = dp2px;
			}
			point.setLayoutParams(params);
			ll_point_group.addView(point);
		}
		
		//因为文件的布局是有顺序的，因此在计算圆点之间的距离之前必须绘制完毕，因此设置layout事件监听
		// 获取视图树，对layout结束事件监听
		ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					// 当布局layout执行结束回调此函数
					@Override
					public void onGlobalLayout() {

						ll_point_group.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						
						//API16以上使用
						//ll_point_group.getViewTreeObserver().removeOnGlobalLayoutListener(this);
						// 获取圆点的间距
						mPointWidth = ll_point_group.getChildAt(1).getLeft()
								- ll_point_group.getChildAt(0).getLeft();

					}
				});
	}

	/*
	 * viewPager滚动监听
	 */

	class GuidePageListener implements OnPageChangeListener {

		// 页面滚动的时候触发
		@Override
		public void onPageScrolled(int postion, float postionOffset,
				int positionOffsetPixels) {
			// "当前位置:" + position
			// "百分比:" + positionOffset
			// "移动距离:" + positionOffsetPixels);

			int len = (int) (mPointWidth * postionOffset) + postion
					* mPointWidth;

			RelativeLayout.LayoutParams params = (LayoutParams) view_red_point
					.getLayoutParams();
			params.leftMargin = len;
			view_red_point.setLayoutParams(params);

		}

		// 某个页面被选中的时候，对button进行隐藏显示操作
		@Override
		public void onPageSelected(int positon) {

			if (positon == mImage.length - 1) {
				btn_guide_start.setVisibility(View.VISIBLE);
			} else {
				btn_guide_start.setVisibility(View.INVISIBLE);
			}

		}

		// 滚动状态发生变化调用
		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}

	/*
	 * 创建适配器对象接收数据
	 */
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImage.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageList.get(position));
			return imageList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			// super.destroyItem(container, position, object);
		}

	}

}
