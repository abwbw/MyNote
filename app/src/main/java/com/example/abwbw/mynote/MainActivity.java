package com.example.abwbw.mynote;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.abwbw.mynote.adapter.NotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;
import com.example.abwbw.mynote.util.LogUtil;

import widget.ItemTouchHelper;

public class MainActivity extends BaseActivity {
    private RecyclerView mContentRv;
    private NotesAdapter mAdapter;

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
        mContentRv.addOnItemTouchListener(new ItemTouchListener());
    }


    private class ItemTouchListener implements RecyclerView.OnItemTouchListener{
        private final int PASS=50;

        private final int LONG_PRESS_TIMEOUT = 1000;

        private long mPressBeginTime= 0;

        private float mInitPointX = 0f;
        private float mInitPointY = 0f;

        private LongClickNotify mLongClickNotify= null;

        private boolean mIsLongClick = false;


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if(rv != null){
                float curX=e.getX();
                float curY=e.getY();

                View curItem = rv.findChildViewUnder(curX, curY);
                float divX = curX - mInitPointX;
                float divY = curY - mInitPointY;
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mInitPointX = curX;
                        mInitPointY = curY;

                        mPressBeginTime=e.getEventTime();

                        if(curItem != null){
                            mLongClickNotify = new LongClickNotify(curItem);
                            curItem.postDelayed(mLongClickNotify,LONG_PRESS_TIMEOUT);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float rawX = e.getRawX();
                        float rawY = e.getRawY();

                        //布局移动后的残影
                        if(curItem != null && mIsLongClick == true){
                            LogUtil.info("left :" + curItem.getLeft() +  " top :" + curItem.getTop() + " right :" + curItem.getRight() + " bottom :" + curItem.getBottom());
                            curItem.layout(curItem.getLeft() + 100, curItem.getTop(), curItem.getRight(), curItem.getBottom());

                        }

                        if((Math.abs(divX) > PASS || Math.abs(divY) > PASS) && mLongClickNotify != null){
                            rv.removeCallbacks(mLongClickNotify);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        mIsLongClick = false;
                        if((Math.abs(divX) > PASS || Math.abs(divY) > PASS) && mLongClickNotify != null){
                            rv.removeCallbacks(mLongClickNotify);
                        }
                        break;
                    default:
                }
            }

            return false;
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
