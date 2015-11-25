package com.hyman.newsviewer.base;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyman.newsviewer.R;
import com.hyman.newsviewer.activity.NewsDetailActivity;
import com.hyman.newsviewer.bean.NewsData.NewsTabData;
import com.hyman.newsviewer.bean.TabData;
import com.hyman.newsviewer.bean.TabData.TabNewsData;
import com.hyman.newsviewer.bean.TabData.TopNewsData;
import com.hyman.newsviewer.global.GlobalContants;
import com.hyman.newsviewer.utils.CacheUtils;
import com.hyman.newsviewer.utils.bitmap.MyBitmapUtils;
import com.hyman.newsviewer.view.RefreshListView;
import com.hyman.newsviewer.view.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;

/*
 *菜单详情页-新闻 
 * */
public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	private NewsTabData mTabData;
	private ViewPager mViewPager;
	private String mUrl;
	private TabData mTopDetailData;
	private ArrayList<TopNewsData> mTopNewsList;// Top标题数据集合
	private TextView tv_title;// Top标题文字
	private CirclePageIndicator mIndicator;// Top标题指示器
	private RefreshListView lv_news;// 正文新闻ListView对象
	private ArrayList<TabNewsData> mNewsList;// 正文新闻数据集合
	private View headView;
	private String mMoreUrl;
	private NewsAdapter mNewsAdapter;
	private SharedPreferences mPrefs;
	private Handler mHandler;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;
		mUrl = GlobalContants.SERVER_URL + mTabData.url;
		mPrefs = mActivity.getSharedPreferences("config",
				mActivity.MODE_PRIVATE);
	}

	/*
	 * 初始化界面
	 */
	@Override
	public View initView() {

		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		headView = View.inflate(mActivity, R.layout.list_header_topnews, null);

		lv_news = (RefreshListView) view.findViewById(R.id.lv_news_list);

		mViewPager = (ViewPager) headView.findViewById(R.id.vp_top_news);
		tv_title = (TextView) headView.findViewById(R.id.tv_top_title);
		mIndicator = (CirclePageIndicator) headView
				.findViewById(R.id.indicator);

		lv_news.addHeaderView(headView);
		lv_news.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				getTopDataFromServer();

			}

			@Override
			public void onLoadingMore() {

				// 判断加载更多资源路径是否为空
				if (mMoreUrl != null) {

					getMoreDataFromServer();
				} else {
					Toast.makeText(mActivity, "已经是最后一页啦！", Toast.LENGTH_SHORT)
							.show();
					lv_news.onRefreshComplete(false);
				}
			}
		});

		lv_news.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				System.out.println(position);

				String ids = mPrefs.getString("read_id", "");
				String readId = mNewsList.get(position).id;
				if (!ids.contains(readId)) {
					ids = ids + readId + ",";
					mPrefs.edit().putString("read_id", ids).commit();
				}

				changeNewsState(view);

				Intent intent = new Intent(mActivity, NewsDetailActivity.class);
				intent.putExtra("url", mNewsList.get(position).url);
				mActivity.startActivity(intent);
			}
		});

		return view;
	}

	/*
	 * 初始化数据
	 */
	public void initData() {

		// 查看是否有缓存，如果有直接解析
		String cache = CacheUtils.getCache(mActivity, mUrl);
		if (!TextUtils.isEmpty(cache)) {
			parserData(cache, false);
		}
		getTopDataFromServer();

	}

	/*
	 * 改变已经读取新闻的状态
	 */
	protected void changeNewsState(View view) {

		TextView tv_listview_title = (TextView) view
				.findViewById(R.id.tv_listview_title);
		tv_listview_title.setTextColor(Color.GRAY);

	}

	/*
	 * 从服务器拉取数据
	 */
	private void getTopDataFromServer() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				String result = responseInfo.result;
				parserData(result, false);
				lv_news.onRefreshComplete(true);

				// 保存缓存
				CacheUtils.setCache(mActivity, mUrl, result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				lv_news.onRefreshComplete(false);

			}
		});

	}

	/*
	 * 从服务器拉取更多数据
	 */
	private void getMoreDataFromServer() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				String result = responseInfo.result;
				parserData(result, true);
				lv_news.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				lv_news.onRefreshComplete(false);

			}
		});

	}

	/*
	 * 解析服务器获得数据
	 */
	protected void parserData(String result, boolean isMore) {

		Gson gson = new Gson();

		mTopDetailData = gson.fromJson(result, TabData.class);

		// 拿到加载更多数据的地址
		String more = mTopDetailData.data.more;
		if (!TextUtils.isEmpty(more)) {
			mMoreUrl = GlobalContants.SERVER_URL + more;
		} else {
			mMoreUrl = null;
		}

		// 判断是否是加载更多，
		if (!isMore) {

			mTopNewsList = mTopDetailData.data.topnews;
			mNewsList = mTopDetailData.data.news;

			// 判断数据集合是否为空
			if (mTopNewsList != null) {

				mViewPager.setAdapter(new TopNewsAdapter());

				mIndicator.setViewPager(mViewPager);
				mIndicator.setSnap(true);// 跳跃显示
				mIndicator.setOnPageChangeListener(this);// 设置监听
				// Top标题指示器设置默认位置
				mIndicator.onPageSelected(0);
				tv_title.setText(mTopNewsList.get(0).title);
			}

			// 判断数据集合是否为空
			if (mNewsList != null) {

				mNewsAdapter = new NewsAdapter();
				lv_news.setAdapter(mNewsAdapter);
			}

			if (mHandler == null) {

				mHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						int currentPage = mViewPager.getCurrentItem();
						if (currentPage < mTopNewsList.size() - 1) {

							currentPage++;
						} else {
							currentPage = 0;
						}
						mViewPager.setCurrentItem(currentPage);
						mHandler.sendEmptyMessageDelayed(0, 3000);

					};
				};

				mHandler.sendEmptyMessageDelayed(0, 3000);
			}

		} else {
			ArrayList<TabNewsData> news = mTopDetailData.data.news;

			mNewsList.addAll(news);

			mNewsAdapter.notifyDataSetChanged();

		}

	}

	/*
	 * 创建top数据适配器
	 */
	class TopNewsAdapter extends PagerAdapter {

		private BitmapUtils bitmapUtils;
		private MyBitmapUtils myBitmapUtils;

		public TopNewsAdapter() {

			bitmapUtils = new BitmapUtils(mActivity);
			myBitmapUtils = new MyBitmapUtils(mActivity);
			// 给top图集设置默认图片
			bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return mTopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView imageView = new ImageView(mActivity);
			imageView.setScaleType(ScaleType.FIT_XY);

			TopNewsData topNewsData = mTopNewsList.get(position);

			// 动态设置图片
			//bitmapUtils.display(imageView, topNewsData.topimage);
			myBitmapUtils.display(imageView, topNewsData.topimage);
			container.addView(imageView);
			imageView.setOnTouchListener(new TopNewOnTouchListener());

			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);

		}
	}

	/*
	 * 创建自定义点击监听来控制轮播图的动作
	 */

	class TopNewOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 按下时，取消所有发送的消息,参数可以是obj(表示某个消息对象)也可以是null(表示取消全部消息)
				mHandler.removeCallbacksAndMessages(null);
				break;
			case MotionEvent.ACTION_CANCEL:
				// 触摸事件取消时，继续发送消息
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;
			case MotionEvent.ACTION_UP:
				// 抬起时，继续发送消息
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;
			default:
				break;
			}
			return false;
		}

	}

	/*
	 * 创建新闻列表的适配器
	 */

	class NewsAdapter extends BaseAdapter {

		private BitmapUtils bitmapUtils;

		public NewsAdapter() {

			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(mActivity, R.layout.item_news_list, null);
				holder = new ViewHolder();
				holder.iv_pic = (ImageView) view
						.findViewById(R.id.iv_listview_pic);
				holder.tv_title = (TextView) view
						.findViewById(R.id.tv_listview_title);
				holder.tv_date = (TextView) view
						.findViewById(R.id.tv_listview_date);
				view.setTag(holder);
			}

			holder.tv_title.setText(getItem(position).title);
			holder.tv_date.setText(getItem(position).pubdate);

			bitmapUtils.display(holder.iv_pic, getItem(position).listimage);

			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_pic;
		TextView tv_title;
		TextView tv_date;

	}

	/*
	 * 指示器的监听实现
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		tv_title.setText(mTopNewsList.get(position).title);
	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
