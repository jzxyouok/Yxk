package com.huilianonline.yxk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liujiqing on 2016/9/24.
 */
public class PhoneInfoUtil {
    private static final String TAG = "PhoneInfoUtil";
    private static PhoneInfoUtil instance;
    public static String mVersionName;
    public static int mDisplayWidth;
    public static int mDisplayHeight;
    public static float mDensity;

    private PhoneInfoUtil() {
    }

    public static synchronized PhoneInfoUtil getInstance() {
        if (instance == null) {
            instance = new PhoneInfoUtil();
        }
        return instance;
    }

    /**
     * 当imei获取失败后通过六种方式生成一个手机唯一识别码
     *
     * @param context
     * @return
     */

    public DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        return metric;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public int getDisplayWidth(Context context) {
        if (mDisplayWidth == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayWidth = metric.widthPixels;
        }
        return mDisplayWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public int getDisplayHeight(Context context) {
        if (mDisplayHeight == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayHeight = metric.heightPixels;
        }
        return mDisplayHeight;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 5.4.1根据屏幕或实际宽度补全图片URL
     *
     * @param context
     * @param imgUrl   图片URL
     * @param type     0为根据imgWidth值来补全
     *                 1为根据屏幕实际宽度补全
     *                 2为根据屏幕实际宽度的1/2补全
     *                 3为根据屏幕实际宽度的1/3补全
     *                 4为根据屏幕实际宽度的1/4补全
     * @param imgWidth 图片的实际width，传过来时已计算好
     * @param zoom     等比缩放的比例，默认传1
     * @return
     */
    public String getImageUrl(Context context, String imgUrl, int type, int imgWidth, int zoom) {
        if (mDisplayWidth == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayWidth = metric.widthPixels;
        }
        int imageWidth = mDisplayWidth;
        switch (type) {
            case 0:
                imageWidth = imgWidth;
                break;
            case 1:
                imageWidth = mDisplayWidth;
                break;
            case 2:
                imageWidth = mDisplayWidth / 2;
                break;
            case 3:
                imageWidth = mDisplayWidth / 3;
                break;
            case 4:
                imageWidth = mDisplayWidth / 4;
                break;
        }
        return imgUrl + "?imageView2/3/w/" + imageWidth;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public float getDisplayDensity(Context context) {
        if (mDensity == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDensity = metric.density;
        }
        return mDensity;
    }

    public int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
//            LogUtil.i(this, e.toString());
        }
        return version;
    }

    /**
     * 获取手机版本
     *
     * @param context
     */
    public String getVersion(Context context) {
        if (TextUtils.isEmpty(mVersionName)) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                // 获取在AndroidManifest.xml中配置的版本号
                mVersionName = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(mVersionName)) {
            if (mVersionName.contains("-")) {
                mVersionName = mVersionName.split("-")[0];
            }
        }
        return mVersionName;
    }

    /**
     * 得到屏幕分辨率
     *
     * @return
     */
    public String getPixels(Context context) {
        DisplayMetrics displayMetrics = PhoneInfoUtil.getInstance()
                .getDisplayMetrics(context);
        return "手机分辨率： " + displayMetrics.widthPixels + "x"
                + displayMetrics.heightPixels;
    }

    /**
     * 获取设备IMEI信息
     * @param context
     * @return
     */
    public String getPhoneIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public String getPhoneSIM(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String SimSerialNumber = tm.getSimSerialNumber();
        return SimSerialNumber;
    }


    /**
     * 去掉指定字符串的开头和结尾的指定字符
     *
     * @param stream 要处理的字符串
     * @return 处理后的字符串
     */
    public static String sideTrim(String stream, String trimst) {
        String trimstr = "\\s*|\t|\r|\n";//去除字符串中的空格、回车、换行符、制表符
        if (!TextUtils.isEmpty(trimst)) {
            trimstr = trimst;
        }
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trimstr == null
                || trimstr.length() == 0) {
            return stream;
        }

        // 结束位置
        int epos;

        // 正规表达式
        String regpattern = "[" + trimstr + "]*+";
        Pattern pattern = Pattern.compile(regpattern, Pattern.CASE_INSENSITIVE);

        // 去掉结尾的指定字符
        StringBuffer buffer = new StringBuffer(stream).reverse();
        Matcher matcher = pattern.matcher(buffer);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = new StringBuffer(buffer.substring(epos)).reverse()
                    .toString();
        }

        // 去掉开头的指定字符
        matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = stream.substring(epos);
        }

        // 返回处理后的字符串
        return stream;
    }

    /**
     * 将url链接中的中文字符串转换成utf-8并用%做分隔
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 隐藏软键盘*
     */
    public static void closeSoftWareInput(Activity activity) {

        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    /**
     * 打开软键盘
     *
     * @param view
     */
    public static void showSoftWareInput(View view) {
        InputMethodManager inputManager =

                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.showSoftInput(view, 0);
    }
}
