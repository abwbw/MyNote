package widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abwbw.mynote.R;
import com.example.abwbw.mynote.util.LayoutInsertUtil;

/**
 * Created by abwbw on 15-8-14.
 */
public class RecyclerViewItemTouchHelper implements RecyclerView.OnItemTouchListener {

    private final int PASS=50;

    private final int LONG_PRESS_TIMEOUT = 1000;

    private float mDownPointX = -1f;
    private float mDownPointY = -1f;
    private float mLastPointX = -1f;
    private float mLastPointY = -1f;

    private LongClickNotify mLongClickNotify= null;

    private boolean mIsLongClick = false;
    private boolean mIsMove = false;
    private boolean mIsLockItem = false;
    private View mCurLockItem;
    private RecyclerView mRecyclerView;
    private View mFoucseView;

    private ItemEventListenter mItemListener;

    private Context mContext;

    public RecyclerViewItemTouchHelper(Context context,RecyclerView rv){
        this.mContext = context;
        this.mRecyclerView = rv;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
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

                    View curItem = lockItem(rv.findChildViewUnder(curX, curY));

                    if(curItem != null){
                        mLongClickNotify = new LongClickNotify(curItem);
                        curItem.postDelayed(mLongClickNotify,LONG_PRESS_TIMEOUT);
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
                        mIsMove = true;
                        //每次滑动的微差
                        if(mLongClickNotify != null) {
                            rv.removeCallbacks(mLongClickNotify);
                        }

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

                                int newLeft = curItem.getLeft() + (int)smallDivX;
                                int newRight = curItem.getRight() + (int)smallDivX;
                                int newTop = curItem.getTop() + (int)smallDivY;
                                int newBottom = curItem.getBottom() + (int)smallDivY;

                                curItem.layout(newLeft, newTop, newRight, newBottom);
                            }
                        }

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    removeFocusView();
                    resetStateWhenUp();
                    if(mLongClickNotify != null){
                        rv.removeCallbacks(mLongClickNotify);
                    }
                    break;
                default:
            }
            mLastPointX = curX;
            mLastPointY = curY;
        }

        return retuanResult;
    }

    private void beginScroll(){
        mRecyclerView.setNestedScrollingEnabled(true);
    }

    private void bandScroll(){
        mRecyclerView.stopScroll();
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
            LayoutInsertUtil.removeView((ViewGroup) mCurLockItem,mFoucseView);
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

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//                LogUtil.toast("itemTouchEvent");

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                LogUtil.toast("onRequestDisallowInterceptTouchEvent");

    }

    public void move(View parentView,View item,float toX,float toY){

        ((ViewGroup)item.getParent()).invalidate();
    }

    private class LongClickNotify implements Runnable{
        private View longClickView;
        public LongClickNotify(View view){
            longClickView = view;
        }

        @Override
        public void run() {
            if(longClickView != null){
                longClickView.performLongClick();
                mIsLongClick = true;
            }

        }
    }

    public static interface ItemEventListenter {
        void onItemClick(RecyclerView parent, View itemView);
        void onItemLongClick(RecyclerView parent,View itemView);
        void onItemSlip(RecyclerView parent,View itemView);
    }

    public static interface EventListener{
        void onClick(RecyclerView parent);
        void onLongClick(RecyclerView parent);
        void onSlip(RecyclerView parent);
    }
}
