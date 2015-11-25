package com.hyman.newsviewer.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyman.newsviewer.R;

public class RefreshListView extends ListView implements OnScrollListener,android.widget.AdapterView.OnItemClickListener{

	private TextView tv_title;
	private TextView tv_time;
	private ImageView iv_arror;
	private ProgressBar pb_progress;
	private View mFooterView;
	private int mFooterViewHight;

	
	private static final int STATE_PULL_FRESH=1;
	private static final int STATE_RELEASE_FRESH=2;
	private static final int STATE_FRESHING=3;
	private int CURRENT_STATE=STATE_PULL_FRESH;
	private int startY=-1;
	private int mHeadViewHight;
	private View mHeaderView;
	private RotateAnimation upAnim;
	private RotateAnimation downAnim;

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	/*
	 * ��ʼ��ͷ�ļ�
	 * 
	 */
	 
	public void initHeaderView(){
		
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);
		
		tv_title = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tv_time = (TextView) mHeaderView.findViewById(R.id.tv_time);
		iv_arror = (ImageView) mHeaderView.findViewById(R.id.iv_arror);
		pb_progress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);

		mHeaderView.measure(0, 0);
		mHeadViewHight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeadViewHight, 0, 0);
		
		tv_time.setText("���ˢ��:" + getCurrentTime());
		
		initArrowAnim();
	}
	
	/*
	 * ��ʼ�����ļ�
	 * */
	public void initFooterView(){
		
		mFooterView = View.inflate(getContext(), R.layout.refresh_footer, null);
		this.addFooterView(mFooterView);
		
		mFooterView.measure(0, 0);
		mFooterViewHight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHight, 0, 0);
	
		this.setOnScrollListener(this);
	}
	
	
	/*
	 * �����¼�
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY==-1) {
				startY = (int) ev.getRawY();	
			}
			if (CURRENT_STATE==STATE_FRESHING) {
				break;
			}
			int endY=(int) ev.getRawY();
			int dy=endY-startY;
			
			
			if (dy>0&&getFirstVisiblePosition()==0) {//��ʾ����ĿΪ��һ����Ŀ���ƶ�������0ʱ������padding
				int padding =dy-mHeadViewHight;
				mHeaderView.setPadding(0, padding, 0, 0);
				
				if (padding>0&&CURRENT_STATE!=STATE_RELEASE_FRESH) {//����0�Ҳ�Ϊ�ɿ�ˢ��ʱ�����¶�̬Ϊ�ɿ�ˢ��
					CURRENT_STATE=STATE_RELEASE_FRESH;
					
					refreshState();
				}else if (padding<0&&CURRENT_STATE!=STATE_PULL_FRESH) {
					CURRENT_STATE=STATE_PULL_FRESH;
					refreshState();
				}
				
				return true;
			}
			
			
			break;
		case MotionEvent.ACTION_UP:
			
			startY=-1;
			if (CURRENT_STATE==STATE_RELEASE_FRESH) {
				
				CURRENT_STATE=STATE_FRESHING;
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshState();
					
			}else if (CURRENT_STATE==STATE_PULL_FRESH) {
				
				mHeaderView.setPadding(0, -mHeadViewHight, 0, 0);
				
			}
			
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}

	/*
	 * ˢ��״̬
	 * */
	private void refreshState() {

		switch (CURRENT_STATE) {
		case STATE_PULL_FRESH:
			
			tv_title.setText("����ˢ��");
			iv_arror.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arror.startAnimation(downAnim);

			break;
		case STATE_RELEASE_FRESH:
			
			tv_title.setText("�ɿ�ˢ��");
			iv_arror.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arror.startAnimation(upAnim);
			
			
			break;
		case STATE_FRESHING:
			tv_title.setText("����ˢ��...");
			iv_arror.clearAnimation();
			iv_arror.setVisibility(View.INVISIBLE);
			pb_progress.setVisibility(View.VISIBLE);
			
			if (mListener!=null) {
				mListener.onRefresh();
			}
			
			break;

		default:
			break;
		}

	}
	
	/*
	 * ��ͷ��ת����
	 * */
	public void initArrowAnim(){
		
		upAnim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnim.setDuration(200);
		upAnim.setFillAfter(true);
		
		
		downAnim = new RotateAnimation(180,360,  Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnim.setDuration(200);
		downAnim.setFillAfter(true);
		
	}
	
	
	/*
	 * ����ˢ�µĽӿڲ�ʵ������ˢ�µļ���
	 * */
	OnRefreshListener mListener;

	public void setOnRefreshListener(OnRefreshListener listener){
		mListener=listener;
	}
	
	public interface OnRefreshListener{
		
		public void onRefresh();
		public void onLoadingMore();
	}
	
	/*
	 * �����²˵�
	 * */
	public void onRefreshComplete(boolean success){
		
		if (isLoadingMore) {
			
			mFooterView.setPadding(0, -mFooterViewHight, 0, 0);
			isLoadingMore=false;
		}
		else {
			
			CURRENT_STATE=STATE_PULL_FRESH;
			tv_title.setText("����ˢ��");
			iv_arror.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -mHeadViewHight, 0, 0);
			if (success) {
				
				tv_time.setText("���ˢ��:" + getCurrentTime());
			}
		}
		
	}
	
	/*
	 * �������ˢ��ʱ��
	 * */
	public String getCurrentTime(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	
	/*
	 * �����¼�����mFooterView����ʾ������
	 * */
	
	private boolean isLoadingMore;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		if (scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING) {
		
			if (getLastVisiblePosition()==getCount()-1&&!isLoadingMore) {
				
				System.out.println("����������������");
				mFooterView.setPadding(0, 0, 0, 0);
				setSelection(getCount()-1);
				isLoadingMore=true;
				
				if (mListener!=null) {
					mListener.onLoadingMore();
				}
			}
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

	
	
	/*
	 * ��дListView��setOnItemClickListener��������֤�����position����
	 */
	
	OnItemClickListener mOnItemClickListener;
	
	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		super.setOnItemClickListener(this);
		mOnItemClickListener=listener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if (mOnItemClickListener!=null) {
			//��װListView�ĵ���¼�
			mOnItemClickListener.onItemClick(parent, view, position-getHeaderViewsCount(), id);
		}
	}
	

}
