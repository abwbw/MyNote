package com.example.abwbw.mynote.base;

import android.app.Application;
import android.content.Context;

import com.example.abwbw.mynote.BuildConfig;
import com.example.abwbw.mynote.util.ScreenUtil;
import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by abwbw on 15-8-12.
 */
public class BaseApplication extends Application{
    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();
        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }
    }

    public static Context getAppContext(){
        return mAppContext;
    }
}
