package com.example.abwbw.mynote;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abwbw.mynote.adapter.MainNotesAdapter;
import com.example.abwbw.mynote.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private DrawerLayout mLeftDl;
    private RecyclerView mContentRv;
    private CardView mCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);mCardView.set
        findView();
        initView();
    }

    private void findView(){
        mContentRv = (RecyclerView) findViewById(R.id.content_rv);
    }

    private void initView(){
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new MainNotesAdapter(this));
    }
}
