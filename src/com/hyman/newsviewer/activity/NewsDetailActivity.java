package com.hyman.newsviewer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.hyman.newsviewer.R;

public class NewsDetailActivity extends Activity implements OnClickListener{

	private WebView wv_newsdetail;
	private ImageButton btn_back;
	private ImageButton btn_textsize;
	private ImageButton btn_share;
	private ProgressBar pb_newsdetail_progress;
	private TextView tv_newsdetail_notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetail);
		
		wv_newsdetail = (WebView) findViewById(R.id.wv_newsdetail);
		btn_back = (ImageButton) findViewById(R.id.ib_newsdetail_back);
		btn_textsize = (ImageButton) findViewById(R.id.ib_newsdetail_textsize);
		btn_share = (ImageButton) findViewById(R.id.ib_newsdetail_share);
		pb_newsdetail_progress = (ProgressBar) findViewById(R.id.pb_newsdetail_progress);
		tv_newsdetail_notice = (TextView) findViewById(R.id.tv_newsdetail_notice);
		
		btn_back.setOnClickListener(this);
		btn_textsize.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		
		
		String url = getIntent().getStringExtra("url");
		WebSettings settings = wv_newsdetail.getSettings();
		settings.setJavaScriptEnabled(true);//设置支持JavaScript格式的网页
		settings.setBuiltInZoomControls(true);//显示放大缩小按钮
		settings.setUseWideViewPort(true);//支持双击缩放
		
		wv_newsdetail.setWebViewClient(new WebViewClient(){
			
			//网页开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				
				pb_newsdetail_progress.setVisibility(View.VISIBLE);
				tv_newsdetail_notice.setVisibility(View.VISIBLE);
					
			}
			
			//网页加载结束
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pb_newsdetail_progress.setVisibility(View.INVISIBLE);
				tv_newsdetail_notice.setVisibility(View.INVISIBLE);
			}
			
			//所有网页的跳转链接都会在此方法中回调,
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//强制所有跳转都用此WebView加载
				wv_newsdetail.loadUrl(url);
				return true;
			}
			
		});
		
		wv_newsdetail.setWebChromeClient(new WebChromeClient(){
			
			//拿到加载进度
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			
			//拿到当前网页标题
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
			
			//拿到当前网页icon
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				// TODO Auto-generated method stub
				super.onReceivedIcon(view, icon);
			}
			
		});
		
		//wv_newsdetail.goBack();
		
		wv_newsdetail.loadUrl(url);
		
		
		
	}

	/*
	 * 点击事件
	 * */
	@Override
	public void onClick(View v) {

		
		switch (v.getId()) {
		case R.id.ib_newsdetail_back:
			
			finish();
			break;
		case R.id.ib_newsdetail_textsize:
			
			showChooseDialog();
			break;
		case R.id.ib_newsdetail_share:
			
			break;

		default:
			break;
		}
	}

	
	/*
	 * 显示选择框
	 * */
	
	private String[] items = new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
	private int current_choose_item;//点击的哪一个
	private int current_item=2;//点击之后的选项，用于记录选择项
	private void showChooseDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请设置字体大小");
		builder.setSingleChoiceItems(items, current_item, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				current_choose_item=which;
				
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebSettings settings = wv_newsdetail.getSettings();
				switch (current_choose_item) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
				
				current_item=current_choose_item;
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
		
	}
}
