package com.huilianonline.yxk;

import android.app.Application;

import com.huilianonline.yxk.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */
public class MyApplication extends Application{

    private static MyApplication instance;

    public static List<BaseActivity> mActList = new ArrayList<>();

    public static List<BaseActivity> getmActList() {
        return mActList;
    }


    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
