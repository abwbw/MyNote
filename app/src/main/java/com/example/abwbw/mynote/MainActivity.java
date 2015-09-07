package com.example.abwbw.mynote;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.abwbw.mynote.adapter.NotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;
import com.example.abwbw.mynote.model.NotesDesrcModel;

public class MainActivity extends BaseActivity {
    private RecyclerView mContentRv;
    private NotesAdapter mAdapter;
    private View mFocusView;
    LinearLayoutManager llm;


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
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mContentRv.setLayoutManager(llm);
        mContentRv.setAdapter(mAdapter);


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
