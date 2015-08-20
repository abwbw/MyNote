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
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(mAdapter);

        mContentRv.addOnItemTouchListener(new RecyclerViewItemTouchHelper(this,mContentRv));
    }


}
