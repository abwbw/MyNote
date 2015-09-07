package com.example.abwbw.mynote.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.abwbw.mynote.util.LogUtil;

/**
 * Created by abwbw on 15-8-26.
 */
public class RVItemTouchHelper implements RecyclerView.OnItemTouchListener {
    private Context mContext;
    private RecyclerView mRecyclerView;

    private GestureDetector mGestureDetector;
    public RVItemTouchHelper(Context context,RecyclerView recyclerView){
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        initGestureDetector();
    }

    public void initGestureDetector(){
        mGestureDetector = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtil.info("longPress");
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        LogUtil.info("onInterceptTouchEvent");
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        LogUtil.info("onTouchEvent");
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
