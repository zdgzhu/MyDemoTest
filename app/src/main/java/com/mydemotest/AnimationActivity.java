package com.mydemotest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_layout);
        initView();
        initImpl();


    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image);


    }

    private void initImpl() {
        findViewById(R.id.btn_frame).setOnClickListener(this);
        findViewById(R.id.btn_frameStop).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_frame://开始帧动画
                image.setImageResource(R.drawable.ring_animation);
                animationDrawable = (AnimationDrawable) image.getDrawable();
                animationDrawable.start();
                break;

            case R.id.btn_frameStop://停止帧动画
                if (animationDrawable != null) {
//                    animationDrawable = (AnimationDrawable) image.getDrawable();
                    animationDrawable.stop();
                }

                break;








            default:
                break;

        }


    }


}
