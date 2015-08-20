package widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abwbw.mynote.util.LayoutInsertUtil;
import com.example.abwbw.mynote.util.LogUtil;

/**
 * Created by abwbw on 15-8-14.
 */
public class RecyclerViewItemTouchHelper implements RecyclerView.OnItemTouchListener {
    public static enum ActionStatus{
        IDLE,PRESS,DRAGING
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

    //被绑定的mRecyclerView
    private RecyclerView mRecyclerView;

    //被锁定时候所附加的视图状态
    private View mFoucseView;

    private Context mContext;

    //标志与用户交互状态的参数
    //RecyclerView的状态
    private ActionStatus mRvActionStatus = ActionStatus.IDLE;
    //RecyclerView Item的状态
    private ActionStatus mCurItemActionStatus = ActionStatus.IDLE;

    private GestureDetectorCompat mGestureDetector;

    public RecyclerViewItemTouchHelper(Context context,RecyclerView rv){
        this.mContext = context;
        this.mRecyclerView = rv;
        this.mGestureDetector = new GestureDetectorCompat(context,new ItemTouchGestureListener());
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        boolean retuanResult = false;
        int pointNumber = e.getPointerCount();
        if(rv != null){
            float curX=e.getX();
            float curY=e.getY();

            rv.stopScroll();


            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDownPointX = curX;
                    mDownPointY = curY;

                    mRvActionStatus = ActionStatus.PRESS;
                    View curItem = lockItem(rv.findChildViewUnder(curX, curY));
                    if(curItem != null){
                        mCurItemActionStatus = ActionStatus.PRESS;
                    }

                    break;
                case MotionEvent.ACTION_MOVE:

                    if(mDownPointX < 0  && mDownPointY < 0){
                        return retuanResult;
                    }
                    //距离第一次按下的滑动距差
                    float dragDivX = curX - mDownPointX;
                    float dragDivY = curY - mDownPointY;
                    if ((Math.abs(dragDivX) > PASS || Math.abs(dragDivY) > PASS)) {
                        if(mLastPointX < 0 && mLastPointY < 0){
                            return retuanResult;
                        }

                        float smallDivX = curX - mLastPointX;
                        smallDivX += (Math.signum(smallDivX) * 0.5f);
                        float smallDivY = curY - mLastPointY;
                        smallDivY += (Math.signum(smallDivY) * 0.5f);

                        if (mIsLongClick == true) {
                            curItem = lockItem(rv.findChildViewUnder(curX, curY));
                            if(curItem != null) {
                                move(curItem,smallDivX,smallDivY);
                            }
                        }

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    removeFocusView();
                    resetStateWhenUp();
                    break;
                default:
            }
            mLastPointX = curX;
            mLastPointY = curY;
        }

        return retuanResult;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        //对应DOWN响应不完全

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //根据坐标去捕获子View
    private View catchItemViewFormPoint(float fromX,float fromY){
        return lockItem(mRecyclerView.findChildViewUnder(fromX, fromY));
    }

    private View releaseCatchedItemView(){
        if(mCurLockItem != null){

        }
    }

    private void resetStateWhenUp(){
        mIsLongClick = false;
        mIsMove = true;
        unlockItem();
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

    private void switchPlaceView(){

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
