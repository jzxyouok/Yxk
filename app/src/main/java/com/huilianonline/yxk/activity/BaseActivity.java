package com.huilianonline.yxk.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.utils.AppManager;
import com.huilianonline.yxk.utils.NetWorkUtils;

public abstract class BaseActivity extends FragmentActivity {

    protected Context mContext;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        boolean isConn = NetWorkUtils.isConn(mContext);
        if (!isConn){
            NetWorkUtils.setNetworkMethod(mContext);
        }

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
//        AppManager.getAppManager().finishActivity(this);
    }
    private long firstTime = 0;

    /**
     * 退出app
     **/
    public void exit() {
        long secondtime = System.currentTimeMillis();
        if (secondtime - firstTime > 2000) {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            firstTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivity();
            finish();
        }
    }

    public void onBack(View view) {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 跳转界面并结束当前的Activity
     *
     * @param clazz
     */
    public void startActivityFinishSelf(Class<? extends Activity> clazz) {
        super.startActivity(new Intent(this, clazz));
        finish();
    }


    /**
     * 显示加载的对话框
     *
     * @param cancelable 是否可被返回键取消
     */
    public void showWaitingDialog(boolean cancelable, final String msg) {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.custom_dialog_transparent) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    ((TextView) dialog.findViewById(R.id.tv_msg)).setText(msg);
                }
            };
            dialog.setContentView(R.layout.lay_pro_loading_dialog);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(cancelable);
        }
        dialog.show();
    }

    public void showWaitingDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.custom_dialog_transparent) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    ((TextView) dialog.findViewById(R.id.tv_msg)).setText("加载中...");
                }
            };
            dialog.setContentView(R.layout.lay_pro_loading_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.show();
    }

    /**
     * 显示默认的对话框
     */
    public void stopWaitingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    // 内存紧张时回收图片资源
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
    // 内存紧张时回收图片资源 API4.0
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    public void onResume() {
        super.onResume();
    }
    public void onPause() {
        super.onPause();
    }

}