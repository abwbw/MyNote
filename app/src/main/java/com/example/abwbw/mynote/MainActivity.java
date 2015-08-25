package com.example.abwbw.mynote;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.abwbw.mynote.adapter.NotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;
import com.example.abwbw.mynote.model.NotesDesrcModel;

import java.util.List;

import widget.RecyclerViewItemTouchHelper;

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
        createDataList();
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(mAdapter);
        mContentRv.addOnItemTouchListener(new RecyclerViewItemTouchHelper(this,mContentRv));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//                return makeMovementFlags(dragFlag,swipeFlag);
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                mAdapter.swapItem(viewHolder.getLayoutPosition(),target.getLayoutPosition());
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                mAdapter.removeItem(viewHolder.getLayoutPosition());
//            }
//        });
//
//        itemTouchHelper.attachToRecyclerView(mContentRv);

    }

    private void createDataList(){
        for(int i=0;i<50;i++){
            NotesDesrcModel model = new NotesDesrcModel();
            model.setTitle("Title" + i);
            model.setAutor("Autor" + i);
            model.setDesrc("Desrc" + i);
            model.setDesrcPic(((BitmapDrawable) getResources().getDrawable(R.drawable.def_head_img)).getBitmap());
            mAdapter.addData(model);
        }

    }


}
