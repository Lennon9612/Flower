package com.catchmind.FlowerClassificationApplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class LoadingActivity extends AppCompatActivity {
    private ImageView imgAndroid;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_loading);
        startLoading();
    }

    private void initView(){
        imgAndroid = (ImageView) findViewById(R.id.img_loading);
        anim = AnimationUtils.loadAnimation(this, R.anim.activity_loading);
        imgAndroid.setAnimation(anim);
    }


    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), SubActivity.class);
                finish();
            }
        }, 2000);
        initView();
    }



}
