package com.hyman.newsviewer.base.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyman.newsviewer.R;
import com.hyman.newsviewer.base.BaseMenuDetailPager;
import com.hyman.newsviewer.bean.PhotosData;
import com.hyman.newsviewer.bean.PhotosData.PhotoInfo;
import com.hyman.newsviewer.global.GlobalContants;
import com.hyman.newsviewer.utils.CacheUtils;
import com.hyman.newsviewer.utils.bitmap.MyBitmapUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/*
 *菜单详情页-组图
 * */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

	private ListView lv_menu_photos;
	private GridView gv_menu_photos;
	private ArrayList<PhotoInfo> mPhotoList;
	private PhotosData photosData;
	private ImageButton ib_photo;

	public PhotoMenuDetailPager(Activity activity, ImageButton ibphoto) {
		super(activity);

		this.ib_photo = ibphoto;
		ib_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				changeShowStyle();
			}
		});
	}

	/*
	 * 初始化两套布局
	 */
	@Override
	public View initView() {

		View view = View.inflate(mActivity, R.layout.photo_menu_pager, null);
		lv_menu_photos = (ListView) view.findViewById(R.id.lv_menu_photos);
		gv_menu_photos = (GridView) view.findViewById(R.id.gv_menu_photos);

		lv_menu_photos.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
		return view;
	}

	/*
	 * 初始化数据，从服务器获取数据
	 */
	public void initData() {

		String cache = CacheUtils
				.getCache(mActivity, GlobalContants.PHOTOS_URL);
		if (!TextUtils.isEmpty(cache)) {

		}

		getPhotoDataFromServer();
	}

	/*
	 * 从服务器获取数据
	 */
	private void getPhotoDataFromServer() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, GlobalContants.PHOTOS_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;

						System.out.println("返回结果" + result);
						parserData(result);
						CacheUtils.setCache(mActivity,
								GlobalContants.PHOTOS_URL, result);

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
	 */
	protected void parserData(String result) {

		Gson gson = new Gson();
		photosData = gson.fromJson(result, PhotosData.class);
		mPhotoList = photosData.data.news;

		System.out.println(mPhotoList.toString());
		if (mPhotoList != null) {
			lv_menu_photos.setAdapter(new PhotosAdapter());
			gv_menu_photos.setAdapter(new PhotosAdapter());
		}

	}

	/*
	 * 创建适配器
	 */

	class PhotosAdapter extends BaseAdapter {

		private BitmapUtils bitmapUtils;
		private MyBitmapUtils myBitmapUtils;

		public PhotosAdapter() {

			bitmapUtils = new BitmapUtils(mActivity);
			myBitmapUtils = new MyBitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			return mPhotoList.size();
		}

		@Override
		public PhotoInfo getItem(int position) {
			return mPhotoList.get(position);
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
				view = View.inflate(mActivity, R.layout.list_photos_item, null);
				holder = new ViewHolder();

				holder.iv_pic = (ImageView) view
						.findViewById(R.id.iv_photos_pic);
				holder.tv_title = (TextView) view
						.findViewById(R.id.tv_photos_title);

				view.setTag(holder);
			}

			PhotoInfo item = getItem(position);
			holder.tv_title.setText(item.title);

			bitmapUtils.display(holder.iv_pic, item.listimage);
			//myBitmapUtils.display(holder.iv_pic, item.listimage);

			return view;
		}

	}

	static class ViewHolder {
		TextView tv_title;
		ImageView iv_pic;
	}

	/*
	 * 切换显示风格
	 */
	private boolean isListShow = true;

	protected void changeShowStyle() {
		if (isListShow) {

			isListShow = false;
			lv_menu_photos.setVisibility(View.GONE);
			gv_menu_photos.setVisibility(View.VISIBLE);

			ib_photo.setImageResource(R.drawable.icon_pic_list_type);

		} else {

			isListShow = true;
			gv_menu_photos.setVisibility(View.GONE);
			lv_menu_photos.setVisibility(View.VISIBLE);

			ib_photo.setImageResource(R.drawable.icon_pic_grid_type);
		}

	}

}
