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
		settings.setJavaScriptEnabled(true);//����֧��JavaScript��ʽ����ҳ
		settings.setBuiltInZoomControls(true);//��ʾ�Ŵ���С��ť
		settings.setUseWideViewPort(true);//֧��˫������
		
		wv_newsdetail.setWebViewClient(new WebViewClient(){
			
			//��ҳ��ʼ����
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				
				pb_newsdetail_progress.setVisibility(View.VISIBLE);
				tv_newsdetail_notice.setVisibility(View.VISIBLE);
					
			}
			
			//��ҳ���ؽ���
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pb_newsdetail_progress.setVisibility(View.INVISIBLE);
				tv_newsdetail_notice.setVisibility(View.INVISIBLE);
			}
			
			//������ҳ����ת���Ӷ����ڴ˷����лص�,
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//ǿ��������ת���ô�WebView����
				wv_newsdetail.loadUrl(url);
				return true;
			}
			
		});
		
		wv_newsdetail.setWebChromeClient(new WebChromeClient(){
			
			//�õ����ؽ���
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			
			//�õ���ǰ��ҳ����
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
			
			//�õ���ǰ��ҳicon
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
	 * ����¼�
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
	 * ��ʾѡ���
	 * */
	
	private String[] items = new String[]{"���������","�������","��������","С������","��С������"};
	private int current_choose_item;//�������һ��
	private int current_item=2;//���֮���ѡ����ڼ�¼ѡ����
	private void showChooseDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�����������С");
		builder.setSingleChoiceItems(items, current_item, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				current_choose_item=which;
				
			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
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
		builder.setNegativeButton("ȡ��", null);
		builder.show();
		
	}
}
