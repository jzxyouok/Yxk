package com.huilianonline.yxk;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.huilianonline.yxk.activity.BaseActivity;
import com.huilianonline.yxk.activity.MessageListActivity;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private static Context context;

    public static List<BaseActivity> mActList = new ArrayList<>();

    public static List<BaseActivity> getmActList() {
        return mActList;
    }

    private static final String TAG = MyApplication.class.getName();

    public static final String UPDATE_STATUS_ACTION = "com.huilianonline.yxk.action.UPDATE_STATUS";

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
           /* 若不为Debug版本则初始化CrashHandler */
//        if (!android.os.Debug.isDebuggerConnected()) {
//            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(getApplicationContext());
//        }
        instance = this;
        context = getApplicationContext();

        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setDebugMode(true);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(context,MessageListActivity.class);
                startActivity(intent);
            }

        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i(TAG, "device token: " + deviceToken);
                Log.e("tttt","device token: " + deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i(TAG, "register failed: " + s + " " + s1);
                Log.e("tttt","register failed: " + s + " " + s1);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
    }


}
