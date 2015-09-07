package com.example.abwbw.mynote.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by abwbw on 15-8-12.
 */
public class ScaleCradView extends CardView {
    private Context mContext;
    private ScaleGestureDetector mScaleDetector;
    public ScaleCradView(Context context) {
        super(context);
        init(context);
    }



    public ScaleCradView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleCradView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        mScaleDetector = new ScaleGestureDetector(mContext,new ScaleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Camera camera = new Camera();
        camera.translate(0,getMeasuredHeight(),0);
        camera.rotate(10, 0, 0);
        Matrix mMatrix = new Matrix();
        camera.getMatrix(mMatrix);
        mMatrix.pre
        camera.applyToCanvas(canvas);

        super.onDraw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);


    }

    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }
    }


}
