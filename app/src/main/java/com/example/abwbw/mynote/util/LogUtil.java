package com.example.abwbw.mynote.util;

import android.util.Log;
import android.widget.Toast;

import com.example.abwbw.mynote.base.BaseApplication;

/**
 * Created by abwbw on 15-8-12.
 */
public class LogUtil {
    public static final String DEBUGTAG = "abwbw1";

    public static void info(String message) {
        Log.i(DEBUGTAG, message);
    }

    public static void toast(String message){
        Toast.makeText(BaseApplication.getAppContext(),message,Toast.LENGTH_LONG).show();
    }
}
