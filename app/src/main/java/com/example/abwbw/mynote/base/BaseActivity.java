package com.example.abwbw.mynote.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.abwbw.mynote.R;

/**
 * Created by abwbw on 15-8-11.
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    private void initToolBar(){
        ActionBar acitonBar = getSupportActionBar();
        acitonBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme_main)));
        acitonBar.setElevation(0);
    }
}
