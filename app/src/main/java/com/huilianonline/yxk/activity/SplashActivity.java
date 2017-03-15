package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.huilianonline.yxk.MainActivity;
import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/7.
 */
public class SplashActivity extends BaseActivity {

    private ImageView mImgWelcome;
    private Animation alphaAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImgWelcome = (ImageView) findViewById(R.id.welcome_bg);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        alphaAnimation.setFillEnabled(true); //启动Fill保持
        alphaAnimation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面
        mImgWelcome.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        goMainActivity();

                    }

                }, 400);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });  //为动画设置监听

    }

    // 跳转到主界面
    protected void goMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this,ManageSetingActivity.class);
//        intent.setClass(SplashActivity.this,ConfirmOrderActivity.class);
        startActivity(intent);
        finish();
    }


}
