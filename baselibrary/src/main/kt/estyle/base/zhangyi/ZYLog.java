package estyle.base.zhangyi;

import android.util.Log;

import estyle.base.BuildConfig;

/**
 * Created by zhangyi
 */
public class ZYLog {

    public static void e() {
        e("", "-------------------------------------------------------------------------");
    }

    public static void e(Object obj) {
        e("", obj);
    }

    public static void e(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.e("zhangyi", tag + " -> " + obj);
        }
    }

}