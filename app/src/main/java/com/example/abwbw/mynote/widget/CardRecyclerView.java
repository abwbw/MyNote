package com.example.abwbw.mynote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.abwbw.mynote.util.LogUtil;

/**
 * Created by abwbw on 15-9-7.
 */
public class CardRecyclerView extends RecyclerView{
    private GradientDrawable mTopShade;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    public CardRecyclerView(Context context) {
        super(context);
        init();
    }

    public CardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        mTopShade = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { 0xFF111111,
                0x00AAAAAA, 0x00AAAAAA });
        setVerticalFadingEdgeEnabled(true);


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {


        super.dispatchDraw(canvas);


    }



    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0;
    }

    private void initCanvas(int width,int height){
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }



}
