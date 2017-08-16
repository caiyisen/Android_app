package com.me.android.dragonfly;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by admin on 2016/12/13.
 */

public class MyLeanCloudApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"tJHwu3s8wteWJW1gOwY5j8qb-gzGzoHsz","db5cOpE8F49buy1NwLmwXAOq");
//        AVOSCloud.initialize(BaseApplication.getContext(),"tJHwu3s8wteWJW1gOwY5j8qb-gzGzoHsz","db5cOpE8F49buy1NwLmwXAOq");


    }




    public static Context getContext() {
        return mContext;
    }
}
