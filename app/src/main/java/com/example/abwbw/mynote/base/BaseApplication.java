package com.example.abwbw.mynote.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by abwbw on 15-8-12.
 */
public class BaseApplication extends Application{
    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();
    }

    public static Context getAppContext(){
        return mAppContext;
    }
}
