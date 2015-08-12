package com.example.abwbw.mynote.util;

import android.util.DisplayMetrics;

import com.example.abwbw.mynote.base.BaseApplication;

/**
 * Created by abwbw on 15-8-12.
 */
public class ScreenUtil {
    public static int getWidthPxFormPrecent(double precent){
        DisplayMetrics mDisplay = BaseApplication.getAppContext().getResources().getDisplayMetrics();
        int width = mDisplay.widthPixels;
        if(precent >=0 && precent <=1){
            return (int)(precent*width);
        }
        return -1;
    }


}
