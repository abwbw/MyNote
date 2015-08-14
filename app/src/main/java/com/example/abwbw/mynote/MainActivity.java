package com.example.abwbw.mynote;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.abwbw.mynote.adapter.NotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;
import com.example.abwbw.mynote.util.LayoutInsertUtil;

public class MainActivity extends BaseActivity {
    private RecyclerView mContentRv;
    private NotesAdapter mAdapter;
    private View mFocusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        findView();
        initView();
    }

    private void findView(){
        mContentRv = (RecyclerView) findViewById(R.id.content_rv);
    }

    private void initView(){
        mAdapter = new NotesAdapter(this);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(mAdapter);
        mContentRv.stopScroll();

//        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
//                int swipFlag = 0;
//                return makeMovementFlags( makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,dragFlag),makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,swipFlag));
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                mAdapter.moveItem(viewHolder.getLayoutPosition(),target.getLayoutPosition());
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//        });
//        itemTouchHelper.attachToRecyclerView(mContentRv);
        mContentRv.addOnItemTouchListener(new ItemTouchListener(mContentRv));
    }


    private class ItemTouchListener implements RecyclerView.OnItemTouchListener{
        private final int PASS=50;

        private final int LONG_PRESS_TIMEOUT = 1000;

        private long mPressBeginTime= 0;

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

        public ItemTouchListener(RecyclerView rv){
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

                        mPressBeginTime=e.getEventTime();

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

        private void addFocusView(ViewGroup item){
            if(mFocusView == null){
                mFocusView = LayoutInflater.from(MainActivity.this).inflate(R.layout.focuse_full_view,null);
            }
            LayoutInsertUtil.insertView(item, mFocusView, LayoutInsertUtil.getMatchParentLayoutParams());
        }

        private void removeFocusView(){
            if(mFocusView != null && mCurLockItem != null){
                LayoutInsertUtil.removeView((ViewGroup) mCurLockItem,mFocusView);
            }

        }

        private View lockItem(View item){
            if(!mIsLockItem && item != null){
                mIsLockItem = true;
                mCurLockItem = item;
//                addFocusView((ViewGroup) item);
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
    }
}
