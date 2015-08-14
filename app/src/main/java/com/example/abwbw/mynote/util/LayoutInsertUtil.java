package com.example.abwbw.mynote.util;

import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abwbw on 15-8-14.
 */
public class LayoutInsertUtil {
    public static void insertView(ViewGroup parentView,View inserView,ViewGroup.LayoutParams layoutParams){
        if(parentView != null && inserView != null){
            parentView.removeView(inserView);
            parentView.addView(inserView,layoutParams);
        }
    }

    public static void removeView(ViewGroup parentView,View removeView){
        if(parentView != null && removeView != null){
            parentView.removeView(removeView);
        }
    }

    public static ViewGroup.LayoutParams getMatchParentLayoutParams(){
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
