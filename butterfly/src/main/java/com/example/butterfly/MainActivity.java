package com.example.butterfly;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private float curX = 0;
    private float curY = 30;
    float nextX = 0;
    float nextY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.butterfly);
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    // 横向上一直向右飞
                    if (nextX > 600) {
                        curX = nextX = 0;
                    } else {
                        nextX += 8;
                    }
                    // 纵向上可以随机上下
                    nextY = curY + (float) (Math.random() * 10 - 5);
                    // 设置显示蝴蝶的ImageView发生位移改变
                    TranslateAnimation anim = new TranslateAnimation(
                            curX, nextX, curY, nextY);
                    curX = nextX;
                    curY = nextY;
                    anim.setDuration(200);
                    // 开始位移动画
                    imageView.startAnimation(anim); // ①
                }

                return false;
            }
        });
        final AnimationDrawable butterfly = (AnimationDrawable) imageView.getBackground();
        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.e("ssssss", "saaabbbbbb");
                                             butterfly.start();
                                             new Timer().schedule(new TimerTask() {
                                                 @Override
                                                 public void run() {

                                                     handler.sendEmptyMessage(0x123);
                                                 }
                                             }, 0, 200);
                                         }
                                     }

        );
    }
}
