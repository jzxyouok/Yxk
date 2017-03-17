package com.huilianonline.yxk;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;


import java.io.*;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author
 */
public class CrashHandler implements UncaughtExceptionHandler {

    /* 休眠的时间 */
    private final static int SLEEP_TIME = 3000;

    /* 系统默认的UncaughtException处理类 */
    private UncaughtExceptionHandler mDefaultHandler;

    /* CrashHandler实例 */
    private static CrashHandler INSTANCE = new CrashHandler();

    /* 程序的Context对象 */
    private Context mContext;

    /* 用来存储设备信息和异常信息 */
    private Map<String, String> infos = null;

    /* 用于格式化日期,作为日志文件名的一部分 */
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     * @return 实例
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     * @param context 程序上下文对象
     */
    public void init(Context context) {
        mContext = context;

        /* 获取系统默认的UncaughtException处理器 */
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        /* 设置该CrashHandler为程序的默认处理器 */
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     * @param thread 处理线程
     * @param ex 错误抛出对象Throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            /* 如果用户没有处理则让系统默认的异常处理器来处理 */
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            /* 休眠一定时间，然后退出程序 */
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
//                VolleyLog.e("error：%s", e.getMessage());
            }

            /* 退出程序 */
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param ex 错误抛出对象Throwable
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        /* 使用Toast来显示异常信息 */
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                // TODO: 将此处修改为对话框确认逻辑

                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        /* 收集设备参数信息 */
//        infos = SystemInfoUtils.collectDeviceInfo(mContext);

        /* 保存日志文件 */
        saveCrashInfo2File(ex);

        // TODO: 调用接口传数据回服务器收集，或上传日志文件

        return true;
    }

    /**
     * 保存错误信息到文件中
     * @param ex 程序上下文对象
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(String.format("%s=%s\n", key, value));
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
//        VolleyLog.e(result);
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = String.format("crash-%s-%s.log", time, timestamp);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = String.format("%s/zihexin/crash/", Environment.getExternalStorageDirectory().getPath());
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
//            VolleyLog.e("an error occured while writing file...%s", e.getMessage());
        }
        return null;
    }
}  