package com.example.abwbw.mynote;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abwbw.mynote.adapter.NotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;

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
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                int swipFlag = 0;
                return makeMovementFlags( makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,dragFlag),makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,swipFlag));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mAdapter.moveItem(viewHolder.getLayoutPosition(),target.getLayoutPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(mContentRv);
    }
}
