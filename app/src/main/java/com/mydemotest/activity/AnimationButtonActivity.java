package com.mydemotest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mydemotest.R;
import com.mydemotest.UI.AnimationButton;

public class AnimationButtonActivity extends AppCompatActivity {

    private AnimationButton animationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_button);
        animationButton = (AnimationButton) findViewById(R.id.animation_btn);
        animationButton.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                animationButton.start();
            }

            @Override
            public void animationFinish() {

            }
        });


    }


}
