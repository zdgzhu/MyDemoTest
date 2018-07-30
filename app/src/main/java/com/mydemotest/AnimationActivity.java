package com.mydemotest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_set).setOnClickListener(this);
        //属性动画
        findViewById(R.id.btn_animator_alpha).setOnClickListener(this);
        findViewById(R.id.btn_animator_rotation).setOnClickListener(this);
        findViewById(R.id.btn_animator_scale).setOnClickListener(this);
        findViewById(R.id.btn_animator_translation).setOnClickListener(this);
        findViewById(R.id.btn_animator_set).setOnClickListener(this);

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
                    animationDrawable.stop();
                    image.setImageResource(R.drawable.a11);
                }

                break;

            case R.id.btn_alpha://补间动画
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
                image.startAnimation(animation);
                break;

            case R.id.btn_set://组合动画
                Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.set_anim);
                image.startAnimation(animation1);
                break;

                //属性动画
            case R.id.btn_animator_alpha:
                alphaAnimator();
                break;

            case R.id.btn_animator_rotation://旋转
                rotationAnimator();
                break;

            case R.id.btn_animator_scale://缩放
                scaleXAnimator();
                break;

            case R.id.btn_animator_translation://位移
                translationAnimator();
                break;


            case R.id.btn_animator_set://组合动画
                animatorSet();
                break;


            default:
                break;

        }
    }


    //属性动画，淡入淡出
    private void alphaAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(image, "alpha", 1.0f, 0.8f, 0.5f, 0.0f, 0.5f, 1.0f);
        anim.setRepeatCount(3);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(3000);
        anim.start();
    }
    //旋转
    private void rotationAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        anim.setDuration(3000);
        anim.start();

    }

    //缩放
    private void scaleXAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(image, "scaleX", 1.0f, 0.5f, 0.0f, 0.5f, 1.0f,1.5f,2.0f,1.5f,1.0f);
        anim.setDuration(3000);
        //设置动画重复次数，这里-1代表无限
        anim.setRepeatCount(3);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();


    }

    //位移
    private void translationAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(image, "translationX", 0, 500,0);
        anim.setDuration(3000);
        anim.start();


    }
    //属性动画的组合效果
    private void animatorSet() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(image, "alpha", 1.0f, 0.5f, 0.0f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(image, "scaleX", 1.0f, 0.5f, 0.0f, 0.5f, 1.0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(image, "rotation", 0, 360);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(image, "translationX", 0, 400, 0);
        AnimatorSet set = new AnimatorSet();
//        anim1.setRepeatCount(3);
        set.playTogether(anim1, anim2, anim3, anim4);
//        set.play(anim1).with(anim2);
        set.setDuration(3000);
        set.start();

    }





}
