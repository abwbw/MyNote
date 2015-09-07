package com.example.abwbw.mynote.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abwbw.mynote.util.LayoutInsertUtil;

/**
 * Created by abwbw on 15-8-14.
 */
public class RecyclerViewItemTouchHelper implements RecyclerView.OnItemTouchListener {
    public enum TouchState{
        PRESS,DRAGING,IDLE,NOINIT
    }
    private class TouchInfo{
        public float curTouchX = -1;
        public float curTouchY = -1;
        public TouchState touchState = TouchState.NOINIT;

    }


    private final int PASS=50;

    //原始坐标值
    private float mDownPointX = -1f;
    private float mDownPointY = -1f;
    //最新运动值
    private float mLastPointX = -1f;
    private float mLastPointY = -1f;

    //状态标记
    private boolean mIsLongClick = false;

    //子View锁,当被被捕获的时候,将进行锁定标记
    private boolean mIsLockItem = false;

    //当前被锁定的ItemView,如果mIsLockItem为false 则该对象为null
    private View mCurLockItem;

    //缓存之前所锁定的Item View
    private View mCachePreLockItem;

    //被绑定的mRecyclerView
    private RecyclerView mRecyclerView;

    //被锁定时候所附加的视图状态
    private View mFoucseView;

    private Context mContext;

    private GestureDetectorCompat mGestureDetector;

    private TouchInfo mTouchInfo;

    public RecyclerViewItemTouchHelper(Context context,RecyclerView rv){
        this.mContext = context;
        this.mRecyclerView = rv;
        this.mGestureDetector = new GestureDetectorCompat(context,new ItemTouchGestureListener());
        this.mTouchInfo = new TouchInfo();
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
//        boolean retuanResult = false;
//        int pointNumber = e.getPointerCount();
//        if(rv != null){
//            float curX=e.getX();
//            float curY=e.getY();
//
//            mTouchInfo.curTouchX = curX;
//            mTouchInfo.curTouchY = curY;
//
//            rv.stopScroll();
//
//            switch (e.getAction()){
//                case MotionEvent.ACTION_DOWN:
//                    mDownPointX = curX;
//                    mDownPointY = curY;
//
//                    mTouchInfo.touchState = TouchState.PRESS;
//
//                    View curItem = catchItemViewFormPoint(curX, curY);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//
//                    if(mDownPointX < 0  && mDownPointY < 0){
//                        return retuanResult;
//                    }
//                    //距离第一次按下的滑动距差
//                    float dragDivX = curX - mDownPointX;
//                    float dragDivY = curY - mDownPointY;
//                    if ((Math.abs(dragDivX) > PASS || Math.abs(dragDivY) > PASS)) {
//                        if(mLastPointX < 0 && mLastPointY < 0){
//                            return retuanResult;
//                        }
//
//                        mTouchInfo.touchState = TouchState.DRAGING;
//
//                        float smallDivX = curX - mLastPointX;
//                        smallDivX += (Math.signum(smallDivX) * 0.5f);
//                        float smallDivY = curY - mLastPointY;
//                        smallDivY += (Math.signum(smallDivY) * 0.5f);
//
//                        if (mIsLongClick == true) {
//                            curItem = catchItemViewFormPoint(curX, curY);
//                            if(curItem != null) {
//                                move(curItem,smallDivX,smallDivY);
//                                retuanResult = true;
//                            }
//                        }
//
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    mTouchInfo.touchState = TouchState.IDLE;
//
//                    removeFocusView();
//                    resetStateWhenUp();
//                    break;
//                default:
//            }
//            mLastPointX = curX;
//            mLastPointY = curY;
//        }

        if(mIsLongClick){
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        mGestureDetector.onTouchEvent(e);
        boolean retuanResult = false;
        int pointNumber = e.getPointerCount();
        if(rv != null){
            float curX=e.getX();
            float curY=e.getY();

            mTouchInfo.curTouchX = curX;
            mTouchInfo.curTouchY = curY;

            rv.stopScroll();

            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDownPointX = curX;
                    mDownPointY = curY;

                    mTouchInfo.touchState = TouchState.PRESS;

                    View curItem = catchItemViewFormPoint(curX, curY);
                    break;
                case MotionEvent.ACTION_MOVE:

                    if(mDownPointX < 0  && mDownPointY < 0){
                        return;
                    }
                    //距离第一次按下的滑动距差
                    float dragDivX = curX - mDownPointX;
                    float dragDivY = curY - mDownPointY;
                    if ((Math.abs(dragDivX) > PASS || Math.abs(dragDivY) > PASS)) {
                        if(mLastPointX < 0 && mLastPointY < 0){
                            return;
                        }

                        mTouchInfo.touchState = TouchState.DRAGING;

                        float smallDivX = curX - mLastPointX;
                        smallDivX += (Math.signum(smallDivX) * 0.5f);
                        float smallDivY = curY - mLastPointY;
                        smallDivY += (Math.signum(smallDivY) * 0.5f);

                        if (mIsLongClick == true) {
                            curItem = catchItemViewFormPoint(curX, curY);
                            if(curItem != null) {
                                move(curItem,smallDivX,smallDivY);
                                retuanResult = true;
                            }
                        }

                    }
                    break;
                case MotionEvent.ACTION_UP:

                    mTouchInfo.touchState = TouchState.IDLE;

                    removeFocusView();
                    resetStateWhenUp();
                    break;
                default:
            }
            mLastPointX = curX;
            mLastPointY = curY;
        }

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //根据坐标去捕获子View
    private View catchItemViewFormPoint(float fromX,float fromY){
        View view = lockItem(mRecyclerView.findChildViewUnder(fromX, fromY));
        switchToFront(view);
        return view;
    }

    //缓存当前的锁定的ItemView
    private void cacheCurrentItemView(){
        if(mCurLockItem != null){
            mCachePreLockItem = mCurLockItem;
        }
    }

    //释放捕获的ItemView
    private void releaseCatchedItemView() {
        //解锁前进行对象缓存
        cacheCurrentItemView();
        unlockItem();
    }

    private void resetStateWhenUp(){
        mIsLongClick = false;
        releaseCatchedItemView();
    }

    public void setFoucseView(View foucseView){
        this.mFoucseView = foucseView;
    }

    private void addFocusView(ViewGroup item){
        if(mFoucseView != null){
            LayoutInsertUtil.insertView(item, mFoucseView, LayoutInsertUtil.getMatchParentLayoutParams());
        }
    }

    private void removeFocusView(){
        if(mFoucseView != null && mCurLockItem != null){
            LayoutInsertUtil.removeView((ViewGroup) mCurLockItem, mFoucseView);
        }

    }

    private void switchToFront(View targeView){
        if(targeView == null){
            return;
        }

        if(mRecyclerView.getChildAdapterPosition(targeView) != RecyclerView.NO_POSITION){
            if(mTouchInfo.touchState == TouchState.DRAGING && mTouchInfo.curTouchX > 0 && mTouchInfo.curTouchY >0){
                View toView = mRecyclerView.findChildViewUnder(mTouchInfo.curTouchX,mTouchInfo.curTouchY);
                if(targeView != toView) {
                    switchPlaceView(targeView, toView);
                }
            }
        }
    }

    private void switchPlaceView(View fromView,View toView){
        if(fromView == null || toView == null){
            return;
        }
        int fromPosition = mRecyclerView.getChildAdapterPosition(fromView);
        int toPosition = mRecyclerView.getChildAdapterPosition(toView);

        if(fromPosition == toPosition){
            return;
        }

        if(fromPosition < toPosition){
            toPosition--;
        }

        if(fromPosition != RecyclerView.NO_POSITION && toPosition != RecyclerView.NO_POSITION){
            int fromLeft = fromView.getLeft();
            int fromRight = fromView.getRight();
            int fromTop = fromView.getTop();
            int fromBottom = fromView.getBottom();

            int toLeft = toView.getLeft();
            int toRight = toView.getRight();
            int toTop = toView.getTop();
            int toBottom = toView.getBottom();

//            mRecyclerView.removeView(fromView);
//            mRecyclerView.addView(fromView, toPosition);

//            mRecyclerView.removeView(toView);
//            mRecyclerView.addView(toView, fromPosition);
//            toView.layout(toLeft,toTop,toRight,toBottom);
//            fromView.layout(fromLeft, fromTop, fromRight, fromBottom);

        }

    }

    private View lockItem(View item){
        if(!mIsLockItem && item != null){
            mIsLockItem = true;
            mCurLockItem = item;
                addFocusView((ViewGroup) item);
        }
        return mCurLockItem;
    }

    private void unlockItem(){
        mIsLockItem = false;
//            removeFocusView();
        mCurLockItem = null;
    }


    public int[] getScreenParams(){
        int[] params = new int[]{0,0};
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        params[0] = dm.widthPixels;
        params[1] = dm.heightPixels;
        return params;
    }



    public void move(View item,float divX,float divY){
        int newLeft = item.getLeft() + (int)divX;
        int newRight = item.getRight() + (int)divX;
        int newTop = item.getTop() + (int)divY;
        int newBottom = item.getBottom() + (int)divY;
        item.layout(newLeft, newTop, newRight, newBottom);
    }

    private class ItemTouchGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            mIsLongClick = true;
        }
    }


}
