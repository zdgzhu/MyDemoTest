package com.mydemotest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mydemotest.R;
import com.mydemotest.activity.AnimationActivity;
import com.mydemotest.activity.CustomUIActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initImpl();

    }
    private void init() {


    }

    private void initImpl() {

        findViewById(R.id.btn01).setOnClickListener(this);
        findViewById(R.id.btn02).setOnClickListener(this);
        findViewById(R.id.btn03).setOnClickListener(this);
        findViewById(R.id.btn04).setOnClickListener(this);
        findViewById(R.id.btn05).setOnClickListener(this);
        findViewById(R.id.btn06).setOnClickListener(this);
        findViewById(R.id.btn07).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn01:
               startToAct(CustomUIActivity.class);
                break;

            case R.id.btn02:
                startToAct(AnimationActivity.class);
                break;

            case R.id.btn03://按钮自定义动画
                startToAct(AnimationButtonActivity.class);
                break;

            case R.id.btn04:

                break;

            case R.id.btn05:

                break;

            case R.id.btn06:

                break;

            case R.id.btn07:

                break;

                default:
                    break;
        }
    }


    private void startToAct(Class clazz) {
        startActivity(new Intent(this,clazz));

    }


}
