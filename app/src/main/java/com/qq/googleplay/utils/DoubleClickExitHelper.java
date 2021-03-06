package com.qq.googleplay.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;

import com.qq.googleplay.manager.AppManager;
/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：GooglePlay
 * Package_Name：com.qq.googleplay
 * Version：1.0
 * time：2016/2/16 13:33
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class DoubleClickExitHelper {

    private final Activity mActivity;

    private boolean  isOnKeyBacking;
    private Handler  mHandler;
    private Snackbar snackbar;

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
        AppManager.getAppManager().addActivity(mActivity);
    }

    /**
     * Activity onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, View view) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (snackbar != null) {
                snackbar.dismiss();
            }
            //AppManager.getAppManager().AppExit(mActivity);
            return true;
        } else {
            isOnKeyBacking = true;
            if (snackbar == null) {
                snackbar = Snackbar.make(view, "再次点击退出应用", 2000);
            }
            snackbar.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    };

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExitHelper.onKeyDown(keyCode, navigationView);
        }
        return flag;
    }*/
}