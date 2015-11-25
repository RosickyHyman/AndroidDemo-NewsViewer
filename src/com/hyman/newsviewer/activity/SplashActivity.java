package com.hyman.newsviewer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.hyman.newsviewer.R;

public class SplashActivity extends Activity {

	private RelativeLayout rl_splash_root;
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initAnimation();
		mPrefs = getSharedPreferences("config", MODE_PRIVATE);
		
	}


	/*
	 * ��ʼ������
	 * */
	private void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
	}

	
	/*
	 * ���ö���
	 * */
	private void initAnimation() {
		
		AnimationSet animationSet = new AnimationSet(false);

		//360����ת����
		RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(2000);
		ra.setFillAfter(true);
		
		//���Ŷ���
		ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(2000);
		sa.setFillAfter(true);
		
		//���䶯��
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(2000);
		aa.setFillAfter(true);
		
		animationSet.addAnimation(ra);
		animationSet.addAnimation(sa);
		animationSet.addAnimation(aa);
		
		//�������ü���������ʱ��תҳ��
		animationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				jumpNextPage();
			}
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		//��������
		rl_splash_root.startAnimation(animationSet);
		
	}


	/*
	 * �ж��Ƿ���Ѿ����������ҳ��
	 */
	protected void jumpNextPage() {

		boolean isGuide = mPrefs.getBoolean("isGuide", false);
		
		if (isGuide) {
			
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}else {
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
			
		}
		
		finish();	
	}
	
	
}
